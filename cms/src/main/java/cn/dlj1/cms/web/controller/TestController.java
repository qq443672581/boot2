package cn.dlj1.cms.web.controller;

import cn.dlj1.cms.controller.impl.TableControllerImpl;
import cn.dlj1.cms.db.key.Key;
import cn.dlj1.cms.service.TableService;
import cn.dlj1.cms.web.entity.Test;
import cn.dlj1.cms.web.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TestController extends TableControllerImpl<Key.Long, Test> {

    @Autowired
    private TestService testService;

    @Override
    public TableService getTabeleService() {
        return testService;
    }

    @Override
    public String getModulePath() {
        return "/";
    }


}
