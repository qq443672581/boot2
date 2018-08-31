package cn.dlj1.cms.controller;

import cn.dlj1.cms.controller.supports.AttachmentView;
import cn.dlj1.cms.db.key.Key;
import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.request.query.Query;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * 数据导出接口
 *
 * @param <K>
 * @param <T>
 */
public interface ExportController<K extends Key, T extends Entity> extends Controller<K, T> {

    /**
     * 导出页面
     *
     * @param query
     * @return
     */
    @RequestMapping("/export.view")
    default ModelAndView exportPage(ModelAndView mav, Query query) {

        return mav;
    }

    /**
     * 导出操作
     *
     * @param query
     * @return
     */

    @RequestMapping(value = "/export", produces = AttachmentView.APPLICATION_VIEW_NAME)
    default String export(HttpServletRequest request, Query query){
        return AttachmentView.view(request,"测试.txt","文件".getBytes());
    }

}
