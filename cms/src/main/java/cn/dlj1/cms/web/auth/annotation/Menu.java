package cn.dlj1.cms.web.auth.annotation;

import java.lang.annotation.*;

/**
 * 菜单注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Menu {

    /**
     * 菜单名
     *
     * @return
     */
    String value();

    /**
     * 是否是一个页面操作<br>
     * 添加按钮、删除操作等，那此时对应的权限key <br>
     * 如果默认值 那就是一个非操作菜单
     *
     * @return
     */
    String key() default "";

}
