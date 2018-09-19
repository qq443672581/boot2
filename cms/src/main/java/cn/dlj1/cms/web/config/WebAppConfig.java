package cn.dlj1.cms.web.config;

import cn.dlj1.cms.web.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web 配置
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Autowired
    private WebApplicationContext context;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(context)).addPathPatterns("/**");
    }

}
