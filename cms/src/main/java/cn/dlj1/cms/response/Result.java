package cn.dlj1.cms.response;

import cn.dlj1.cms.request.query.Pager;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

public class Result {

    public static final Result SUCCESS = new Result();
    public static final Result FAIL = new Fail("系统异常!");
    public static final Result FAIL_NULL = new Fail("数据为空!");

    private int status = 0;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String msg;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object others;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Pager pager;

    public Result() {
    }

    public Result(String msg) {
        status = -1;
        this.msg = msg;
    }

    public static class Fail extends Result {
        public Fail(String msg) {
            super(msg);
        }
    }
    public static class Success extends Result {
        public Success(Object object) {
            super();
            super.data = object;
        }
    }

    @JsonIgnore
    public boolean isSuccess() {
        return status == 0;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getOthers() {
        return others;
    }

    public void setOthers(Object others) {
        this.others = others;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
        this.pager.setRecords(null);
    }
}
