package cn.dlj1.cms.web.entity.sys.auth;

import cn.dlj1.cms.entity.LongEntity;
import cn.dlj1.cms.entity.annotation.Cloumn;
import cn.dlj1.cms.entity.annotation.Table;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 */
@Table("管理uuid表")
@TableName("sys_admin_uuid")
public class AdminUuid extends LongEntity {

    @Cloumn("管理员ID")
    private Long adminId;

    @Cloumn("UUID")
    private String uuid;

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}

