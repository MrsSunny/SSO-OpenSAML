<%@page import="org.sms.SysConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SPBUILDARTIFACT</title>
</head>
<body>
	Artifact:<%=request.getAttribute(SysConstants.ARTIFACT_KEY)%>
	Token:<%=request.getAttribute(SysConstants.TOKEN_KEY)%>
	<form action="<%=SysConstants.IDPRECEIVESPARTIFACT_URL%>" method="post">
		<input type="hidden" name="artifact" value="<%=request.getAttribute(SysConstants.ARTIFACT_KEY)%>" />
		<input type="hidden" name="token" value="<%=request.getAttribute(SysConstants.TOKEN_KEY)%>" />
		<input type="submit" value="Submit" />
	</form>
</body>
</html>