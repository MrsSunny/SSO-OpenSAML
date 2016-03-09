package org.sms.project.register.event;

import org.sms.organization.user.entity.User;
import org.springframework.context.ApplicationEvent;

public class RegisterEvent extends ApplicationEvent {

  private static final long serialVersionUID = 4670113061631261959L;

  public RegisterEvent(User user) {
    super(user);
  }
}