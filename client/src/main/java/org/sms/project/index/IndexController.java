package org.sms.project.index;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Sunny
 */
@Controller
@RequestMapping("/indexPage")
public class IndexController {

  @RequestMapping(value = "", method = RequestMethod.GET)
  public String toIndex(HttpServletRequest request) {
    return "/index/index";
  }
}