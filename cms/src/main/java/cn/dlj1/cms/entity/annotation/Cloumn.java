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
     * 注释
     *
     * @return
     */
    String value();

    /**
     * 可被查询 导出的字段
     *
     * @return
     */
    boolean search() default true;

}
