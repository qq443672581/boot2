package cn.dlj1.cms.request.query;

/**
 * excel请求导出
 *
 * @param <T>
 */
public class ExportQuery<T> extends Query<T> {

    private String exportFileName;

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
