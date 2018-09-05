package cn.dlj1.cms.service;

import cn.dlj1.cms.dao.Dao;
import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.exception.MessageException;
import cn.dlj1.cms.response.Result;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;

/**
 * 操作
 *
 * @param <T>
 */
public interface ActionService<T extends Entity> extends Service<T> {

    static Log log = LogFactory.getLog(ActionService.class);

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

    default void packaging(T entity){

    }


    default Result delete(Serializable... id) {
        int i = getDao().deleteById(id);
        if (i == 1) {
            return Result.SUCCESS;
        }
        throw new MessageException(getModuleClazz(), "删除失败");
//        log.error(String.format("删除实体[%s]时错误", getModuleClazz().getName() ));
//        return Result.FAIL;
    }


}
