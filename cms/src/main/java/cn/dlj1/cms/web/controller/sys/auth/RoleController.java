package cn.dlj1.cms.web.controller.sys.auth;

import cn.dlj1.cms.controller.*;
import cn.dlj1.cms.service.Service;
import cn.dlj1.cms.web.auth.annotation.Menu;
import cn.dlj1.cms.web.entity.sys.auth.Role;
import cn.dlj1.cms.web.service.sys.auth.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sys/auth/role")
@Menu("角色管理")
public class RoleController implements
        MainController<Role>,
        TableController<Role>,
        ViewController<Role, Long>,
        AddController<Role>,
        EditController<Role>,
        SelectController<Role>,
        DeleteController<Role, Long> {

    @Autowired
    private RoleService roleService;

    @Override
    public Service getService() {
        return roleService;
    }

}
