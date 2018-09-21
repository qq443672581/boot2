package cn.dlj1.cms.dao.support;

import cn.dlj1.cms.dao.annotation.MoreToMore;
import cn.dlj1.cms.dao.annotation.OneToMore;
import cn.dlj1.cms.dao.annotation.OneToOne;
import cn.dlj1.cms.utils.ClassUtils;
import com.baomidou.mybatisplus.annotation.TableId;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 关联查询 的字段缓存
 */
public class RelationFieldCache {

    final static Object lock = new Object();
    // 实体的关联字段
    public static Map<String, Field[]> RELATION_FIELD = new HashMap<>();
    // 实体的主键字段
    public static Map<String, Field> ENTITY_PRIMARY_KEY = new HashMap<>();

    public static Field[] getRelationFields(Class clazz) {
        Field[] fields = RELATION_FIELD.get(clazz.getName());
        if (null != fields) {
            return fields;
        }
        synchronized (lock) {
            if (null != fields) {
                return fields;
            }
            List<Field> list = new ArrayList<>();
            Field[] fs = ClassUtils.getFields(clazz);
            for (int i = 0; i < fs.length; i++) {
                if (null != fs[i].getAnnotation(OneToOne.class)
                        || null != fs[i].getAnnotation(OneToMore.class)
                        || null != fs[i].getAnnotation(MoreToMore.class)
                        ) {
                    list.add(fs[i]);
                }
            }
            RELATION_FIELD.put(clazz.getName(), list.toArray(new Field[list.size()]));
        }
        return RELATION_FIELD.get(clazz.getName());
    }

    public static Field getEntityPkField(Class clazz) {
        Field pk = ENTITY_PRIMARY_KEY.get(clazz.getName());
        if (null != pk) {
            return pk;
        }
        synchronized (lock) {
            if (null != pk) {
                return pk;
            }
            Field[] fs = ClassUtils.getFields(clazz);
            for (int i = 0; i < fs.length; i++) {
                if (null != fs[i].getAnnotation(TableId.class)) {
                    pk = fs[i];
                }
            }
            ENTITY_PRIMARY_KEY.put(clazz.getName(), pk);
        }
        pk = ENTITY_PRIMARY_KEY.get(clazz.getName());
        if (null == pk) {
            throw new RuntimeException(String.format("实体[%s]主键不存在!", clazz.getName()));
        }
        return pk;
    }

}
