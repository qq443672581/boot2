package cn.dlj1.cms.web.interceptor;

import cn.dlj1.cms.response.Result;
import cn.dlj1.cms.web.auth.session.Session;
import cn.dlj1.cms.web.auth.session.SessionFactory;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractAuthInterceptor extends HandlerInterceptorAdapter {

    private SessionFactory sessionFactory;
    private static String AUTH_401 = new Result.Fail("权限不足!").toJSON();
    private static String AUTH_LOGIN = new Result.Fail("请登录!").toJSON();

    public AbstractAuthInterceptor(WebApplicationContext context) {
        this.sessionFactory = context.getBean(SessionFactory.class);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object h)
            throws Exception {
        int i=1;
        if(i == 1){
            return true;
        }

        if (!(h instanceof HandlerMethod)) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.getWriter().append(Result.FAIL.toJSON()).close();
            return false;
        }

        HandlerMethod handler = (HandlerMethod) h;

        // 不拦截
        if ((!isIntercept(handler)) || handler.getBeanType() == BasicErrorController.class) {
            return true;
        }

        // 验证token
        String authToken = request.getHeader("auth-token");
        if (null == authToken || (!this.sessionFactory.has(authToken))) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().append(this.AUTH_LOGIN).close();
            return false;
        }

        // 菜单权限过滤
        if (doService(authToken, handler, this.sessionFactory.get(authToken))) {
            return true;
        }

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().append(this.AUTH_401).close();
        return false;
    }

    /**
     * 是否要进行拦截
     *
     * @param handler
     * @return
     */
    public abstract boolean isIntercept(HandlerMethod handler);

    /**
     * 实现
     *
     * @param authToken
     * @param handler
     * @param session
     * @return
     */
    public abstract boolean doService(String authToken, HandlerMethod handler, Session session);

    /**
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作 可在modelAndView中加入数据，比如当前时间
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等
     * <p>
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

}
