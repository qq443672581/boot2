package cn.dlj1.cms.controller;

import cn.dlj1.cms.controller.supports.AttachmentView;
import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.request.query.Query;
import cn.dlj1.cms.response.Result;
import cn.dlj1.cms.service.ExportService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 数据导出接口
 */
public interface ExportController<T extends Entity> extends Controller<T> {

    /**
     * 导出信息
     * 返回导出数据时需要的
     * 字段信息 [{k(字段值):v(字段名),...}]
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
    default String export(HttpServletRequest request, Query query) {
        ExportService service = (ExportService) getService();
        return AttachmentView.view(request, "测试.txt", service.export(query));
    }

}
