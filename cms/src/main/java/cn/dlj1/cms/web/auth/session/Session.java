package cn.dlj1.cms.web.auth.session;

import java.util.Set;

public interface Session {

    String getId();

    long getTimeout();

    Object getAttribute(String key);

    void setAttribute(String key, Object value);

    Set<String> getMenus();

    boolean hasMenu(String clazz, String method);

}
