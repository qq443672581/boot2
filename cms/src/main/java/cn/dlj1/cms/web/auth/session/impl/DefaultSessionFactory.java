package cn.dlj1.cms.web.auth.session.impl;

import cn.dlj1.cms.utils.RandomUtils;
import cn.dlj1.cms.web.auth.menu.Menu;
import cn.dlj1.cms.web.auth.session.Session;
import cn.dlj1.cms.web.auth.session.SessionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Configuration
public class DefaultSessionFactory implements SessionFactory {

    @Bean
    @ConditionalOnMissingBean
    public SessionFactory sessionFactory() {
        return new DefaultSessionFactory();
    }

    private static final Map<String, Session> sessions = new HashMap<>();

    @Override
    public boolean has(String authToken) {
        return null != get(authToken);
    }

    @Override
    public String create(Set<String> menus) {
        String id = RandomUtils.getUUID() + RandomUtils.getUUID();
        Session session = new DefaultSession(id, menus);
        sessions.put(id, session);
        return id;
    }

    @Override
    public String create() {
        return create(null);
    }

    @Override
    public Session get(String authToken) {
        Session session = sessions.get(authToken);
        if (null != session && session.getTimeout() < System.currentTimeMillis()) {
            remove(authToken);
            return null;
        }
        return session;
    }

    @Override
    public void remove(String authToken) {
        sessions.remove(authToken);
    }

    @Override
    public long size() {
        return sessions.size();
    }

    @Override
    public Map<String, Session> sessions() {
        return sessions;
    }
}
