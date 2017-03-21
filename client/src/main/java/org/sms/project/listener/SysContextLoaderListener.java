package org.sms.project.listener;

import javax.servlet.ServletContextEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sms.opensaml.service.impl.SamlServiceImpl;
import org.sms.project.datasource.SysDataSource;
import org.sms.project.init.SysConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoaderListener;

/**
 * @author Sunny
 */
public class SysContextLoaderListener extends ContextLoaderListener {

  private Logger logger = LoggerFactory.getLogger(SamlServiceImpl.class.getName());
  
  @Autowired
  private SysDataSource sysDataSource;

  public void contextInitialized(ServletContextEvent sce) {
    logger.debug("初始换数据开始");
    SysConfig.INSTANCE.loadSysConfig();
  }
}
