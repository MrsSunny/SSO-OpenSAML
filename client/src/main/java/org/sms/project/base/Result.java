package org.sms.project.base;

import java.util.List;

import org.sms.project.page.Page;
import org.sms.project.user.entity.User;

public class Result {

  private List<User> list;

  private Page page;
  
  private String[] title;
  
  public List<User> getList() {
    return list;
  }

  public void setList(List<User> list) {
    this.list = list;
  }

  public Page getPage() {
    return page;
  }

  public void setPage(Page page) {
    this.page = page;
  }

  public String[] getTitle() {
    return title;
  }

  public void setTitle(String[] title) {
    this.title = title;
  }
}
