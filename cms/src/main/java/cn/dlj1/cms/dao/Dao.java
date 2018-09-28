package cn.dlj1.cms.dao;

import cn.dlj1.cms.dao.support.DaoUtils;
import cn.dlj1.cms.dao.support.RelationFieldCache;
import cn.dlj1.cms.dao.support.relationQueryImpl.RelationAddProcess;
import cn.dlj1.cms.dao.support.relationQueryImpl.RelationDeleteProcess;
import cn.dlj1.cms.dao.support.relationQueryImpl.RelationEditProcess;
import cn.dlj1.cms.dao.support.relationQueryImpl.RelationQueryProcess;
import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.entity.annotation.TableFieldUtils;
import cn.dlj1.cms.exception.MessageException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.commons.lang.reflect.FieldUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.*;
import java.util.List;
import java.util.Map;

/**
 * dao(mapper)扩展接口
 * <p>
 * 接口
 */
public interface Dao<T extends Entity> extends BaseMapper<T> {

    /**
     * 关联添加
     *
     * @param request
     * @param entity
     * @return
     */
    default Serializable _add_(HttpServletRequest request, T entity) {
        if (null == entity) {
            throw new MessageException("保存对象不能为空!");
        }

        int size = insert(entity);
        if (size == 0) {
            return null;
        }
        Object id = null;
        try {
            Field pkField = RelationFieldCache.getEntityPkField(entity.getClass());
            id = FieldUtils.readField(entity, pkField.getName(), true);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        // 如果没有级联
        Field[] relationFields = RelationFieldCache.getRelationFields(entity.getClass());
        if (relationFields.length == 0) {
            return (Serializable) id;
        }

        new RelationAddProcess() {
            @Override
            public HttpServletRequest getRequest() {
                return request;
            }

            @Override
            public Entity getEntity() {
                return entity;
            }
        }.add();

        return (Serializable) id;
    }

    default int _delete_(HttpServletRequest request, Class<T> clazz, Serializable id) {
        if (null == id) {
            throw new MessageException("删除数据不能为空!");
        }

        int ret = selectCount(new QueryWrapper<T>().eq(TableFieldUtils.getName(RelationFieldCache.getEntityPkField(clazz)), id));
        if (ret != 1) {
            return -1;
        }
        ret = deleteById(id);

        // 如果没有关联字段
        if (ret == 0 || RelationFieldCache.getRelationFields(clazz).length == 0) {
            return ret;
        }

        new RelationDeleteProcess() {
            @Override
            public HttpServletRequest getRequest() {
                return request;
            }

            @Override
            public Class getClazz() {
                return clazz;
            }

            @Override
            public T getEntity(Serializable id) {
                return selectById(id);
            }

            @Override
            public Serializable getId() {
                return id;
            }
        }.delete();

        return ret;
    }

    default int _edit_(HttpServletRequest request, T entity) {
        if (null == entity) {
            throw new MessageException("数据不能为空!");
        }

        int level = DaoUtils.editLevel(entity);
        if (level == 1) {
            throw new MessageException("无可修改数据!");
        }
        int ret = 0;
        if (level == 2 || level == 4) {
            ret = updateById(entity);
            if (ret == 0) {
                return 0;
            }
        }

        // 如果没有关联字段
        if (ret == 0 && RelationFieldCache.getRelationFields(entity.getClass()).length == 0) {
            return ret;
        }
        ret = 1;

        new RelationEditProcess() {
            @Override
            public HttpServletRequest getRequest() {
                return request;
            }

            @Override
            public Entity getEntity() {
                return entity;
            }

        }.edit();


        return ret;

    }

    default Map<String, Object> _view_(HttpServletRequest request, Class<T> clazz, Serializable id, boolean relation) {
        QueryWrapper queryWrapper = new QueryWrapper<T>();

        queryWrapper.select(cn.dlj1.cms.service.supports.FieldUtils.getSearchFields(clazz));
        queryWrapper.eq(TableFieldUtils.getName(RelationFieldCache.getEntityPkField(clazz)), id);

        List<Map<String, Object>> list = selectMaps(queryWrapper);

        if (null == list || list.size() == 0) {
            return null;
        }
        Map<String, Object> map = list.get(0);
        // 如果没有关联字段
        if (!relation || RelationFieldCache.getRelationFields(clazz).length == 0) {
            return map;
        }

        new RelationQueryProcess() {
            @Override
            public HttpServletRequest getRequest() {
                return request;
            }

            @Override
            public Class getClazz() {
                return clazz;
            }

            @Override
            public Map<String, Object> getMap() {
                return map;
            }
        }.query();

        return map;
    }

}
