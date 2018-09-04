package cn.dlj1.cms.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * 类工具
 *
 * @author fivewords(443672581@qq.com)
 * @date 2017年7月20日
 */
public class ClassUtils {

    private static final Map<String, Field[]> fields = new HashMap<>();
    private static byte[] lock = new byte[0];

    /**
     * 递归获取本类的字段以及父类的字段<br>
     *
     * @param clazz
     * @return
     */
    public static Field[] getFields(Class<?> clazz, boolean filterStatic) {
        if (null != fields.get(clazz.getName())) {
            return fields.get(clazz.getName());
        }
        synchronized (lock) {
            if (null != fields.get(clazz.getName())) {
                return fields.get(clazz.getName());
            }
            List<Field> list = new ArrayList<Field>();

            Class<?> _clazz = clazz;
            while (_clazz != Object.class) {
                List<Field> list2 = new ArrayList<Field>();
                Field[] fields = _clazz.getDeclaredFields();
                for (Field field : fields) {
                    if (filterStatic && Modifier.isStatic(field.getModifiers())) {
                        continue;
                    }
                    list2.add(field);
                }
                Collections.reverse(list2);
                list.addAll(list2);

                _clazz = _clazz.getSuperclass();
            }
            Collections.reverse(list);
            Field[] fs = new Field[list.size()];
            list.toArray(fs);
            fields.put(clazz.getName(), fs);

        }
        return fields.get(clazz.getName());
    }

    /**
     * 递归获取本类的字段以及父类的字段<br>
     * 过滤静态字段
     *
     * @param clazz
     * @return
     */
    public static Field[] getFields(Class<?> clazz) {
        return getFields(clazz, true);
    }

    /**
     * 获取实体的字段数组
     *
     * @param clazz
     * @return
     */
    public static String[] getStringFields(Class<?> clazz) {
        Field[] fields = getFields(clazz, true);
        if (null == fields || fields.length == 0) {
            return new String[0];
        }
        String[] fs = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fs[i] = fields[i].getName();
        }
        return fs;
    }

    public static Field find(Class<?> clazz, String field) {
        Field[] fields = getFields(clazz);
        for (Field f : fields) {
            if (f.getName().equals(field)) {
                return f;
            }

        }
        return null;
    }

    /**
     * 获取字段的 set方法
     *
     * @param name
     * @return
     */
    public static String setMethodName(String name) {
        return "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    /**
     * 获取字段的get方法
     *
     * @param field
     * @param isBoolean
     * @return
     */
    public static String getMethodName(String field, boolean isBoolean) {
        String prefix = "get";
        if (isBoolean) {
            prefix = "is";
        }
        return prefix + field.substring(0, 1).toUpperCase() + field.substring(1);
    }

    /**
     * 获取非布尔类型字段的get方法
     *
     * @param field
     * @return
     */
    public static String getMethodName(String field) {
        return getMethodName(field, false);
    }

    /**
     * 判断类型获取字段的get方法
     *
     * @param field
     * @return
     */
    public static String getMethodName(Field field) {
        String name = field.getName();
        if (field.getType() == boolean.class || field.getType() == Boolean.class) {
            return getMethodName(name, true);
        }
        return getMethodName(name, false);
    }

    /**
     * 获取类泛型的类型
     *
     * @param clazz
     * @param index
     * @return
     */
    public static Class<?> getGenericityClazz(Class<?> clazz, int index) {
        ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericInterfaces()[0];
        if (null == parameterizedType || parameterizedType.getActualTypeArguments().length == 0) {
            return null;
        }
        return parameterizedType.getActualTypeArguments()[index].getClass();
    }

}
