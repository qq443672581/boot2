package cn.dlj1.cms.controller;

import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.response.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;

/**
 * 下拉选择接口
 */
public interface SelectController<T extends Entity> extends Controller<T> {

    @GetMapping("/select")
    @ResponseBody
    default Result select(@Validated @NotNull String text) {
        return getActionService().select(text);
    }

}
