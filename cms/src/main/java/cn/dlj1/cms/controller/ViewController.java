package cn.dlj1.cms.controller;

import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.response.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 查询一条数据接口（展示接口）
 *
 *
 */
public interface ViewController<T extends Entity, K extends Serializable> extends Controller<T> {

    @GetMapping("/view")
    @ResponseBody
    default Result view(@Validated(Entity.view.class) @NotNull K id) {
        return getActionService().view(id);
    }

}
