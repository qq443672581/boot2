package cn.dlj1.cms.entity.support;

import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.utils.ClassUtils;
import com.baomidou.mybatisplus.annotation.TableId;
import org.springframework.util.Assert;

import java.lang.reflect.Field;

public class EntityUtils {

    public static String getEntityPk(Class<? extends Entity> clazz) {
        Assert.notNull(clazz, "类型不能为空!");
        Field[] fields = ClassUtils.getFields(clazz);
        for (Field field : fields) {
            if (null != field.getAnnotation(TableId.class)) {
                return field.getName();
            }
        }
        throw new RuntimeException(String.format("Class[%s]不存在主键!", clazz.getName()));
    }

}
