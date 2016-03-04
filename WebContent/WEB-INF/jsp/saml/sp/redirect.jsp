<%@page import="org.sms.SysConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%
	System.out.print("++++++++++++++++++++++++++++++");
	System.out.print((String)session.getAttribute(SysConstants.REDIRECT_URL_KEY));
	System.out.print("++++++++++++++++++++++++++++++");
	response.sendRedirect((String)session.getAttribute(SysConstants.REDIRECT_URL_KEY));
%>