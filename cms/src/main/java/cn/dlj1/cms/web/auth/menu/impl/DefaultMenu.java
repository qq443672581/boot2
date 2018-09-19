package cn.dlj1.cms.web.auth.menu.impl;

import cn.dlj1.cms.web.auth.menu.Menu;

public class DefaultMenu implements Menu {

    private String name;

    private String code;

    private DefaultMenu[] items;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public DefaultMenu[] getItems() {
        return items;
    }

}
