package org.sms.project.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 异常拦截器
 * @author Sunny
 * @since 1.8.0
 */
@Component
public class SysExceptionResolver implements HandlerExceptionResolver {

  @Override
  public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    String message = ex.getMessage();
    ModelAndView modelAndView = new ModelAndView("/jsp/error/404.jsp");
    modelAndView.addObject("errorMsg", message);
    return modelAndView;
  }
}