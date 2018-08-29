package cn.dlj1.cms.request.query;

/**
 * 查询排序
 */
public class Sort {

    private String field;
    private String type;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Sort() {
    }

    public Sort(String field, String type) {
        this.field = field;
        this.type = type;
    }

    public Sort(String field, Type type) {
        this.field = field;
        if (null == type) {
            return;
        }
        if (type == Type.ASC) {
            this.type = "asc";
        } else if (type == Type.DESC) {
            this.type = "desc";
        }
    }

    public static enum Type {
        ASC(),
        DESC();
    }
}
