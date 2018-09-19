package cn.dlj1.cms.web.interceptor;

import cn.dlj1.cms.web.auth.annotation.NotIntercept;
import cn.dlj1.cms.web.auth.session.Session;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;

/**
 * 权限控制
 */
public class AuthInterceptor extends AbstractAuthInterceptor {

    public AuthInterceptor(WebApplicationContext context) {
        super(context);
    }

    public boolean isIntercept(HandlerMethod handler) {
        if (null != handler.getBeanType().getAnnotation(NotIntercept.class)
                || null != handler.getMethod().getAnnotation(NotIntercept.class)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean doService(String authToken, HandlerMethod handler, Session session) {
        boolean has = session.hasMenu(handler.getBeanType().getName(), handler.getMethod().getName());
        if (has) {
            return true;
        }

        return false;
    }
}
