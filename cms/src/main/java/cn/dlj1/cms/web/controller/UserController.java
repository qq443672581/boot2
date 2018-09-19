package cn.dlj1.cms.web.controller;

import cn.dlj1.cms.controller.*;
import cn.dlj1.cms.response.Result;
import cn.dlj1.cms.service.Service;
import cn.dlj1.cms.web.auth.annotation.Menu;
import cn.dlj1.cms.web.auth.annotation.NotIntercept;
import cn.dlj1.cms.web.auth.session.SessionFactory;
import cn.dlj1.cms.web.entity.User;
import cn.dlj1.cms.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/user")
@Menu("用户")
public class UserController implements
        MainController<User>,
        TableController<User>,
        ViewController<User, Long>,
        AddController<User>,
        EditController<User>,
        UploadController<User>,
        SelectController<User>,
        DeleteController<User, Long>,
        ExportController<User> {

    @Autowired
    private UserService userService;

    @Override
    public Service getService() {
        return userService;
    }

    @Autowired
    private SessionFactory sessionFactory;

    @GetMapping("/login")
    @ResponseBody
    @NotIntercept
    @Menu("登录")
    public Result login() {
        Set<String> menus = new HashSet<>();
        menus.add("cn.dlj1.cms.web.controller.UserController.view");

        String id = sessionFactory.create(menus);
        return new Result.Success(id);
    }

}
