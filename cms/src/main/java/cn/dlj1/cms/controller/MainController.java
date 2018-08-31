package cn.dlj1.cms.controller;

import cn.dlj1.cms.db.key.Key;
import cn.dlj1.cms.entity.Entity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 模块主页面接口
 *
 * @param <K>
 * @param <T>
 */
public interface MainController<K extends Key, T extends Entity> extends Controller<K, T> {

    static Log log = LogFactory.getLog(MainController.class);

    /**
     * 模块主展示页面
     * 入口是两个 一个默认模块根目录 一个main.view
     *
     * @param mav
     * @return
     */
    @RequestMapping(value = {"", "main.view"})
    default ModelAndView mainView(ModelAndView mav) {
        mav.setViewName(getModulePath());
        return mav;
    }

}
