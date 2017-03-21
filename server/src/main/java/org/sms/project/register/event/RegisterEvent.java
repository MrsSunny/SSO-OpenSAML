package org.sms.project.register.event;

import org.sms.project.user.entity.User;
import org.springframework.context.ApplicationEvent;

/**
 * 注册事件类，用于处理注册成功后执行的其他操作
 * @author Sunny
 */
public class RegisterEvent extends ApplicationEvent {

  private static final long serialVersionUID = 4670113061631261959L;

  public RegisterEvent(User user) {
    super(user);
  }
}