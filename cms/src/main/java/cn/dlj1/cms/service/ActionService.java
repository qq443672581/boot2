package cn.dlj1.cms.service;

import cn.dlj1.cms.config.GlobalConfig;
import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.entity.annotation.SelectModule;
import cn.dlj1.cms.entity.annotation.SelectModuleUtils;
import cn.dlj1.cms.exception.MessageException;
import cn.dlj1.cms.response.Result;
import cn.dlj1.cms.service.supports.FileUploadUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

/**
 * 操作
 *
 * @param <T>
 */
public interface ActionService<T extends Entity> extends Service<T> {

    static Logger logger = LoggerFactory.getLogger(GlobalConfig.class);

    Result EDIT_FAIL = new Result.Fail("修改失败!");

    Result UPLOAD_FILE_SIZE_TOO_BIG = new Result.Fail("文件太大!");
    Result UPLOAD_FILE_EXT_NOT_ALLOW = new Result.Fail("不被允许的文件类型!");
    Result SELECT_MODULE_NOT_CONFIG = new Result.Fail("下拉模块未配置!");


    /**
     * 添加
     *
     * @param entity
     * @return
     */
    @Transactional
    default Result add(HttpServletRequest request, T entity) {
        fill(false, entity);
        Serializable id = getDao()._add_(request, entity);
        if (null != id) {
            logger.info("保存实体[{}][{}]", getModuleClazz().getName(), entity.getId());
            return new Result.Success(id);
        }
        logger.error("保存实体[{}]时错误", getModuleClazz().getName());
        return Result.FAIL;
    }

    /**
     * 修改 <br>
     * 根据ID <br>
     *
     * @param entity
     * @return
     */
    @Transactional
    default Result edit(HttpServletRequest request, T entity) {
        fill(false, entity);
        int i = getDao()._edit_(request, entity);
        if (i == 1) {
            return Result.SUCCESS;
        }
        logger.error("修改实体[{}]时错误", getModuleClazz().getName());
        return EDIT_FAIL;
    }

    /**
     * 填充 <br>
     * 处于 add 和 edit 之前 <br>
     *
     * @param isEdit
     * @param entity
     */
    default void fill(boolean isEdit, T entity) {
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @Transactional
    default Result delete(HttpServletRequest request, Serializable... ids) {
        if (null == ids || ids.length == 0) {
            return Result.FAIL_NULL;
        }
        for (int i = 0; i < ids.length; i++) {
            int ret = getDao()._delete_(request, getModuleClazz(), ids[i]);
            if (ret == -1) {
                throw new MessageException(getModuleClazz(), "删除失败,数据不存在!");
            }
            if (ret != 1) {
                throw new MessageException(getModuleClazz(), "删除失败!");
            }
        }
        return Result.SUCCESS;
    }

    /**
     * 单条查询
     *
     * @param request
     * @param id
     * @return
     */
    default Result view(HttpServletRequest request, Serializable id) {
        Map<String, Object> map = getDao()._view_(request, getModuleClazz(), id, true);
        if (null == map) {
            return new Result.Fail("数据不存在!");
        }
        return new Result.Success(map);
    }

    /**
     * 下拉选择
     *
     * @param text
     * @return
     */
    default Result select(String text) {
        SelectModule selectModule = SelectModuleUtils.get(getModuleClazz());
        if (null == selectModule) {
            logger.error("模块[{}] 虽然实现了 SelectController，但是没有把 @SelectModule 注解标注在模块实体", getModuleClazz().getName());
            return SELECT_MODULE_NOT_CONFIG;
        }
        String textField = selectModule.text();
        String valueField = selectModule.value();
        String field = selectModule.order();
        boolean asc = selectModule.asc();

        QueryWrapper<T> wrapper = new QueryWrapper<>();
        wrapper.select(textField + " as text", valueField + " as value");
        wrapper.like(textField, text);
        wrapper.orderBy(true, asc, "".equals(field) ? valueField : field);
        IPage<Map<String, Object>> page = getDao().selectMapsPage(new Page<T>(1, 1000), wrapper);

        return new Result.Success(page.getRecords());
    }

    /**
     * 文件上传
     *
     * @param request
     * @param ele
     * @return
     */
    @Transactional
    default Result upload(HttpServletRequest request, MultipartFile ele) {
        GlobalConfig config = getGlobalConfig(request);
        String fileName = ele.getOriginalFilename();
        String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);

        // 优先模块自定义的配置
        // 大小限制
        Long moduleSzie = config.getFileUpload().getModuleSize().get(getModuleClazz().getName());
        if ((null == moduleSzie && ele.getSize() > config.getFileUpload().getSize())
                || ((null != moduleSzie && ele.getSize() > moduleSzie))
                ) {
            return UPLOAD_FILE_SIZE_TOO_BIG;
        }
        // 格式限制
        String[] moduleExts = config.getFileUpload().getModuleExt().get(getModuleClazz().getName());
        if ((null == moduleExts && !ArrayUtils.contains(config.getFileUpload().getExt(), fileExt))
                || ((null != moduleExts && !ArrayUtils.contains(moduleExts, fileExt)))
                ) {
            return UPLOAD_FILE_EXT_NOT_ALLOW;
        }
        // root 路径
        if (StringUtils.isEmpty(config.getFileUpload().getRootPath())) {
            logger.error("系统默认文件上传路径未配置[ec.file-upload.root-path]");
            return Result.FAIL;
        }

        // 创建文件夹
        // 文件相对路径
        String fileRelationPath = FileUploadUtils.getFileRelationPath(config.getFileUpload().getRootPath(), fileExt);

        File file = new File(config.getFileUpload().getRootPath() + fileRelationPath);
        try {
            ele.transferTo(file);
            logger.info("文件上传：模块[{}]上传文件[{}]到[{}]", getModuleClazz().getName(), fileName, file.getAbsolutePath());
            return new Result.Success(fileRelationPath);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("文件上传失败,[{}]", e.getMessage());
        }

        return Result.FAIL;
    }

}
