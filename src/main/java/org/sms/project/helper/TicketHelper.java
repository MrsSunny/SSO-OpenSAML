package org.sms.project.helper;

import org.sms.SysConstants;
import org.sms.project.user.entity.User;
import org.sms.project.util.DateUtil;

/**
 * @author Sunny
 */
public class TicketHelper {

  public static boolean check(String ticket) {
    
    return Boolean.FALSE;
  }
  
  public static String getTicket(User user) {
    StringBuilder ticketBuilder = new StringBuilder();
    ticketBuilder.append(user.getId());
    ticketBuilder.append(SysConstants.TICKET_SPILT);
    ticketBuilder.append(user.getEmail());
    ticketBuilder.append(SysConstants.TICKET_SPILT);
    ticketBuilder.append(DateUtil.getSpecifiedDayAfter(SysConstants.DEFAULT_EXPIRE));
    return ticketBuilder.toString();
  }
}
