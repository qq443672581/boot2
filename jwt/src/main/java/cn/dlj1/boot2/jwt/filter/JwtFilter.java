package cn.dlj1.boot2.jwt.filter;

import cn.dlj1.boot2.jwt.config.Audience;
import cn.dlj1.boot2.jwt.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.security.auth.login.LoginException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean {

    // 不拦截的接口
    private String[] prefixIignores = {"/login"};
    private Audience audience;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (canIgnore(request)) {
            chain.doFilter(request, response);
            return;
        }

        //等到请求头信息authorization信息
        String token = request.getHeader("jwt_token");
        if (null == token) {
            response.sendError(403, "请登录");
            return;
        }
        if (null == token || !token.startsWith("bearer;")) {
            throw new RuntimeException("x");
        }
        token = token.substring(7);

        try {
            if (audience == null) {
                BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
                audience = factory.getBean(Audience.class);
            }
            Claims claims = JwtUtils.parseJWT(token, audience.getBase64Secret());
            if (claims == null) {
                throw new LoginException("");
            }
            request.setAttribute("jwt_login_info", claims);
        } catch (Exception e) {
            throw new RuntimeException("");
        }

        chain.doFilter(req, res);
    }

    private boolean canIgnore(HttpServletRequest request) {
        boolean isExcludedPage = false;
        for (String page : prefixIignores) {// 判断是否在过滤url之外
            if (((HttpServletRequest) request).getServletPath().equals(page)) {
                isExcludedPage = true;
                break;
            }
        }
        return isExcludedPage;
    }

}
