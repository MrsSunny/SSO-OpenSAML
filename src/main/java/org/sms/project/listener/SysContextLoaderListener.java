package org.sms.project.listener;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

/** 
 * @author zhenxing.Liu
 */
public class SysContextLoaderListener extends ContextLoaderListener {

    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("初始换数据开始" + "===============================");
    }
}
