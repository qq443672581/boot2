package cn.dlj1.cms.controller;

import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.response.Result;
import cn.dlj1.cms.web.auth.annotation.Menu;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 修改接口
 */
public interface EditController<T extends Entity> extends Controller<T> {


    @PutMapping("/edit")
    @ResponseBody
    @Menu(value = "修改", key = "edit")
    default Result edit(@Validated(Entity.edit.class) T entity) {
        return getActionService().edit(entity);
    }

}
