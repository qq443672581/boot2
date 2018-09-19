package cn.dlj1.cms.web.auth.menu.impl;

import cn.dlj1.cms.web.auth.menu.Menu;
import cn.dlj1.cms.web.auth.menu.SystemMenuScanner;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
public class DefaultSystemMenuScanner implements SystemMenuScanner {

    private Menu[] menus;

    @Override
    public Menu[] getMenus() {
        return menus;
    }

    public DefaultSystemMenuScanner(RequestMappingHandlerMapping handlerMapping) {
        Map<String, String> map = new HashMap<>();

        Map<RequestMappingInfo, HandlerMethod> mm = handlerMapping.getHandlerMethods();
        HandlerMethod handlerMethod = null;
        Class<?> beanType = null;
        Method method = null;
        cn.dlj1.cms.web.auth.annotation.Menu beanMenu = null;
        cn.dlj1.cms.web.auth.annotation.Menu methodMenu = null;
        for (RequestMappingInfo info : mm.keySet()) {
            handlerMethod = mm.get(info);
            beanType = handlerMethod.getBeanType();
            method = handlerMethod.getMethod();

            beanMenu = beanType.getAnnotation(cn.dlj1.cms.web.auth.annotation.Menu.class);
            methodMenu = method.getAnnotation(cn.dlj1.cms.web.auth.annotation.Menu.class);
            if (null == beanMenu || null == methodMenu) {
                continue;
            }

            if (null == map.get(beanType.getName())) {
                map.put(beanType.getName(), beanMenu.value());
            }

            map.put(beanType.getName() + "." + method.getName(), methodMenu.value());
        }

        //
        System.out.println(JSON.toJSONString(map));

    }

}
