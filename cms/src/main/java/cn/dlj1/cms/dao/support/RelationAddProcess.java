package cn.dlj1.cms.dao.support;

import cn.dlj1.cms.dao.Dao;
import cn.dlj1.cms.dao.annotation.MoreToMore;
import cn.dlj1.cms.dao.annotation.OneToMore;
import cn.dlj1.cms.dao.annotation.OneToOne;
import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.exception.MessageException;
import org.apache.commons.lang.reflect.FieldUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.List;

public interface RelationAddProcess {

    HttpServletRequest getRequest();

    Entity getEntity();

    boolean isRecursion();

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
        } catch (Exception e) {
            e.printStackTrace();
            throw new MessageException("系统错误!");
        }

    }

    default void process() throws IllegalAccessException {
        Field[] fields = getFields();
        for (int i = 0; i < getFields().length; i++) {
            Field field = fields[i];

            if (null != field.getAnnotation(OneToOne.class)) {
                oneToOne(field);
                continue;
            }
            if (null != getFields()[i].getAnnotation(OneToMore.class)) {
                oneToMore(field);
                continue;
            }
            if (null != getFields()[i].getAnnotation(MoreToMore.class)) {
                MoreToMore(field);
                continue;
            }


        }
    }

    default void oneToOne(Field field) throws IllegalAccessException {
        // 一对一
        OneToOne oneToOne = field.getAnnotation(OneToOne.class);
        Dao dao = (Dao) getContext().getBean(oneToOne.mapper());

        Object relationValue = FieldUtils.readField(getEntity(), oneToOne.field(), true);
        Object relationEntity = FieldUtils.readField(field, getEntity(), true);
        FieldUtils.writeField(relationEntity, oneToOne.one(), relationValue, true);
        dao._add_(getRequest(), (Entity) relationEntity, isRecursion());
    }

    default void oneToMore(Field field) throws IllegalAccessException {
        // 一对多
        OneToMore oneToMore = field.getAnnotation(OneToMore.class);
        Dao dao = (Dao) getContext().getBean(oneToMore.mapper());

        Object relationValue = FieldUtils.readField(getEntity(), oneToMore.field(), true);
        Object relationEntity = FieldUtils.readField(field, getEntity(), true);
        if (null != relationEntity) {
            List relationList = (List) relationEntity;
            for (int j = 0; j < relationList.size(); j++) {
                Object obj = relationList.get(j);
                FieldUtils.writeField(obj, oneToMore.more(), relationValue, true);
                dao._add_(getRequest(), (Entity) obj, isRecursion());
            }
        }

    }

    default void MoreToMore(Field field) throws IllegalAccessException {
        MoreToMore moreToMore = field.getAnnotation(MoreToMore.class);
        Dao dao = (Dao) getContext().getBean(moreToMore.middleMapper());

        // 关联字段
        Object relationValue = FieldUtils.readField(getEntity(), moreToMore.field(), true);
        Object relationEntity = FieldUtils.readField(field, getEntity(), true);
        if (null != relationEntity) {
            List relationList = (List) relationEntity;
            for (int j = 0; j < relationList.size(); j++) {
                Object obj = relationList.get(j);
                // 关联字段
                Object fieldValue = FieldUtils.readField(obj, moreToMore.more(), true);
                try {
                    Object middleEntity = moreToMore.middleClazz().newInstance();

                    FieldUtils.writeField(middleEntity, moreToMore.leftField(), relationValue, true);
                    FieldUtils.writeField(middleEntity, moreToMore.rightField(), fieldValue, true);

                    dao._add_(getRequest(), (Entity) middleEntity, isRecursion());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
