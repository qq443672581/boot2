package cn.dlj1.cms.controller;

import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.response.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 添加接口
 *
 *
 */
public interface AddController<T extends Entity> extends Controller<T> {

    @PostMapping("/add")
    @ResponseBody
    default Result add(@Validated(Entity.add.class) T entity) {
        return getActionService().add(entity);
    }

}
