package cn.dlj1.cms.web.service;

import cn.dlj1.cms.dao.Dao;
import cn.dlj1.cms.service.ActionService;
import cn.dlj1.cms.service.ExportService;
import cn.dlj1.cms.service.TableService;
import cn.dlj1.cms.utils.RandomUtils;
import cn.dlj1.cms.web.dao.UserDao;
import cn.dlj1.cms.web.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserService implements
        TableService<User>,
        ActionService<User>,
        ExportService<User> {

    @Autowired
    private UserDao dao;

    @Override
    public Dao getDao() {
        return dao;
    }

}

