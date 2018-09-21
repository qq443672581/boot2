CREATE TABLE `sys_admin` (
  `id` INT(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `passport` varchar(16) NOT NULL COMMENT '账号',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '姓名',
  `state` INT(1) NOT NULL DEFAULT 1 COMMENT '状态,1为可用,0不可用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `passport` (`passport`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员表';

CREATE TABLE `sys_role` (
  `id` INT(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(16) NOT NULL COMMENT '角色名',
  `state` INT(1) NOT NULL DEFAULT 1 COMMENT '状态,1为可用,0不可用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

CREATE TABLE `sys_admin_role` (
  `id` INT(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `admin_id` INT(10) NOT NULL COMMENT '管理员ID',
  `role_id` INT(10) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员角色表';

CREATE TABLE `sys_menu` (
  `id` INT(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',

  `title` varchar(16) NOT NULL COMMENT '菜单名',
  `path` varchar(128) NOT NULL COMMENT '路径',
  `act_key` varchar(16) NOT NULL DEFAULT '' COMMENT '操作key',
  `parent` INT(10) NOT NULL COMMENT '状态,1为可用,0不可用',
  `clazz` varchar(128) NOT NULL DEFAULT '' COMMENT '报名',

  PRIMARY KEY (`id`),
  UNIQUE KEY `path` (`path`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

CREATE TABLE `sys_role_menu` (
  `id` INT(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` INT(10) NOT NULL COMMENT '角色ID',
  `menu_id` INT(10) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

CREATE TABLE `sys_admin_extend` (
  `id` INT(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `admin_id` INT(10) NOT NULL COMMENT 'adminId',
  `birthday` datetime COMMENT '生日',
  PRIMARY KEY (`id`),
  UNIQUE KEY `admin_id` (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员扩展表';

CREATE TABLE `sys_admin_uuid` (
  `id` INT(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `admin_id` INT(10) NOT NULL COMMENT 'adminId',
  `uuid` VARCHAR(32) COMMENT 'uuid',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员uuid表';