package cn.dlj1.cms.service;

import cn.dlj1.cms.config.GlobalConfig;
import cn.dlj1.cms.dao.Dao;
import cn.dlj1.cms.entity.Entity;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.ParameterizedType;

/**
 * 服务顶级接口
 */
public interface Service<T extends Entity> {

    /**
     * 获取全局配置
     *
     * @return
     */
    default GlobalConfig getGlobalConfig(HttpServletRequest request) {
        return WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()).getBean(GlobalConfig.class);
    }

    /**
     * 获取Dao
     *
     * @return
     */
    Dao<T> getDao();

    /**
     * 获取模块类型
     *
     * @return
     */
    default Class<T> getModuleClazz() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
    }

}
