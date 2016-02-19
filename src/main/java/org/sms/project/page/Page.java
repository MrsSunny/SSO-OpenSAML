package org.sms.project.page;

/**
 * @author zhenxing.Liu
 */
public class Page {

  private static final int defaultSize = 15;

  public Page() {
  }

  /**
   * 构造函数:
   * 
   * @param recordCount
   *          记录总数
   * @param pageSize
   *          每页有多少条记录
   */
  public Page(int recordCount, int pageSize) {
    this();
    if (recordCount < 0) {
      recordCount = 0;
    }
    if (pageSize < 1) {
      pageSize = defaultSize;
    }
    this.recordCount = recordCount;
    this.pageSize = pageSize;
  }

  /**
   * recordCount 表示: 记录总数
   */
  private int recordCount;

  /**
   * pageSize 表示: 每页有多少条记录
   */
  private int pageSize;

  /**
   * currentPage 表示: 当前第几页
   */
  private int currentPage = 1;

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
    return (currentPage - 1) * pageSize + 1;
  }

  public int getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(int currentPage) {
    if (currentPage <= 1 || getPageCount() == 0) {
      currentPage = 1;
    } else if (currentPage > getPageCount()) {
      currentPage = getPageCount();
    }
    this.currentPage = currentPage;
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

  public void setRecordCount(int recordCount) {
    this.recordCount = recordCount;
  }
}