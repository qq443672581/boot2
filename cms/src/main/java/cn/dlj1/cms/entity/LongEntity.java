package cn.dlj1.cms.entity;

import cn.dlj1.cms.entity.annotation.Cloumn;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 *
 *
 */
public class LongEntity implements Entity {

    @TableId(type = IdType.AUTO)
    @Cloumn("主键")
    private long id;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
