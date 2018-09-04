package cn.dlj1.cms.request.query;

/**
 * 分页
 */
public class Pager {

//    public static final Pager EMPTY = new Pager();

    private int now;

    private int pageSize;

    private int pages;

    private long count;

    public int getNow() {
        return now;
    }

    public void setNow(int now) {
        this.now = now;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public Pager() {
    }

    public Pager init() {
        this.now = 1;
        this.count = 0;
        this.pageSize = 20;
        this.pages = 0;
        return this;
    }

}
