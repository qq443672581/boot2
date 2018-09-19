package cn.dlj1.cms.web.auth.session;

import java.util.Map;
import java.util.Set;

public interface SessionFactory {

    boolean has(String authToken);

    String create();

    String create(Set<String> menus);

    Session get(String authToken);

    void remove(String authToken);

    long size();

    Map<String, Session> sessions();
}
