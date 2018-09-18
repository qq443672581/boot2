package cn.dlj1.cms.entity;

import cn.dlj1.cms.entity.annotation.Cloumn;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * 字符串主键
 */
public class StringEntity implements Entity {

    @TableId(type = IdType.ID_WORKER_STR)
    @Cloumn("主键")
    @NotBlank(groups = {delete.class, edit.class, view.class})
    @Null(groups = add.class)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
