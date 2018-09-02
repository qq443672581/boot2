package cn.dlj1.cms.utils;

import cn.dlj1.cms.entity.Entity;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * 类工具
 *
 * @author fivewords(443672581@qq.com)
 * @date 2017年7月20日
 */
public class ClassUtils {

    /**
     * 递归获取本类的字段以及父类的字段<br>
     *
     * @param clazz
     * @return
     */
    public static Field[] getFields(Class<?> clazz, boolean filterStatic) {
        List<Field> list = new ArrayList<Field>();

        Class<?> _clazz = clazz;
        while (_clazz != Object.class) {
            Field[] fields = _clazz.getDeclaredFields();
            for (Field field : fields) {
                if (filterStatic && Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                list.add(field);
            }

            _clazz = _clazz.getSuperclass();
        }
        Field[] fieldArr = new Field[list.size()];
        for (int i = 0; i < fieldArr.length; i++) {
            fieldArr[i] = list.get(i);
        }
        return fieldArr;
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
