package org.sms.project.blog.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.sms.project.blog.service.BlogService;

import base.BaseTest;

public class BlogServiceImplTest extends BaseTest {

    private BlogService blogService = (BlogService) aCtx.getBean("blogService");

    // @Test
    // public void insert() {
    // Blog blog = new Blog();
    // blog.setContent("");
    // blog.setCreateDate(new Date());
    // blog.setCreateUserId(1L);
    // blog.setMdFileId("");
    // blog.setReadNum(0L);
    // blog.setTitle("学习Nginx编程");
    // blog.setUsableStatus(0);
    // blogService.insert(blog);
    // }

    @Test
    public void parse() {
        System.out.println("===");
        String html = "<html><head><title> 开源中国社区 </title></head>" + "<body><p> 这里是 jsoup 项目的相关文章 </p></body></html>";
        Document doc = Jsoup.parse(html);
        System.out.println(doc.toString());
    }

    // @Test
    // public void update() {
    // Blog blog = new Blog();
    // blog.setId(1L);
    // blog.setContent("jlasfasdfljasfasdf");
    // blog.setCreateDate(new Date());
    // blog.setCreateUserId(1L);
    // blog.setMdFileId("/user/a.md");
    // blog.setReadNum(0L);
    // blog.setTitle("学习Nginx编程快上手");
    // blog.setUsableStatus(0);
    // blogService.update(blog);
    // }
    //
    // @Test
    // public void delete() {
    // blogService.delete(1L);
    // }
    //
    // @Test
    // public void findById() {
    // Blog blog = blogService.findById(2L);
    // System.out.println(blog.getContent());
    // }
}
