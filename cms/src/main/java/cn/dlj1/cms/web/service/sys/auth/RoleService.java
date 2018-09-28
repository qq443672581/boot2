package cn.dlj1.cms.web.service.sys.auth;

import cn.dlj1.cms.dao.Dao;
import cn.dlj1.cms.service.ActionService;
import cn.dlj1.cms.service.TableService;
import cn.dlj1.cms.web.dao.sys.auth.RoleDao;
import cn.dlj1.cms.web.entity.sys.auth.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class RoleService implements
        TableService<Role>,
        ActionService<Role> {

    @Autowired
    private RoleDao dao;

    @Override
    public Dao getDao() {
        return dao;
    }

}

