package cn.dlj1.cms.web.entity.sys.auth;

import cn.dlj1.cms.dao.annotation.MoreToMore;
import cn.dlj1.cms.dao.annotation.OneToMore;
import cn.dlj1.cms.dao.annotation.OneToOne;
import cn.dlj1.cms.entity.LongEntity;
import cn.dlj1.cms.entity.annotation.Cloumn;
import cn.dlj1.cms.entity.annotation.SelectModule;
import cn.dlj1.cms.entity.annotation.Table;
import cn.dlj1.cms.web.dao.sys.auth.AdminExtendDao;
import cn.dlj1.cms.web.dao.sys.auth.AdminUuidDao;
import cn.dlj1.cms.web.dao.sys.auth.AdminRoleDao;
import cn.dlj1.cms.web.dao.sys.auth.RoleDao;
import com.baomidou.mybatisplus.annotation.TableName;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.util.List;

/**
 * 用户表
 */
@Table("管理员")
@TableName("sys_admin")
@SelectModule(text = "name", value = "id")
public class Admin extends LongEntity {

    @Length(groups = add.class, min = 5, max = 16, message = "账号长度5-16位")
    @Null(groups = edit.class, message = "账号不可修改")
    @NotBlank(groups = add.class)
    @Cloumn("账号")
    private String passport;

    @Length(groups = add.class, min = 5, max = 16, message = "密码长度5-16位")
    @NotBlank(groups = add.class)
    @Cloumn("密码")
    private String password;

    @Cloumn("姓名")
    private String name;

    @Cloumn("可用")
    private Integer state;

    @Cloumn("角色")
    @MoreToMore(mapper = RoleDao.class, middleMapper = AdminRoleDao.class, leftField = "adminId", rightField = "roleId")
    private transient List<Role> roles;

    @Cloumn("扩展")
    @OneToOne(mapper = AdminExtendDao.class, one = "adminId")
    private transient AdminExtend extend;

    @Cloumn("uuid表")
    @OneToMore(mapper = AdminUuidDao.class, more = "adminId")
    private transient List<AdminUuid> uuid;

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public AdminExtend getExtend() {
        return extend;
    }

    public void setExtend(AdminExtend extend) {
        this.extend = extend;
    }

    public List<AdminUuid> getUuid() {
        return uuid;
    }

    public void setUuid(List<AdminUuid> uuid) {
        this.uuid = uuid;
    }
}

