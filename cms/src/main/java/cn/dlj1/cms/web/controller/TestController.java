package cn.dlj1.cms.web.controller;

import cn.dlj1.cms.controller.ExportController;
import cn.dlj1.cms.controller.MainController;
import cn.dlj1.cms.controller.TableController;
import cn.dlj1.cms.db.key.Key;
import cn.dlj1.cms.service.Service;
import cn.dlj1.cms.web.entity.Test;
import cn.dlj1.cms.web.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController implements
        MainController,
        TableController,
        ExportController {

    @Autowired
    private TestService testService;

    @Override
    public Service getService() {
        return testService;
    }
}
