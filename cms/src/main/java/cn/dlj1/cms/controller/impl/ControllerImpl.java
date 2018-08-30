package cn.dlj1.cms.controller.impl;

import cn.dlj1.cms.controller.Controller;
import cn.dlj1.cms.db.key.Key;
import cn.dlj1.cms.entity.Entity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 上层基础实现
 *
 * @param <K>
 * @param <T>
 */
public abstract class ControllerImpl<K extends Key, T extends Entity>
        implements Controller<K, T> {

    private static Log log = LogFactory.getLog(ControllerImpl.class);

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public HttpServletRequest getRequest() {
        return httpServletRequest;
    }

    @Override
    public abstract String getModulePath();

    @RequestMapping("")
    @Override
    public ModelAndView mainView(ModelAndView mav) {
        mav.setViewName(getModulePath());
        return mav;
    }
}
