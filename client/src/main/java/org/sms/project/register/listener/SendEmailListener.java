package org.sms.project.register.listener;

import org.sms.project.register.event.RegisterEvent;
import org.sms.project.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SendEmailListener implements ApplicationListener<RegisterEvent> {
  
  @Autowired
  private ApplicationContext context;
  
  @Async
  @Override
  public void onApplicationEvent(final RegisterEvent event) {
    User user = (User) event.getSource();
    System.out.println(user.getPassword());
    System.out.println("发送邮件信息是：" + ((User) event.getSource()));
  }
}