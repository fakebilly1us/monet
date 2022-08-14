package com.fakebilly.monet.business.server.page;

import java.io.Serializable;

/**
 * SimplePage
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public abstract class SimplePage implements Serializable {

    private static final long serialVersionUID = 667258467721436011L;

    /**
     * 默认展示条数
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


    public int getPageIndex() {
        if (pageIndex < 1) {
            return 1;
        }
        return pageIndex;
    }

    public SimplePage setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
        return this;
    }

    public int getPageSize() {
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        return pageSize;
    }

    public SimplePage setPageSize(int pageSize) {
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        this.pageSize = pageSize;
        return this;
    }
}
