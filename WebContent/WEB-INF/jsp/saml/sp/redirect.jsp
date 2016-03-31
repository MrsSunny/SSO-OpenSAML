<%@page import="java.net.URLEncoder"%>
<%@page import="org.sms.SysConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%
	Object object = request.getAttribute(SysConstants.ERROR_LOGIN);
	if (null != object) {
	  if (object instanceof Boolean) {
	    boolean success = (Boolean) object;
	    if (!success) {
	  		Object redirectUrl = session.getAttribute(SysConstants.REDIRECT_URL_KEY);
	  		if (redirectUrl == null) {
	  		  response.sendRedirect(SysConstants.DEFAULT_CUSTOMER_INDEX);
	  		} else {
	  		  response.sendRedirect((String)session.getAttribute(SysConstants.REDIRECT_URL_KEY));
	  		}
	    } else {
	      response.sendRedirect(SysConstants.LOGIN_PAGE);
	    }
	  } else {
			response.sendRedirect(SysConstants.LOGIN_PAGE);
	  }
	} else {
		response.sendRedirect(SysConstants.LOGIN_PAGE);
	}
%>