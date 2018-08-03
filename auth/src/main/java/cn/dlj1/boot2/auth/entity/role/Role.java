package cn.dlj1.boot2.auth.entity.role;

import cn.dlj1.boot2.auth.entity.menu.Menu;

public interface Role {

    String id();

    String name();

    Menu[] menus();

}