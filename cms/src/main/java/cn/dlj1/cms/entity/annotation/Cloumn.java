package cn.dlj1.cms.entity.annotation;

import java.lang.annotation.*;

/**
 * 目录
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Cloumn {

    /**
     * 列名
     *
     * @return
     */
    String value();

    /**
     * 是否可被查询、导出
     *
     * @return
     */
    boolean search() default true;

}
