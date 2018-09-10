package cn.dlj1.cms.entity.annotation;

import com.baomidou.mybatisplus.annotation.TableField;

import java.lang.reflect.Field;

public class TableFieldUtils {

    public static TableField get(Field field) {
        TableField tableField = field.getAnnotation(TableField.class);
        if (null == tableField)
            return null;
        return tableField;
    }

    public static String getName(Field field) {
        TableField tableField = get(field);
        if (null == tableField || "".equals(tableField.value())) {
            return field.getName();
        }
        return tableField.value();

    }

}
