package cn.dlj1.mybatis_plus.controller;

import cn.dlj1.mybatis_plus.dao.AdminDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @Autowired
    AdminDao dao;

    @RequestMapping
    public Object test() {
        return dao.selectMaps(null);
    }
}
