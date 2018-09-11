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
public interface Controller<T extends Entity> extends ControllerSuper<T> {

    /**
     * 获取服务实现类
     * 通过下层的类来注入
     *
     * @return
     */
    Service getService();

    default ActionService getActionService() {
        Assert.notNull(getService(), "核心服务类不能为空!");
        if (!(getService() instanceof ActionService)) {
            throw new RuntimeException(String.format("服务[%s]没有实现操作服务类", getService()));
        }

        return (ActionService) getService();
    }

    /**
     * 获取模块的路径
     *
     * @return
     */
    default String getModulePath() {
        RequestMapping rm = this.getClass().getAnnotation(RequestMapping.class);
        String path = "";
        if (null != rm) {
            String[] values = rm.value();
            if (null != values && values.length > 0) {
                path = values[0] + "/";
            }
        }
        return path;
    }


}
