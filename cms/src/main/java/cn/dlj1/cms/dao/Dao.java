package cn.dlj1.cms.dao;

import cn.dlj1.cms.dao.annotation.MoreToMore;
import cn.dlj1.cms.dao.annotation.OneToMore;
import cn.dlj1.cms.dao.annotation.OneToOne;
import cn.dlj1.cms.dao.support.RelationFieldCache;
import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.exception.MessageException;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.commons.lang.reflect.FieldUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * dao顶级接口
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

    default Serializable add_(HttpServletRequest request, T entity) {
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

        // 如果有级联注解
        Field[] relationFields = RelationFieldCache.getRelationFields(entity.getClass());
        if (relationFields.length == 0) {
            return (Serializable) id;
        }

        ApplicationContext context =
                WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
        for (int i = 0; i < relationFields.length; i++) {
            if (null != relationFields[i].getAnnotation(OneToOne.class)) {
                // 一对一
                OneToOne oneToOne = relationFields[i].getAnnotation(OneToOne.class);
                Dao dao = (Dao) context.getBean(oneToOne.mapper());
                try {
                    Object relationValue = FieldUtils.readField(entity, oneToOne.field(), true);
                    Object relationEntity = FieldUtils.readField(relationFields[i], entity, true);
                    FieldUtils.writeField(relationEntity, oneToOne.one(), relationValue, true);
                    dao.add_(request, (Entity) relationEntity);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else if (null != relationFields[i].getAnnotation(OneToMore.class)) {
                // 一对多
                OneToMore oneToMore = relationFields[i].getAnnotation(OneToMore.class);
                Dao dao = (Dao) context.getBean(oneToMore.mapper());
                try {
                    Object relationValue = FieldUtils.readField(entity, oneToMore.field(), true);
                    Object relationEntity = FieldUtils.readField(relationFields[i], entity, true);
                    if (null != relationEntity) {
                        List relationList = (List) relationEntity;
                        for (int j = 0; j < relationList.size(); j++) {
                            Object obj = relationList.get(j);
                            FieldUtils.writeField(obj, oneToMore.more(), relationValue, true);
                            dao.add_(request, (Entity) obj);
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            } else if (null != relationFields[i].getAnnotation(MoreToMore.class)) {
                MoreToMore moreToMore = relationFields[i].getAnnotation(MoreToMore.class);
                Dao dao = (Dao) context.getBean(moreToMore.middleMapper());
                try {
                    // 关联字段
                    Object relationValue = FieldUtils.readField(entity, moreToMore.field(), true);
                    Object relationEntity = FieldUtils.readField(relationFields[i], entity, true);
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

                                dao.add_(request, (Entity) middleEntity);
                            } catch (InstantiationException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }


        }

        return (Serializable) id;
    }

    default void delete_(HttpServletRequest request, Serializable... ids) {

    }

    default void edit_(HttpServletRequest request, T entity) {

    }

}
