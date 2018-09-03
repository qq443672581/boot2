package cn.dlj1.cms.entity;

import cn.dlj1.cms.entity.annotation.Cloumn;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 *
 *
 */
public class StringEntity implements Entity {

    @TableId(type = IdType.ID_WORKER_STR)
    @Cloumn("主键")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
