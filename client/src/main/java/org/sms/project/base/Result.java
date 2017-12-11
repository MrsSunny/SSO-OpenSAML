package org.sms.project.base;

import java.util.List;

import org.sms.project.page.Page;

public class Result<T> {

    private List<T> list;

    private Page page;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}