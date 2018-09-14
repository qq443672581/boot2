package cn.dlj1.cms.web.controller;

import cn.dlj1.cms.controller.*;
import cn.dlj1.cms.service.Service;
import cn.dlj1.cms.web.entity.User;
import cn.dlj1.cms.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController implements
        MainController<User>,
        TableController<User>,
        ViewController<User, Long>,
        AddController<User>,
        EditController<User>,
        UploadController<User>,
        DeleteController<User, Long>,
        ExportController<User> {

    @Autowired
    private UserService userService;

    @Override
    public Service getService() {
        return null;
    }


}
