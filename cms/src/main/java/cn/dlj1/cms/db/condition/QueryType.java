package cn.dlj1.cms.db.condition;

public enum QueryType {

    FALSE("", "", "不可查询"),
    EQUALS("=", "equals", "等于"),
    NOT_EQUALS("!=", "not_equals", "不等于"),
    GT(">", "gt", "大于"),
    GET(">=", "get", "大于等于"),
    LT("<", "lt", "小于"),
    LET("<=", "let", "小于等于"),

    LIKE("like", "like", "模糊查询"),
    START("like", "start", "开始于"),
    END("like", "end", "结束于"),

    AND("and", "and", "并且"),
    OR("or", "or", "或者"),
    BETWEEN("between", "between", "之间"),

    IS("is", "is", "是"), //只用于  is null
    NOT_IN("not in", "not_in", "不存在"),
    IN("in", "in", "存在于");

    private String code;
    private String en;
    private String cn;

    private QueryType(String code, String en, String cn) {
        this.code = code;
        this.en = en;
        this.cn = cn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public static final QueryType[] compositeTypes = new QueryType[]{BETWEEN, AND, OR};

    public static boolean isCompoundCnd(QueryType type) {
        for (int i = 0; i < compositeTypes.length; i++) {
            if (compositeTypes[i] == type) {
                return true;
            }
        }
        return false;
    }

    public static String[] getCompositeTypeString() {
        String[] ss = new String[compositeTypes.length];
        for (int i = 0; i < compositeTypes.length; i++) {
            ss[i] = compositeTypes[i].getCode();
        }
        return ss;
    }

    /**
     * @param code
     * @return
     */
    public static QueryType getType(String code) {
        switch (code) {

            case "equals":
                return QueryType.EQUALS;
            case "not_equals":
                return QueryType.NOT_EQUALS;
            case "gt":
                return QueryType.GT;
            case "get":
                return QueryType.GET;
            case "lt":
                return QueryType.LT;
            case "let":
                return QueryType.LET;
            case "like":
                return QueryType.LIKE;
            case "start":
                return QueryType.START;
            case "end":
                return QueryType.END;
            case "and":
                return QueryType.AND;
            case "or":
                return QueryType.OR;
            case "between":
                return QueryType.BETWEEN;
            case "in":
                return QueryType.IN;
            case "not_in":
                return QueryType.NOT_IN;
            case "is":
                return QueryType.IS;
            default:
                return null;
        }
    }

}
