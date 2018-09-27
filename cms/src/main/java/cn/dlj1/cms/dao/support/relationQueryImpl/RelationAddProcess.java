package cn.dlj1.cms.dao.support.relationQueryImpl;

import cn.dlj1.cms.dao.Dao;
import cn.dlj1.cms.dao.annotation.MoreToMore;
import cn.dlj1.cms.dao.annotation.OneToMore;
import cn.dlj1.cms.dao.annotation.OneToOne;
import cn.dlj1.cms.dao.support.RelationFieldCache;
import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.entity.annotation.CloumnUtils;
import cn.dlj1.cms.exception.MessageException;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.apache.commons.lang.reflect.FieldUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public interface RelationAddProcess {

    HttpServletRequest getRequest();

    Entity getEntity();

    default Field[] getFields() {
        return RelationFieldCache.getRelationFields(getEntity().getClass());
    }

    default ApplicationContext getContext() {
        return WebApplicationContextUtils.getWebApplicationContext(getRequest().getServletContext());
    }

    default void check() {
        if (getFields().length == 0) {
            return;
        }
    }

    default void add() {
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
        for (int i = 0; i < getFields().length; i++) {
            Field field = fields[i];
            Object relationObj = FieldUtils.readField(field, getEntity(), true);
            if (null == relationObj) {
                continue;
            }

            if (null != field.getAnnotation(OneToOne.class)) {
                if (!(relationObj instanceof Entity)) {
                    throw new MessageException(String.format("字段[%s]不支持的数据类型!", CloumnUtils.getName(field)));
                }
                oneToOne(field, (Entity) relationObj);
                continue;
            }
            if (null != getFields()[i].getAnnotation(OneToMore.class)) {
                if (!(relationObj instanceof List)) {
                    throw new MessageException(String.format("字段[%s]不支持的数据类型!", CloumnUtils.getName(field)));
                }
                oneToMore(field, (List<Entity>) relationObj);
                continue;
            }
            if (null != getFields()[i].getAnnotation(MoreToMore.class)) {
                if (!(relationObj instanceof List)) {
                    throw new MessageException(String.format("字段[%s]不支持的数据类型!", CloumnUtils.getName(field)));
                }
                MoreToMore(field, (List<Entity>) relationObj);
                continue;
            }


        }
    }

    default void oneToOne(Field field, Entity oneData) throws IllegalAccessException {
        // 一对一
        OneToOne oneToOne = field.getAnnotation(OneToOne.class);
        Dao dao = (Dao) getContext().getBean(oneToOne.mapper());

        Object relationValue = FieldUtils.readField(getEntity(), oneToOne.field(), true);
        FieldUtils.writeField(oneData, oneToOne.one(), relationValue, true);
        dao._add_(getRequest(), oneData);
    }

    default void oneToMore(Field field, List<Entity> moreData) throws IllegalAccessException {
        // 一对多
        OneToMore oneToMore = field.getAnnotation(OneToMore.class);
        Dao dao = (Dao) getContext().getBean(oneToMore.mapper());

        Object relationValue = FieldUtils.readField(getEntity(), oneToMore.field(), true);
        for (int j = 0; j < moreData.size(); j++) {
            Entity ele = moreData.get(j);
            FieldUtils.writeField(ele, oneToMore.more(), relationValue, true);
            dao._add_(getRequest(), ele);
        }

    }

    default void MoreToMore(Field field, List<Entity> moreData) throws IllegalAccessException {
        MoreToMore moreToMore = field.getAnnotation(MoreToMore.class);
        Dao middleDao = (Dao) getContext().getBean(moreToMore.middleMapper());
        Class middleClazz = (Class) ((ParameterizedType) moreToMore.middleMapper().getGenericInterfaces()[0]).getActualTypeArguments()[0];

        // 关联字段
        Object relationValue = FieldUtils.readField(getEntity(), moreToMore.field(), true);

        for (int j = 0; j < moreData.size(); j++) {
            Entity ele = moreData.get(j);
            // 关联字段
            Object fieldValue = FieldUtils.readField(ele, moreToMore.more(), true);
            try {
                Entity middleEntity = (Entity) middleClazz.newInstance();

                FieldUtils.writeField(middleEntity, moreToMore.leftField(), relationValue, true);
                FieldUtils.writeField(middleEntity, moreToMore.rightField(), fieldValue, true);

                middleDao._add_(getRequest(), middleEntity);
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

}
