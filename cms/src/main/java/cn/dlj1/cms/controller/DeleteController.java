package cn.dlj1.cms.controller;

import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.response.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 删除接口
 */
public interface DeleteController<T extends Entity,K extends Serializable> extends Controller<T> {


    @RequestMapping("/delete")
    @ResponseBody
    default Result add(@Validated @NotNull K id) {
        return getActionService().delete(id);
    }

}