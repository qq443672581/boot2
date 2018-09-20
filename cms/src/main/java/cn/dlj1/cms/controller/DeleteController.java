package cn.dlj1.cms.controller;

import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.response.Result;
import cn.dlj1.cms.web.auth.annotation.Menu;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 删除接口
 *
 *
 */
public interface DeleteController<T extends Entity,K extends Serializable> extends Controller<T> {


    @DeleteMapping("/delete")
    @ResponseBody
    @Menu(value = "删除", key = "delete")
    default Result delete(@Validated @NotBlank K... id) {
        return getActionService().delete(id);
    }

}
