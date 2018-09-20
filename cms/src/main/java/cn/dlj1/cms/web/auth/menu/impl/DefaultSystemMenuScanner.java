package cn.dlj1.cms.web.auth.menu.impl;

import cn.dlj1.cms.web.auth.annotation.Menu;
import cn.dlj1.cms.web.auth.menu.SystemMenuScanner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.*;

@Component
public class DefaultSystemMenuScanner implements SystemMenuScanner {

    private Set<Object> menus = new HashSet<>();

    @Override
    public Set<Object> getMenus() {
        return menus;
    }

    public DefaultSystemMenuScanner(RequestMappingHandlerMapping handlerMapping) {
        Map<String, ControllerMenu> ControllerMap = new HashMap<>();

        Map<RequestMappingInfo, HandlerMethod> mm = handlerMapping.getHandlerMethods();
        HandlerMethod handlerMethod = null;
        Class<?> beanType = null;
        Method method = null;
        Menu beanMenu = null;
        Menu methodMenu = null;
        RequestMapping beanMapping = null;
        String url = null;
        for (RequestMappingInfo info : mm.keySet()) {
            url = info.getPatternsCondition().getPatterns().iterator().next();

            handlerMethod = mm.get(info);

            beanType = handlerMethod.getBeanType();
            method = handlerMethod.getMethod();

            beanMenu = beanType.getAnnotation(Menu.class);
            methodMenu = method.getAnnotation(Menu.class);
            if (null == beanMenu || null == methodMenu) {
                continue;
            }
            beanMapping = beanType.getAnnotation(RequestMapping.class);

            if (null == ControllerMap.get(beanType.getName())) {
                ControllerMap.put(beanType.getName(), new ControllerMenu(beanMenu.value(), beanMapping.value()[0], beanType.getName()));
            }

            String key = "".equals(methodMenu.key()) ? null : methodMenu.key();
            ControllerMenu controllerMenu = (ControllerMenu) ControllerMap.get(beanType.getName());
            controllerMenu.addKey(key);
            controllerMenu.addItem(
                    methodMenu.value(), url, method.getName(), key
            );
        }
        for (Iterator<ControllerMenu> i = ControllerMap.values().iterator(); i.hasNext(); ) {
            menus.add(i.next());
        }

    }

}
