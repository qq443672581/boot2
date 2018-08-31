package cn.dlj1.cms.web.controller;

import cn.dlj1.cms.controller.ExportController;
import cn.dlj1.cms.controller.MainController;
import cn.dlj1.cms.controller.TableController;
import cn.dlj1.cms.db.key.Key;
import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.web.entity.Test;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController implements
        MainController<Key.Long,Test>,
        TableController<Key.Long,Test>,
        ExportController<Key.Long,Test>
{

}
