package cn.dlj1.cms.controller;

import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.response.Result;
import cn.dlj1.cms.web.auth.annotation.Menu;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 添加接口
 */
public interface AddController<T extends Entity> extends Controller<T> {

    @PostMapping("/add")
    @ResponseBody
    @Menu(value = "添加", key = "add")
    default Result add(@Validated(Entity.add.class) T entity) {
        return getActionService().add(entity);
    }

}
