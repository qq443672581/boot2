package cn.dlj1.cms.service;

import cn.dlj1.cms.config.GlobalConfig;
import cn.dlj1.cms.dao.Dao;
import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.entity.support.EntityUtils;
import cn.dlj1.cms.exception.MessageException;
import cn.dlj1.cms.response.Result;
import cn.dlj1.cms.service.supports.FieldUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

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

    static Log log = LogFactory.getLog(ActionService.class);

    Result UPLOAD_SIZE_TOO_BIG = new Result.Fail("文件太大!");

    @Override
    GlobalConfig getGlobalConfig();

    @Override
    Dao<T> getDao();

    default Result add(T entity) {
        int i = getDao().insert(entity);
        if (i == 1) {
            log.info(String.format("保存实体[%s][%s]", getModuleClazz().getName(), entity.getId()));
            return new Result.Success(entity.getId());
        }
        log.error(String.format("保存实体[%s]时错误", getModuleClazz().getName()));
        return Result.FAIL;
    }

    default Result edit(T entity) {
        int i = getDao().updateById(entity);
        if (i == 1) {
            return Result.SUCCESS;
        }
        log.error(String.format("修改实体[%s]时错误", getModuleClazz().getName()));
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
//        log.error(String.format("删除实体[%s]时错误", getModuleClazz().getName() ));
//        return Result.FAIL;
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

    default Result upload(MultipartFile ele) {
        GlobalConfig config = getGlobalConfig();
        Long size = config.getFileSize().get(getModuleClazz().getName());

        if ((null != size && ele.getSize() > size)
                || ele.getSize() > config.getUploadFileSize()) {
            return UPLOAD_SIZE_TOO_BIG;
        }

        System.out.println(ele.getOriginalFilename());
        System.out.println(ele.getName());
        System.out.println(ele.getContentType());

        File file = new File("C:\\Users\\Administrator\\Desktop\\xxxxxxxxx.txt");
        try {
            ele.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Result.SUCCESS;
    }

}
