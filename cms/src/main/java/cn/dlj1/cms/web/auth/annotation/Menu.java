package cn.dlj1.cms.web.auth.annotation;

import java.lang.annotation.*;

/**
 * 菜单注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Menu {

    String value();

}
