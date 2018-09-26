package cn.dlj1.cms.web.service.sys.auth;

import cn.dlj1.cms.dao.Dao;
import cn.dlj1.cms.service.ActionService;
import cn.dlj1.cms.service.ExportService;
import cn.dlj1.cms.service.TableService;
import cn.dlj1.cms.utils.Security;
import cn.dlj1.cms.web.dao.sys.auth.AdminDao;
import cn.dlj1.cms.web.entity.sys.auth.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AdminService implements
        TableService<Admin>,
        ActionService<Admin>,
        ExportService<Admin> {

    @Autowired
    private AdminDao dao;

    @Override
    public Dao getDao() {
        return dao;
    }

    @Override
    public void fill(boolean isEdit, Admin entity) {
        if (null != entity.getPassword()) {
            entity.setPassword(Security.md5(entity.getPassword()));
        }
        if (isEdit) {
        }
    }
}

