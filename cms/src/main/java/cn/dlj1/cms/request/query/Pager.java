package cn.dlj1.cms.request.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * 分页
 *
 * 继承自 {@link Page}
 */
public class Pager<T> extends Page<T> {

    public static final int DEFAULT_PAGE_SIZE = 20;

    private long pageTotal;

    public long getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(long pageTotal) {
        this.pageTotal = pageTotal;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Override
    public List<T> getRecords() {
        return super.getRecords();
    }

    public Pager() {
    }

    /**
     * 初始化页数
     */
    public void initPages() {
        if (super.getTotal() != 0) {
            return;
        }
        if (super.getSize() == 0) {
            super.setSize(DEFAULT_PAGE_SIZE);
        }
        setPageTotal(
                (super.getTotal() % super.getSize() == 0 ?
                        (super.getTotal() / super.getSize()) :
                        (super.getTotal() / super.getSize() + 1))
        );
    }

}
