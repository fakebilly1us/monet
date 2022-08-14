package com.fakebilly.monet.business.server.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * PageDTO
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class PageDTO<T> implements Serializable {

    private static final long serialVersionUID = 7333886757005518881L;

    /**
     * 总条数
     */
    private int totalCount = 0;
    /**
     * 每页展示条数
     */
    private int pageSize = 1;
    /**
     * 当前页
     */
    private int pageIndex = 1;
    /**
     * 数据
     */
    private Collection<T> records;

    /**
     * 总条数
     */
    private int total = 0;
    /**
     * 当前页
     */
    private int current = 0;
    /**
     * 总页数
     */
    private int pages = 0;


    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        if (pageSize < 1) {
            return 1;
        }
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize < 1) {
            this.pageSize = 1;
        } else {
            this.pageSize = pageSize;
        }
    }

    public int getPageIndex() {
        if (pageIndex < 1) {
            return 1;
        }
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        if (pageIndex < 1) {
            this.pageIndex = 1;
        } else {
            this.pageIndex = pageIndex;
        }
    }

    public List<T> getRecords() {
        return null == records ? Collections.emptyList() : new ArrayList<>(records);
    }

    public void setRecords(Collection<T> records) {
        this.records = records;
    }

    public int getTotalPages() {
        return this.totalCount % this.pageSize == 0 ? this.totalCount
                / this.pageSize : (this.totalCount / this.pageSize) + 1;
    }

    public boolean isEmpty() {
        return records == null || records.size() == 0;
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public int getTotal() {
        return totalCount;
    }

    public int getCurrent() {
        if (pageIndex < 1) {
            return 1;
        }
        return pageIndex;
    }

    public int getPages() {
        return getTotalPages();
    }

    @Override
    public String toString() {
        return "PageDTO{" +
                "totalCount=" + totalCount +
                ", pageSize=" + pageSize +
                ", pageIndex=" + pageIndex +
                ", records=" + records +
                ", total=" + total +
                ", current=" + current +
                ", pages=" + pages +
                '}';
    }
}
