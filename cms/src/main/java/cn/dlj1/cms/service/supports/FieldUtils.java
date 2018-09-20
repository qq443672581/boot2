package cn.dlj1.cms.service.supports;

import cn.dlj1.cms.entity.annotation.CloumnUtils;
import cn.dlj1.cms.entity.annotation.TableFieldUtils;
import cn.dlj1.cms.exception.MessageException;
import cn.dlj1.cms.utils.ClassUtils;
import com.baomidou.mybatisplus.annotation.TableField;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实体字段属性工具类
 */
public class FieldUtils {

    private static final Map<String, String[]> searchFields = new HashMap<>();

    /**
     * 获取可被查询的字段
     *
     * @param clazz
     * @return
     */
    public static String[] getSearchFields(Class clazz) {
        if (null != searchFields.get(clazz.getName())) {
            return searchFields.get(clazz.getName());
        }
        Field[] fields = ClassUtils.getFields(clazz);
        List<String> fs = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            if (!CloumnUtils.isSearch(fields[i])
                    || fields[i].getModifiers() == Modifier.TRANSIENT
                    || null == fields[i].getAnnotation(TableField.class)
                    || !fields[i].getAnnotation(TableField.class).exist()
                    ) {
                continue;
            }
            fs.add(TableFieldUtils.getName(fields[i]));
        }
        String[] s = new String[fs.size()];
        fs.toArray(s);
        searchFields.put(clazz.getName(), s);

        return searchFields.get(clazz.getName());
    }

    /**
     * 检查字段是否可以被搜索
     *
     * @param clazz
     * @param fields
     */
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
