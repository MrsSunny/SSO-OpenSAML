<%@page import="org.sms.SysConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Login</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="" />
<!-- Bootstrap Core CSS -->
<link href="/css/bootstrap.min.css" rel='stylesheet' type='text/css' />
<!-- Custom CSS -->
<link href="/css/style.css" rel='stylesheet' type='text/css' />
<link href="/css/font-awesome.css" rel="stylesheet">
<script src="/js/jquery.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
</head>
<body id="login">
	<div class="login-logo">
		<a href="index.html"><img src="/images/logo/sunny.jpg" alt="" /></a>
	</div>
	<div class="app-cam">
		<form action="loginFilter" method="POST">
			<input type="text" name="email" class="text" value="E-mail address" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'E-mail address';}">
			<input type="password" name="password" value="Password" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Password';}">
			<div class="submit">
				<input type="submit" onclick="myFunction()" value="Login">
			</div>
		</form>
	</div>
</body>
</html>