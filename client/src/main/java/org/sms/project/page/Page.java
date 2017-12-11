package org.sms.project.page;

/**
 * @author Sunny
 */
public class Page {

    private static final int defaultSize = 10;

    /**
     * recordCount 表示: 记录总数
     */
    private int recordCount;

    /**
     * pageSize 表示: 每页有多少条记录
     */
    private int pageSize;

    private int startIndex;

    private int pageNumber;

    public Page() {
    }

    /**
     * 构造函数:
     * 
     * @param recordCount
     *            记录总数
     * @param pageSize
     *            每页有多少条记录
     */
    public Page(int pageNumber, int pageSize) {
        if (pageSize < 1) {
            pageSize = defaultSize;
        }
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.startIndex = (pageNumber - 1) * pageSize;
    }

    /**
     * 功能: 获得总页数
     * 
     * @return
     */
    public int getPageCount() {
        int pc = recordCount / pageSize + (recordCount % pageSize == 0 ? 0 : 1);
        if (pc == 0) {
            pc = 1;
        }
        return pc;
    }

    /**
     * 功能: 获得开始条数
     *
     * @return
     */
    public int getStartIndex() {
        return this.startIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}