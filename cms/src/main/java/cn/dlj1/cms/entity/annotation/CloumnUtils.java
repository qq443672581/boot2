package cn.dlj1.cms.entity.annotation;

import java.lang.reflect.Field;

public class CloumnUtils {

    public static Cloumn get(Field field) {
        Cloumn cloumn = field.getAnnotation(Cloumn.class);
        if (null == cloumn)
            return null;
        return cloumn;
    }

    public static String getName(Field field) {
        Cloumn cloumn = get(field);
        if (null == cloumn)
            return null;
        else
            return cloumn.value();
    }

    public static boolean isSearch(Field field) {
        Cloumn cloumn = get(field);
        if (null == cloumn)
            return true;
        else
            return cloumn.search();
    }

}
