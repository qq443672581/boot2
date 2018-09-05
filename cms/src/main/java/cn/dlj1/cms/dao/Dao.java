package cn.dlj1.cms.dao;

import cn.dlj1.cms.entity.Entity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * dao顶级接口
 * <p>
 * 接口
 */
public interface Dao<T extends Entity> extends BaseMapper<T> {

    List<Map<String, Object>> selectPage(Page<T> page, @Param("map") Map map);

}
