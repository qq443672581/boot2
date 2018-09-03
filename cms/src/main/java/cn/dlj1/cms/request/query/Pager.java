package cn.dlj1.cms.request.query;

/**
 * 分页
 */
public class Pager {

    public static final Pager EMPTY = new Pager();

    private boolean all;

    private int now;

    private int pageSize;

    private int count;

    public boolean isAll() {
        return all;
    }

    public void setAll(boolean all) {
        this.all = all;
    }

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isEmpty() {
        if (this == EMPTY || this.getCount() == 0) {
            return true;
        }
        return false;
    }

    public Pager() {
    }

    public Pager init() {
        this.now = 1;
        this.count = 0;
        this.pageSize = 20;
        return this;
    }

}
