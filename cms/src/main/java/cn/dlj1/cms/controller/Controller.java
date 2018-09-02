package cn.dlj1.cms.controller;

import cn.dlj1.cms.db.key.Key;
import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.service.Component.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 控制器上层接口
 *
 * @param <K>
 * @param <T>
 */
public interface Controller<K extends Key, T extends Entity> {

    /**
     * 获取模块的路径
     *
     * @return
     */
    default String getModulePath() {
        RequestMapping rm = this.getClass().getAnnotation(RequestMapping.class);
        String path = "";
        if (null != rm) {
            String[] values = rm.value();
            if (null != values && values.length > 0) {
                path = values[0] + "/";
            }
        }
        return path;
    }

     Component getC();

}
