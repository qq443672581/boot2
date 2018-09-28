package cn.dlj1.cms.web.entity.sys.auth;

import cn.dlj1.cms.dao.annotation.OneToMore;
import cn.dlj1.cms.entity.LongEntity;
import cn.dlj1.cms.entity.annotation.Cloumn;
import cn.dlj1.cms.entity.annotation.Table;
import cn.dlj1.cms.web.dao.sys.auth.RoleMenuDao;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.List;

@Table("角色")
@TableName("sys_role")
public class Role extends LongEntity {

    @Cloumn("角色名")
    private String name;

    @Cloumn("可用")
    private Integer state;

    @Cloumn("菜单")
    @OneToMore(mapper = RoleMenuDao.class, more = "roleId")
    private transient List<RoleMenu> menus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
