package cn.dlj1.cms.web.dao.sys.auth;

import cn.dlj1.cms.dao.Dao;
import cn.dlj1.cms.web.entity.sys.auth.Admin;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminDao extends Dao<Admin> {

//    @Override
//    List<Map<String, Object>> selectMaps(@Param("ew") Wrapper<Admin> wrapper);

    @Override
    Admin selectOne(@Param("ew") Wrapper<Admin> wrapper);
}

