package cn.dlj1.cms.service;

import cn.dlj1.cms.config.GlobalConfig;
import cn.dlj1.cms.dao.Dao;
import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.entity.support.EntityUtils;
import cn.dlj1.cms.exception.MessageException;
import cn.dlj1.cms.response.Result;
import cn.dlj1.cms.service.supports.FieldUtils;
import cn.dlj1.cms.utils.DateUtils;
import cn.dlj1.cms.utils.RandomUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 操作
 *
 * @param <T>
 */
public interface ActionService<T extends Entity> extends Service<T> {

    static Logger logger = LoggerFactory.getLogger(GlobalConfig.class);

    Result UPLOAD_FILE_SIZE_TOO_BIG = new Result.Fail("文件太大!");
    Result UPLOAD_FILE_EXT_NOT_ALLOW = new Result.Fail("不被允许的文件类型!");

    @Override
    Dao<T> getDao();

    default Result add(T entity) {
        int i = getDao().insert(entity);
        if (i == 1) {
            logger.info("保存实体[{}][{}]", getModuleClazz().getName(), entity.getId());
            return new Result.Success(entity.getId());
        }
        logger.error("保存实体[{}]时错误", getModuleClazz().getName());
        return Result.FAIL;
    }

    default Result edit(T entity) {
        int i = getDao().updateById(entity);
        if (i == 1) {
            return Result.SUCCESS;
        }
        logger.error("修改实体[{}]时错误", getModuleClazz().getName());
        return Result.FAIL;
    }

    default void packaging(T entity) {

    }


    default Result delete(Serializable... ids) {
        if (null == ids || ids.length == 0) {
            return Result.FAIL_NULL;
        }
        for (int i = 0; i < ids.length; i++) {
            if (getDao().deleteById(ids[i]) != 1) {
                throw new MessageException(getModuleClazz(), "删除失败!");
            }
        }
        return Result.SUCCESS;
    }

    default Result view(Serializable id) {
        QueryWrapper queryWrapper = new QueryWrapper<T>();
        queryWrapper.select(FieldUtils.getSearchFields(getModuleClazz()));
        queryWrapper.eq(EntityUtils.getEntityPk(getModuleClazz()), id);

        List<Map<String, Object>> list = getDao().selectMaps(queryWrapper);
        if (null == list || list.size() != 1) {
            return new Result.Fail("数据不存在或存在多条!");
        }
        return new Result.Success(list.get(0));
    }

    default Result select(String text) {

        return Result.SUCCESS;
    }

    default Result upload(HttpServletRequest request, MultipartFile ele) {
        GlobalConfig config = getGlobalConfig(request);
        String fileName = ele.getOriginalFilename();
        String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);

        // 优先模块自定义的配置
        // 大小限制
        Long moduleSzie = config.getFileUploadModuleSize().get(getModuleClazz().getName());
        if ((null == moduleSzie && ele.getSize() > config.getFileUploadSize())
                || ((null != moduleSzie && ele.getSize() > moduleSzie))
                ) {
            return UPLOAD_FILE_SIZE_TOO_BIG;
        }
        // 格式限制
        String[] moduleExts = config.getFileUploadModuleExt().get(getModuleClazz().getName());
        if ((null == moduleExts && !ArrayUtils.contains(config.getFileUploadExt(), fileExt))
                || ((null != moduleExts && !ArrayUtils.contains(moduleExts, fileExt)))
                ) {
            return UPLOAD_FILE_EXT_NOT_ALLOW;
        }
        // root 路径
        if (StringUtils.isEmpty(config.getFileUploadRootPath())) {
            return Result.FAIL;
        }

        // 创建文件夹
        String dirPath = DateUtils.getDateString(DateUtils.getNow(),
                File.separator + "yyyyMM" + File.separator + "dd");
        File file = new File(config.getFileUploadRootPath() + dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        // 文件相对路径
        String fileRelationPath = dirPath + File.separator + RandomUtils.getFileName(fileExt);
        file = new File(config.getFileUploadRootPath() + fileRelationPath);
        try {
            ele.transferTo(file);
            logger.info("文件上传：模块[{}]上传文件[{}]到[{}]", getModuleClazz().getName(), fileName, file.getAbsolutePath());
            return new Result.Success(fileRelationPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Result.FAIL;
    }

}
