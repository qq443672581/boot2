package cn.dlj1.cms.request.query;

import cn.dlj1.cms.db.condition.Cnd;

/**
 * 数据查询
 */
public class Query {

    private String[] fields;

    private Cnd[] cnds;

    private Sort sort;

    private Pager pager;

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    public Cnd[] getCnds() {
        return cnds;
    }

    public void setCnds(Cnd[] cnds) {
        this.cnds = cnds;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public void initPager() {
        if (null == getPager()) {
            setPager(new Pager().init());
        }
        if (getPager().getNow() == 0) {
            getPager().setNow(1);
        }
        if (getPager().getPageSize() == 0) {
            getPager().setPageSize(20);
        }
    }
}
