package org.sms.project.register.listener;

import org.sms.organization.user.entity.User;
import org.sms.project.register.event.RegisterEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class CreateIndexListener implements ApplicationListener<RegisterEvent> {

  @Async
  @Override
  public void onApplicationEvent(final RegisterEvent event) {
    System.out.println("创建索引信息" + ((User) event.getSource()).getUsername());
  }
}
