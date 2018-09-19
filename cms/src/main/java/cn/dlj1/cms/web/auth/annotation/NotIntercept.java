package cn.dlj1.cms.web.auth.annotation;

import java.lang.annotation.*;

/**
 * 不拦截此方法
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NotIntercept {
}
