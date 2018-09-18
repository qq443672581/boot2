package cn.dlj1.cms.web.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.*;
import java.io.IOException;

public class RequestFilter implements Filter {

    private static Log log = LogFactory.getLog(RequestFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        long time = System.currentTimeMillis();
        filterChain.doFilter(servletRequest, servletResponse);
        if (log.isDebugEnabled()) {
            log.debug("请求响应时间:" + (System.currentTimeMillis() - time) + "ms");
        }

    }

    @Override
    public void destroy() {

    }
}
