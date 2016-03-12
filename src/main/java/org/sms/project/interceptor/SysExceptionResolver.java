package org.sms.project.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 异常拦截器
 * @author Sunny
 * @since 1.8.0
 */
@Component
public class SysExceptionResolver implements HandlerExceptionResolver {
  
  private Logger logger = LoggerFactory.getLogger(SysExceptionResolver.class.getName());

  @Override
  public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    String message = ex.getMessage();
    logger.error("Exception: ",ex);
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("error/error");
    modelAndView.addObject("message", message);
    return modelAndView;
  }
}