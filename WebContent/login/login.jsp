<%@page import="org.sms.SysConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>权限受限</title>
</head>
<%
	Object object = session.getAttribute(SysConstants.LOGIN_USER);
	if (object == null) {
	  request.getRequestDispatcher("/SAML2/sendArtifactToIDP").forward(request, response);
	}
%>
<body>
	权限受到限制！！！！！！！
</body>
</html>