package org.sms.project.monitor;

import org.aspectj.lang.JoinPoint;

/**
 * @author zhenxing.Liu
 */
public class SysMonitor {

  public void doAfter(JoinPoint jp) {
    System.out.println("方法执行后");
  }

  public void doBefore(JoinPoint jp) {
    System.out.println("方法执行开始");
  }

  /**
   * 错误信息 记录日志
   */
  public void doThrowing(JoinPoint jp, Throwable ex) {
    ex.printStackTrace();
  }

  public void sendEx(String ex) {
  }
}
