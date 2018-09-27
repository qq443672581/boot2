package cn.dlj1.cms.web.entity.sys.auth;

import cn.dlj1.cms.entity.LongEntity;
import cn.dlj1.cms.entity.annotation.Cloumn;
import cn.dlj1.cms.entity.annotation.Table;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 */
@Table("管理角色中间表")
@TableName("sys_admin_role")
public class AdminRole extends LongEntity {

    @Cloumn("管理员ID")
    @TableField("admin_id")
    private Long adminId;

    @Cloumn("角色ID")
    @TableField("role_id")
    private Long roleId;

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}

