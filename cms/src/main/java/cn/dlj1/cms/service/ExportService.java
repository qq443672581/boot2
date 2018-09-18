package cn.dlj1.cms.service;

import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.request.query.ExportQuery;
import cn.dlj1.cms.response.Result;
import cn.dlj1.cms.service.supports.ExportUtils;
import cn.dlj1.cms.utils.DateUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * excel导出
 */
public interface ExportService<T extends Entity> extends TableService<T> {

    static Log log = LogFactory.getLog(ExportService.class);

    /**
     * 获取类字段
     *
     * @return
     */
    default Result getExportInfos() {
        Class<T> clazz = getModuleClazz();
        Map map = ExportUtils.getExportFields(clazz);
        return new Result.Success(map);
    }

    /**
     * 导出
     *
     * @return
     */
    default void export(HttpServletResponse response, ExportQuery query) {
        Result result = table(query);
        List<Map<String, Object>> data = null;
        if (result.isSuccess()) {
            data = (List<Map<String, Object>>) result.getData();
        }

        Map<String, String> map = null;
        try {
            map = JSON.parseObject(query.getExportFields(), LinkedHashMap.class);
        } catch (Exception e) {
            response.setContentType("text/html;charset=utf-8");
            try {
                response.getWriter().append("导出字段格式不正确!").close();
                return;
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        if (null == query.getExportFileName()) {
            query.setExportFileName("data_" + (DateUtils.getNow().getTime() / 1000));
        }
        if (!query.getExportFileName().endsWith(".xls")) {
            query.setExportFileName(query.getExportFileName() + ".xls");
        }

        try {
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(query.getExportFileName(), "UTF-8"));
            ExportUtils.export(response, "default", "module", "", map, data);
        } catch (IOException e) {
            response.setContentType("text/html;charset=utf-8");
            try {
                response.getWriter().append("导出失败!").close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }

}
