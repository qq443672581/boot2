package cn.dlj1.cms.dao.annotation;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.lang.annotation.*;

/**
 * 一对多查询
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OneToMore {

    /**
     * 本类字段
     *
     * @return
     */
    String field() default "id";

    /**
     * 关联类
     *
     * @return
     */
    Class<? extends BaseMapper> mapper();

    /**
     * 关联类中的关联字段
     *
     * @return
     */
    String more();

}
