package cn.dlj1.cms.web.entity.sys.auth;

import cn.dlj1.cms.entity.LongEntity;
import cn.dlj1.cms.entity.annotation.Cloumn;
import cn.dlj1.cms.entity.annotation.Table;
import com.baomidou.mybatisplus.annotation.TableName;

@Table("角色菜单")
@TableName("sys_role_menu")
public class RoleMenu extends LongEntity {

    @Cloumn("角色ID")
    private int roleId;

    @Cloumn("菜单ID")
    private int menuId;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }
}
