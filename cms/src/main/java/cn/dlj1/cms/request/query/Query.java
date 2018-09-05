package cn.dlj1.cms.request.query;

import cn.dlj1.cms.db.condition.Cnd;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.servlet.http.HttpServletRequest;

/**
 * 数据查询
 */
public class Query<T> {

    @JsonIgnore
    private HttpServletRequest request;

    private String[] fields;

    // 查询条件，可以使用 cn.dlj1.cms.db.condition.CndUtils.toObjMap 进行转化map
    private Cnd[] cnds;

    private Sort sort;

    private Pager<T> pager;

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

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public void initPager() {
        if (null == getPager()) {
            setPager(new Pager());
        }
        if (getPager().getCurrent() == 0) {
            getPager().setCurrent(1);
        }
        if (getPager().getSize() == 0) {
            getPager().setSize(Pager.DEFAULT_PAGE_SIZE);
        }
        if (getPager().getPageTotal() == 0) {
            getPager().setPageTotal(1);
        }
    }

}
