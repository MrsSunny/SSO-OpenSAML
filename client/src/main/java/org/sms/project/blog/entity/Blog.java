package org.sms.project.blog.entity;

import java.util.Date;

public class Blog {
    private Long id;

    private String title;

    private String htmlFileId;

    private String mdFileId;

    private Long readNum;

    private Integer usableStatus;

    private Long createUserId;

    private Date createDate;

    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHtmlFileId() {
        return htmlFileId;
    }

    public void setHtmlFileId(String htmlFileId) {
        this.htmlFileId = htmlFileId;
    }

    public String getMdFileId() {
        return mdFileId;
    }

    public void setMdFileId(String mdFileId) {
        this.mdFileId = mdFileId;
    }

    public Long getReadNum() {
        return readNum;
    }

    public void setReadNum(Long readNum) {
        this.readNum = readNum;
    }

    public Integer getUsableStatus() {
        return usableStatus;
    }

    public void setUsableStatus(Integer usableStatus) {
        this.usableStatus = usableStatus;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}