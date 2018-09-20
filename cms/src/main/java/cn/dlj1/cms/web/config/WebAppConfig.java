package cn.dlj1.cms.web.config;

import cn.dlj1.cms.web.filter.RequestFilter;
import cn.dlj1.cms.web.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web 配置 <br/>
 * <p>
 * 拦截器、过滤器 <br/>
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Autowired
    private WebApplicationContext context;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(context)).addPathPatterns("/**");
    }

    @Bean
    public FilterRegistrationBean testFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new RequestFilter());
        registration.addUrlPatterns("/*");
        registration.setName("RequestFilter");
        return registration;
    }

}
