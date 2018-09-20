package cn.dlj1.cms.entity.annotation;

import java.lang.annotation.*;

/**
 * 标注于实体之上
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Table {

    /**
     * 模块名
     *
     * @return
     */
    String value();

}
