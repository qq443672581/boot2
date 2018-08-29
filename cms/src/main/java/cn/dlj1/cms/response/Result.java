package cn.dlj1.cms.response;

import cn.dlj1.cms.request.query.Pager;

public class Result {

    public static final Result SUCCESS = null;

    private int ret;
    private String msg;
    private Object data;
    private Object others;

    private Pager pager;
}
