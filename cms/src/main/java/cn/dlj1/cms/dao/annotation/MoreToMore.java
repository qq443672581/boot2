package cn.dlj1.cms.dao.annotation;

import cn.dlj1.cms.entity.Entity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.lang.annotation.*;

/**
 * 多对多查询 <br>
 * <br>
 * 需要引入了中间表 <br>
 * <br>
 *
 * left_table           middle_table            right_table
 *    id        left_table_id  right_table_id       id
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MoreToMore {

    /**
     * 本注解类需要关联的字段
     * 别名：左表
     *
     * @return
     */
    String field() default "id";

    /**
     * 中间表的mapper
     *
     * @return
     */
    Class<? extends BaseMapper> middleMapper();

    /**
     * 左表在中间表的字段，左字段
     *
     * @return
     */
    String leftField();

    /**
     * 右表在中间表中的字段，右字段
     *
     * @return
     */
    String rightField();

    /**
     * 右表的mapper
     *
     * @return
     */
    Class<? extends BaseMapper> mapper();

    /**
     * 关联的表的字段
     * 别名：右表
     *
     * @return
     */
    String more() default "id";


}
