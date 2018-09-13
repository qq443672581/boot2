package cn.dlj1.cms.web.service;

import cn.dlj1.cms.config.GlobalConfig;
import cn.dlj1.cms.dao.Dao;
import cn.dlj1.cms.service.ActionService;
import cn.dlj1.cms.service.ExportService;
import cn.dlj1.cms.service.TableService;
import cn.dlj1.cms.web.dao.UserDao;
import cn.dlj1.cms.web.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements
        TableService<User>,
        ActionService<User>,
        ExportService<User> {

    @Autowired
    private UserDao dao;
    @Autowired
    private GlobalConfig config;

    @Override
    public GlobalConfig getGlobalConfig() {
        return config;
    }

    @Override
    public Dao getDao() {
        return dao;
    }

}
