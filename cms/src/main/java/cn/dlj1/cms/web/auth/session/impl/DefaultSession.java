package cn.dlj1.cms.web.auth.session.impl;

import cn.dlj1.cms.web.auth.session.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DefaultSession implements Session {

    private Map<String, Object> attributes = null;

    private String id;

    private long timeout;

    private Set<String> menus;

    @Override
    public String getId() {
        return null;
    }

    @Override
    public Object getAttribute(String key) {
        if (null == attributes) {
            return null;
        }
        return attributes.get(key);
    }

    @Override
    public void setAttribute(String key, Object value) {
        if (null == attributes) {
            attributes = new HashMap<>();
        }
        attributes.put(key, value);
    }

    @Override
    public long getTimeout() {
        return timeout;
    }

    @Override
    public Set<String> getMenus() {
        return menus;
    }

    @Override
    public boolean hasMenu(String clazz, String method) {
        if (null == this.menus) {
            return false;
        }
        return this.menus.contains(clazz + "." + method);
    }

    public DefaultSession(String id, Set<String> menus) {
        this.id = id;
        this.menus = menus;
        this.timeout = System.currentTimeMillis() + 1000 * 60 * 60; // 一小时超时
    }
}
