package cn.dlj1.cms.dao;

import cn.dlj1.cms.db.key.Key;
import cn.dlj1.cms.entity.Entity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 接口
 */
public interface Dao<K, T extends Entity> {

    @Select("select * from ${table} where id = #{id}")
    T selectOneById(@Param("table") String table, @Param("id") K id);

    @Select("select * from ${table}")
    List<T> selectAll(@Param("table") String table);

}
