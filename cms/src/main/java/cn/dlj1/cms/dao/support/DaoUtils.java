package cn.dlj1.cms.dao.support;

import cn.dlj1.cms.dao.annotation.MoreToMore;
import cn.dlj1.cms.dao.annotation.OneToMore;
import cn.dlj1.cms.dao.annotation.OneToOne;
import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.utils.ClassUtils;
import org.apache.commons.lang.reflect.FieldUtils;

import java.lang.reflect.Field;

/**
 * 工具类
 */
public class DaoUtils {

    /**
     * 数据修改级别
     * <p>
     * 不修改 1|修改自身 2|修改关联属性 3|修改自身和关联属性 4
     *
     * @param entity
     * @return
     */
    public static int editLevel(Entity entity) {
        int flag_this = 0;
        int flag_relation = 0;

        Field pk = RelationFieldCache.getEntityPkField(entity.getClass());
        Object value = null;

        Field[] fields = ClassUtils.getFields(entity.getClass());
        for (Field field : fields) {
            if (field.getName().equals(pk.getName())) {
                continue;
            }
            try {
                value = FieldUtils.readField(field, entity, true);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (null != value) {
                if (null != field.getAnnotation(OneToOne.class) ||
                        null != field.getAnnotation(OneToMore.class) ||
                        null != field.getAnnotation(MoreToMore.class)) {
                    flag_relation = 1;
                } else {
                    flag_this = 1;
                }
            }
        }
        if (flag_this == 0 && flag_relation == 0) {
            return 1;
        }
        if (flag_this == 1 && flag_relation == 0) {
            return 2;
        }
        if (flag_this == 0 && flag_relation == 1) {
            return 3;
        }
        if (flag_this == 1 && flag_relation == 1) {
            return 4;
        }

        return 0;
    }

    /**
     * 一个实体的属性都是空的
     *
     * @param entity
     * @return
     */
    public static boolean entityPropertyIsAllEmpty(Object entity) {
        Object value = null;
        Field[] fields = ClassUtils.getFields(entity.getClass());
        for (Field field : fields) {
            try {
                value = FieldUtils.readField(field, entity, true);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (null != value) {
                return false;
            }
        }
        return true;
    }

    /**
     * 一个实体 只有主键不为空
     *
     * @param entity
     * @return
     */
    public static boolean entityPropertyOnlyPkIsNotNull(Object entity) {
        Object value = null;
        String pk = RelationFieldCache.getEntityPkField(entity.getClass()).getName();
        Field[] fields = ClassUtils.getFields(entity.getClass());
        for (Field field : fields) {
            if (field.getName().equals(pk)) {
                continue;
            }
            try {
                value = FieldUtils.readField(field, entity, true);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (null != value) {
                return false;
            }
        }
        return true;
    }

}
