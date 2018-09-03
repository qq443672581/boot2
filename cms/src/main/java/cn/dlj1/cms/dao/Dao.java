package cn.dlj1.cms.dao;

import cn.dlj1.cms.db.key.Key;
import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.web.entity.Test;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 *
 *
 * 接口
 */
public interface Dao<T extends Entity> extends BaseMapper<T>{

}
