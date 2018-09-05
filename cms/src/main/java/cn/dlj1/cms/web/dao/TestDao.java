package cn.dlj1.cms.web.dao;

import cn.dlj1.cms.dao.Dao;
import cn.dlj1.cms.web.entity.Test;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TestDao extends Dao<Test> {

    List<Map<String, Object>> selectAll(Page<Test> page, @Param("map") Map map);

}
