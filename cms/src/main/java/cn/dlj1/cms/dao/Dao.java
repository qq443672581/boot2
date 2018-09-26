package cn.dlj1.cms.dao;

import cn.dlj1.cms.dao.support.RelationAddProcess;
import cn.dlj1.cms.dao.support.RelationFieldCache;
import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.exception.MessageException;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.commons.lang.reflect.FieldUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

/**
 * dao(mapper)扩展接口
 * <p>
 * 接口
 */
public interface Dao<T extends Entity> extends BaseMapper<T> {

    /**
     * 获取模块类型
     *
     * @return
     */
    default Class<T> getModuleClazz() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
    }

    /**
     * 关联添加
     *
     * @param request
     * @param entity
     * @param recursion 是否递归属性的关联查询
     * @return
     */
    default Serializable _add_(HttpServletRequest request, T entity, boolean recursion) {
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

        // 如果有级联
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

            @Override
            public boolean isRecursion() {
                return recursion;
            }
        }.add();

        return (Serializable) id;
    }

    default void _delete_(HttpServletRequest request, Serializable... ids) {

    }

    default void _edit_(HttpServletRequest request, T entity) {

    }

}
