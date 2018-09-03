package cn.dlj1.cms.web.controller;

import cn.dlj1.cms.controller.*;
import cn.dlj1.cms.service.Service;
import cn.dlj1.cms.web.entity.Test;
import cn.dlj1.cms.web.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController implements
        MainController<Test>,
        TableController<Test>,
        AddController<Test>,
        EditController<Test>,
        DeleteController<Test, Long>,
        ExportController<Test> {

    @Autowired
    private TestService testService;

    @Override
    public Service getService() {
        return testService;
    }


}
