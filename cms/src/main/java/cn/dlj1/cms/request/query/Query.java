package cn.dlj1.cms.request.query;

import cn.dlj1.cms.db.condition.Cnd;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 数据查询
 *
 */
@Validated
public class Query {

    @NotNull
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
}
