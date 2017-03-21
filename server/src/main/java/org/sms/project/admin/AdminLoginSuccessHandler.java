package org.sms.project.admin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.PublicKey;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opensaml.xml.util.Base64;
import org.sms.SysConstants;
import org.sms.opensaml.service.SamlService;
import org.sms.project.encrypt.rsa.RSACoder;
import org.sms.project.helper.CertificateHelper;
import org.sms.project.helper.SessionHelper;
import org.sms.project.user.entity.User;
import org.sms.project.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * @author Sunny
 */
@Component
public class AdminLoginSuccessHandler implements AuthenticationSuccessHandler {
  
  @Autowired
  private SamlService samlService;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    User user = (User) SessionHelper.get(request, SysConstants.LOGIN_USER);
    
    /**
     * 制作Ticket
     */
    String ticket = this.buildTicket(user);
    /**
     * 验证成功以后需要向客户端记录Cookie
     */
    String cookieTicket = encryptTicket(ticket);
    this.addSSOCookie(response, cookieTicket);
    response.sendRedirect(SysConstants.DEFAULT_CUSTOMER_INDEX);
  }
  
  private String encryptTicket(String ticket) {
    PublicKey publicKey = CertificateHelper.getRSAPublicKey();
    try {
      byte[] encryptArray = RSACoder.INSTANCE.encryptByPublicKey(publicKey, ticket.getBytes());
      return Base64.encodeBytes(encryptArray);
    } catch (Exception e) {
      throw new RuntimeException("加密数据错误");
    }
  }
  
  private String buildTicket(User user) {
    if (Objects.isNull(user)) {
      return null;
    }
    StringBuilder ticketBuilder = new StringBuilder();
    ticketBuilder.append(user.getId());
    ticketBuilder.append(SysConstants.TICKET_SPILT);
    ticketBuilder.append(user.getEmail());
    ticketBuilder.append(SysConstants.TICKET_SPILT);
    ticketBuilder.append(DateUtil.getSpecifiedDayAfter(SysConstants.DEFAULT_EXPIRE));
    return ticketBuilder.toString();
  }
  
  private void addSSOCookie(HttpServletResponse response, String string) throws UnsupportedEncodingException {
    Cookie cookie = new Cookie(SysConstants.IDP_TICKET, URLEncoder.encode(string, SysConstants.CHARSET));
    cookie.setDomain("." + SysConstants.DOMAIN);
    cookie.setPath("/");
    cookie.setMaxAge(SysConstants.DEFAULT_EXPIRE * 24 * 60 * 60);
    response.addCookie(cookie);
  }
}