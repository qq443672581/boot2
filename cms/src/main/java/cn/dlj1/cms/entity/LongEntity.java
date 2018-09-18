package cn.dlj1.cms.entity;

import cn.dlj1.cms.entity.annotation.Cloumn;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

/**
 * long 主键
 */
public class LongEntity implements Entity {

    @TableId(type = IdType.AUTO)
    @Cloumn("主键")
    @NotBlank(groups = {delete.class, edit.class, view.class})
    @Null(groups = add.class)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
