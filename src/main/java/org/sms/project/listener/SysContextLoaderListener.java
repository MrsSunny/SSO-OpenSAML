package org.sms.project.listener;

import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sms.project.init.SysConfig;
import org.sms.saml.service.impl.SamlServiceImpl;
import org.springframework.web.context.ContextLoaderListener;

/**
 * @author zhenxing.Liu
 */
public class SysContextLoaderListener extends ContextLoaderListener {

  private Logger logger = LoggerFactory.getLogger(SamlServiceImpl.class.getName());

  public void contextInitialized(ServletContextEvent sce) {
    logger.debug("初始换数据开始");
    SysConfig.INSTANCE.loadSysConfig();
  }
}
