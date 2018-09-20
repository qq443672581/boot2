package cn.dlj1.cms.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

public class RequestFilter implements Filter {

    private static Logger log = LoggerFactory.getLogger(RequestFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");

        long time = System.currentTimeMillis();
        filterChain.doFilter(servletRequest, servletResponse);
        if (log.isDebugEnabled()) {
            log.debug("Request Response Time:{} ms", (System.currentTimeMillis() - time));
        }

    }

    @Override
    public void destroy() {

    }

}
