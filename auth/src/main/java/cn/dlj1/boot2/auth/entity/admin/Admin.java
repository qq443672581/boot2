package cn.dlj1.boot2.auth.entity.admin;

import cn.dlj1.boot2.auth.entity.role.Role;

public interface Admin {

    String id();

    String name();

    Role[] roles();

}
