package cn.dlj1.cms.controller;

import cn.dlj1.cms.controller.supports.AttachmentView;
import cn.dlj1.cms.db.key.Key;
import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.request.query.Query;
import cn.dlj1.cms.response.Result;
import cn.dlj1.cms.service.ExportService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * 数据导出接口
 *
 */
public interface ExportController extends Controller {

    /**
     * 导出信息
     * 返回导出数据时需要的
     *      字段信息 [{k(字段值):v(字段名),...}]
     *
     * @return
     */
    @RequestMapping("/exportInfos")
    @ResponseBody
    default Result exportInfos() {
        ExportService service = (ExportService) getService();
        return service.getExportInfos();
    }

    /**
     * 导出操作
     *
     * @param query
     * @return
     */

    @RequestMapping(value = "/export", produces = AttachmentView.APPLICATION_VIEW_NAME)
    @ResponseBody
    default String export(HttpServletRequest request, Query query){
        return AttachmentView.view(request,"测试.txt","文件".getBytes());
    }

}
