package cn.dlj1.cms.entity.annotation;

import java.lang.annotation.*;

/**
 * 目录
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Cloumn {

    String value();

    boolean export() default true;

}
