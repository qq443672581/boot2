package cn.dlj1.cms.request.query;

import javax.validation.constraints.NotBlank;

/**
 * excel请求导出
 *
 * @param <T>
 */
public class ExportQuery<T> extends Query<T> {

    @NotBlank(message = "导出文件名不能为空")
    private String exportFileName;

    @NotBlank(message = "导出字段不能为空")
    private String exportFields;

    public String getExportFileName() {
        return exportFileName;
    }

    public void setExportFileName(String exportFileName) {
        this.exportFileName = exportFileName;
    }

    public String getExportFields() {
        return exportFields;
    }

    public void setExportFields(String exportFields) {
        this.exportFields = exportFields;
    }
}
