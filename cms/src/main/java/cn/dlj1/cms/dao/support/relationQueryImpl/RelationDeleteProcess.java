package cn.dlj1.cms.dao.support.relationQueryImpl;

import cn.dlj1.cms.dao.Dao;
import cn.dlj1.cms.dao.annotation.MoreToMore;
import cn.dlj1.cms.dao.annotation.OneToMore;
import cn.dlj1.cms.dao.annotation.OneToOne;
import cn.dlj1.cms.dao.support.RelationFieldCache;
import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.entity.annotation.CloumnUtils;
import cn.dlj1.cms.entity.annotation.TableFieldUtils;
import cn.dlj1.cms.exception.MessageException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang.reflect.FieldUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public interface RelationDeleteProcess {

    HttpServletRequest getRequest();

    Class getClazz();

    Entity getEntity(Serializable id);

    Serializable getId();

    default Field[] getFields() {
        return RelationFieldCache.getRelationFields(getClazz());
    }

    default ApplicationContext getSpringContext() {
        return WebApplicationContextUtils.getWebApplicationContext(getRequest().getServletContext());
    }

    default void check() {
        // 如果没有级联
        if (getFields().length == 0) {
            return;
        }

    }

    default void delete() {
        check();

        try {
            process();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new MessageException("系统异常!");
        }

    }

    default void process() throws IllegalAccessException {
        Field[] fields = getFields();
        Serializable id = getId();

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];

            if (null != field.getAnnotation(OneToOne.class)) {
                if (!(Entity.class.isAssignableFrom(field.getType()))) {
                    throw new MessageException(String.format("字段[%s]不支持的数据类型!", CloumnUtils.getName(field)));
                }
                oneToOne(field, id);
                continue;
            }
            if (null != getFields()[i].getAnnotation(OneToMore.class)) {
                if (!(List.class.isAssignableFrom(field.getType()))) {
                    throw new MessageException(String.format("字段[%s]不支持的数据类型!", CloumnUtils.getName(field)));
                }
                oneToMore(field, id);
                continue;
            }
            if (null != getFields()[i].getAnnotation(MoreToMore.class)) {
                if (!(List.class.isAssignableFrom(field.getType()))) {
                    throw new MessageException(String.format("字段[%s]不支持的数据类型!", CloumnUtils.getName(field)));
                }
                MoreToMore(field, id);
                continue;
            }


        }
    }

    default String getRelationFieldName(Class mapper, String field) {
        Class moreClazz = (Class) ((ParameterizedType) mapper.getGenericInterfaces()[0]).getActualTypeArguments()[0];
        Field moreField = FieldUtils.getField(moreClazz, field, true);
        if (null == moreField) {
            throw new MessageException("字段不存在!");
        }
        return TableFieldUtils.getName(moreField);
    }

    default void oneToOne(Field field, Serializable id) throws IllegalAccessException {
        OneToOne oneToOne = field.getAnnotation(OneToOne.class);
        Dao oneDao = (Dao) getSpringContext().getBean(oneToOne.mapper());

        Object obj = id;
        // 如果不是主键
        if (!oneToOne.field().equals(RelationFieldCache.getEntityPkField(getClazz()).getName())) {
            // 查询
            Entity entity = getEntity(id);
            obj = FieldUtils.readField(entity, oneToOne.field());
        }
        String oneName = getRelationFieldName(oneToOne.mapper(), oneToOne.one());

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq(oneName, obj);
        oneDao.delete(wrapper);
    }

    default void oneToMore(Field field, Serializable id) throws IllegalAccessException {
        OneToMore oneToMore = field.getAnnotation(OneToMore.class);
        Dao moreDao = (Dao) getSpringContext().getBean(oneToMore.mapper());

        Object obj = id;
        // 如果不是主键
        if (!oneToMore.field().equals(RelationFieldCache.getEntityPkField(getClazz()).getName())) {
            // 查询
            Entity entity = getEntity(id);
            obj = FieldUtils.readField(entity, oneToMore.field());
        }

        String moreName = getRelationFieldName(oneToMore.mapper(), oneToMore.more());
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq(moreName, obj);
        moreDao.delete(wrapper);

    }

    default void MoreToMore(Field field, Serializable id) throws IllegalAccessException {
        MoreToMore moreToMore = field.getAnnotation(MoreToMore.class);
        Dao middleDao = (Dao) getSpringContext().getBean(moreToMore.middleMapper());

        Object obj = id;
        // 如果不是主键
        if (!moreToMore.field().equals(RelationFieldCache.getEntityPkField(getClazz()).getName())) {
            // 查询
            Entity entity = getEntity(id);
            obj = FieldUtils.readField(entity, moreToMore.field());
        }

        String moreName = getRelationFieldName(moreToMore.middleMapper(), moreToMore.leftField());
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq(moreName, obj);
        middleDao.delete(wrapper);
    }

}
