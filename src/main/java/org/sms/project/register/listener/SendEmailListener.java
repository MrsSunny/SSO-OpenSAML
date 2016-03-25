package org.sms.project.register.listener;

import org.sms.project.register.event.RegisterEvent;
import org.sms.project.user.entity.User;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SendEmailListener implements ApplicationListener<RegisterEvent> {
  
  @Async
  @Override
  public void onApplicationEvent(final RegisterEvent event) {
    System.out.println("发送邮件信息是：" + ((User) event.getSource()));
  }
}