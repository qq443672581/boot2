package cn.dlj1.cms.entity.annotation;

import cn.dlj1.cms.entity.Entity;

public class SelectModuleUtils {

    public static SelectModule get(Class<? extends Entity> clazz) {
        SelectModule select = clazz.getAnnotation(SelectModule.class);
        if (null == select)
            return null;
        return select;
    }

    public static String getText(Class<? extends Entity> clazz) {
        SelectModule select = get(clazz);
        if (null == select)
            return null;
        else
            return select.text();
    }

    public static String getValue(Class<? extends Entity> clazz) {
        SelectModule select = get(clazz);
        if (null == select)
            return null;
        else
            return select.value();
    }


}
