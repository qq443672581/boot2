package cn.dlj1.cms.controller;

import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.response.Result;
import cn.dlj1.cms.web.auth.annotation.Menu;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

/**
 * 修改接口
 */
public interface EditController<T extends Entity> extends Controller<T> {


    @PostMapping("/edit")
    @ResponseBody
    @Menu(value = "修改", key = "edit")
    default Result edit(HttpServletRequest request, @RequestBody @Validated(Entity.edit.class) T entity) {
        return getActionService().edit(request, entity);
    }

}
