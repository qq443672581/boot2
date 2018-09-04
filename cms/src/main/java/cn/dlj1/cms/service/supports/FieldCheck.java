package cn.dlj1.cms.service.supports;

import cn.dlj1.cms.entity.annotation.CloumnUtils;
import cn.dlj1.cms.exception.MessageException;
import cn.dlj1.cms.utils.ClassUtils;
import cn.dlj1.cms.utils.JSONUtils;
import cn.dlj1.cms.web.entity.Test;

import java.lang.reflect.Field;

/**
 * Sql数据检查
 */
public class FieldCheck {

    public static void searchFieldCheck(Class clazz, String... fields) {
        if (null == fields || fields.length == 0) {
            fields = ClassUtils.getStringFields(clazz);
        }
        for (String field : fields) {
            Field f = ClassUtils.find(clazz, field);
            if (null == f)
                throw new MessageException(clazz, String.format("字段[%s]不存在", field));

            if (!CloumnUtils.isSearch(f))
                throw new MessageException(clazz, String.format("字段[%s]不可查询", field));


        }

    }


}
