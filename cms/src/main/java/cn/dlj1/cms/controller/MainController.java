package cn.dlj1.cms.controller;

import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.response.Result;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 模块主页面接口
 */
public interface MainController<T extends Entity> extends Controller<T> {

    static Log log = LogFactory.getLog(MainController.class);

    /**
     * 模块主展示页面
     * 入口是两个 一个默认模块根目录 一个main
     *
     * @return
     */
    @RequestMapping(value = {"", "main"}, method = RequestMethod.GET)
    @ResponseBody
    default Result mainView() {
        return new Result.Success(String.format("module:%s", getModuleClazz().getSimpleName()));
    }

}
