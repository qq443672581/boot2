package cn.dlj1.cms.web.service;

import cn.dlj1.cms.dao.Dao;
import cn.dlj1.cms.service.Component.Table;
import org.springframework.stereotype.Service;

@Service
public class TestService implements Table{

    @Override
    public Dao getDao() {
        return null;
    }
}
