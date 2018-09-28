package cn.dlj1.cms.web.service.sys.auth;

import cn.dlj1.cms.dao.Dao;
import cn.dlj1.cms.service.ActionService;
import cn.dlj1.cms.service.ExportService;
import cn.dlj1.cms.service.TableService;
import cn.dlj1.cms.utils.Security;
import cn.dlj1.cms.web.dao.sys.auth.AdminDao;
import cn.dlj1.cms.web.dao.sys.auth.MenuDao;
import cn.dlj1.cms.web.entity.sys.auth.Admin;
import cn.dlj1.cms.web.entity.sys.auth.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuService implements
        TableService<Menu>,
        ActionService<Menu>,
        ExportService<Menu> {

    @Autowired
    private MenuDao dao;

    @Override
    public Dao getDao() {
        return dao;
    }

}

