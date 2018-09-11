package cn.dlj1.cms.controller;

import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.service.ActionService;
import cn.dlj1.cms.service.Service;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 控制器顶级接口
 *
 *
 */
public interface ControllerSuper<T extends Entity> {

    /**
     * 获取服务实现类
     * 通过下层的类来注入
     *
     * @return
     */
    Service getService();

    ActionService getActionService();

    /**
     * 获取模块的路径
     *
     * @return
     */
    String getModulePath();


}
