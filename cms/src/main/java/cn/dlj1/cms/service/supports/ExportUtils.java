package cn.dlj1.cms.service.supports;

import cn.dlj1.cms.entity.annotation.CloumnUtils;
import cn.dlj1.cms.utils.ClassUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

public class ExportUtils {

    private static final Map<String, Map<String, String>> map = new HashMap<>();

    public static Map<String, String> getExportFields(Class clazz) {
        if (null != map.get(clazz.getName())) {
            return map.get(clazz.getName());
        }
        Field[] fields = ClassUtils.getFields(clazz);

        if (fields.length == 0) {
            map.put(clazz.getName(), new Hashtable<>(0));
            return getExportFields(clazz);
        }

        Map<String, String> m = new LinkedHashMap<>(fields.length);
        for (Field field : fields) {
            String name = CloumnUtils.getName(field);
            if (null == name) {
                continue;
            }
            m.put(field.getName(), name);
        }
        map.put(clazz.getName(), m);

        return getExportFields(clazz);
    }

}
