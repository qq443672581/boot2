package cn.dlj1.cms.web.entity.sys.auth;

import cn.dlj1.cms.entity.LongEntity;
import cn.dlj1.cms.entity.annotation.Cloumn;
import cn.dlj1.cms.entity.annotation.Table;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.sql.Date;

/**
 */
@Table("管理扩展表")
@TableName("sys_admin_extend")
public class AdminExtend extends LongEntity {

    @Cloumn("管理员ID")
    @TableField("admin_id")
    private Long adminId;

    @Cloumn("生日")
    private Date birthday;

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}

