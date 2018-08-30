package cn.dlj1.cms.controller;

import cn.dlj1.cms.db.key.Key;
import cn.dlj1.cms.entity.Entity;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器上层接口
 *
 * @param <K>
 * @param <T>
 */
public interface Controller<K extends Key, T extends Entity> {

    HttpServletRequest getRequest();

    String getModulePath();

    ModelAndView mainView(ModelAndView mav);

}
