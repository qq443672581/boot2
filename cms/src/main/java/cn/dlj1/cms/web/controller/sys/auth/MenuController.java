package cn.dlj1.cms.web.controller.sys.auth;

import cn.dlj1.cms.controller.*;
import cn.dlj1.cms.response.Result;
import cn.dlj1.cms.service.Service;
import cn.dlj1.cms.web.auth.menu.SystemMenuScanner;
import cn.dlj1.cms.web.entity.sys.auth.Menu;
import cn.dlj1.cms.web.service.sys.auth.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sys/auth/menu")
@cn.dlj1.cms.web.auth.annotation.Menu("菜单管理")
public class MenuController implements
        MainController<Menu>,
        TreeController<Menu>,
        ViewController<Menu, Long>,
        AddController<Menu>,
        EditController<Menu>,
        UploadController<Menu>,
        SelectController<Menu>,
        DeleteController<Menu, Long>,
        ExportController<Menu> {

    @Autowired
    private MenuService menuService;

    @Override
    public Service getService() {
        return menuService;
    }

    @Autowired
    private SystemMenuScanner menuScanner;

    @GetMapping("/systemMenus")
    @ResponseBody
    @cn.dlj1.cms.web.auth.annotation.Menu("系统菜单")
    public Result systemMenus() {
        return new Result.Success(menuScanner.getMenus());
    }

}
