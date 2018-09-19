package cn.dlj1.cms.entity.annotation;

import java.lang.annotation.*;

/**
 * 下拉模块
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SelectModule {

    String text();

    String value();

    String order() default "";

    boolean asc() default true;
}
