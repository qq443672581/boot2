package cn.dlj1.cms.web.service;

import cn.dlj1.cms.dao.Dao;
import cn.dlj1.cms.db.key.Key;
import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.service.impl.TableServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TestService extends TableServiceImpl<Key.Long, Entity>{

    @Override
    public Dao getDao() {
        return null;
    }

}
