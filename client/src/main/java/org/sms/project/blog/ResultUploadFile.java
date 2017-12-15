package org.sms.project.blog;

import org.sms.project.common.ResultAdd;

public class ResultUploadFile extends ResultAdd {
    
    private String mdPath;
    private String htmlPath;
    private String name;
    private String url;
    private String id;
    private int type;
    public String getMdPath() {
        return mdPath;
    }
    public void setMdPath(String mdPath) {
        this.mdPath = mdPath;
    }
    public String getHtmlPath() {
        return htmlPath;
    }
    public void setHtmlPath(String htmlPath) {
        this.htmlPath = htmlPath;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
}
