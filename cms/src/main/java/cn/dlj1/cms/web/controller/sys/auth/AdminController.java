package cn.dlj1.cms.web.controller.sys.auth;

import cn.dlj1.cms.controller.*;
import cn.dlj1.cms.response.Result;
import cn.dlj1.cms.service.Service;
import cn.dlj1.cms.web.auth.annotation.Menu;
import cn.dlj1.cms.web.auth.annotation.NotIntercept;
import cn.dlj1.cms.web.auth.session.SessionFactory;
import cn.dlj1.cms.web.entity.sys.auth.Admin;
import cn.dlj1.cms.web.service.sys.auth.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/sys/auth/admin")
@Menu("管理员管理")
public class AdminController implements
        MainController<Admin>,
        TableController<Admin>,
        ViewController<Admin, Long>,
        AddController<Admin>,
        EditController<Admin>,
        UploadController<Admin>,
        SelectController<Admin>,
        DeleteController<Admin, Long>,
        ExportController<Admin> {

    @Autowired
    private AdminService adminService;

    @Override
    public Service getService() {
        return adminService;
    }

    @Autowired
    private SessionFactory sessionFactory;

    @GetMapping("/login")
    @ResponseBody
    @NotIntercept
    @Menu("登录")
    public Result login() {
        Set<String> menus = new HashSet<>();
        menus.add("cn.dlj1.cms.web.controller.sys.auth.AdminController.view");

        String id = sessionFactory.create(menus);
        return new Result.Success(id);
    }

}
