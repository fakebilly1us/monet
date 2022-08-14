package com.fakebilly.monet.basic.server.page;

import java.io.Serializable;

/**
 * Page
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public abstract class Page implements Serializable {

    private static final long serialVersionUID = -6586088993113827019L;

    /**
     * 正向排序
     */
    public static final String ASC = "ASC";
    /**
     * 倒序
     */
    public static final String DESC = "DESC";
    /**
     * 默认分页展示条数
     */
    private static final int DEFAULT_PAGE_SIZE = 10;
    /**
     * 每页展示条数
     */
    private int pageSize = DEFAULT_PAGE_SIZE;
    /**
     * 当前页码
     */
    private int pageIndex = 1;
    /**
     * 排序
     */
    private String orderBy;
    /**
     * 倒序排序
     */
    private String orderDirection = DESC;
    /**
     * 分组
     */
    private String groupBy;
    /**
     * 需要总条数
     */
    private boolean needTotalCount = true;

    public int getPageIndex() {
        if (pageIndex < 1) {
            return 1;
        }
        return pageIndex;
    }

    public Page setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
        return this;
    }

    public int getPageSize() {
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        return pageSize;
    }

    public Page setPageSize(int pageSize) {
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        this.pageSize = pageSize;
        return this;
    }

    public int getOffset() {
        return (getPageIndex() - 1) * getPageSize();
    }

    public String getOrderBy() {
        return orderBy;
    }

    public Page setOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public Page setOrderDirection(String orderDirection) {
        if (ASC.equalsIgnoreCase(orderDirection) || DESC.equalsIgnoreCase(orderDirection)) {
            this.orderDirection = orderDirection;
        }
        return this;
    }

    public String getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }

    public boolean isNeedTotalCount() {
        return needTotalCount;
    }

    public void setNeedTotalCount(boolean needTotalCount) {
        this.needTotalCount = needTotalCount;
    }
}
