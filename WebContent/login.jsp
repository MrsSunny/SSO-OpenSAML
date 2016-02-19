<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%-- ${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message} --%> <!-- 输出异常信息 -->
	<form action="loginFilter" method="POST">
		<c:if test="${param.error != null}">
			<p>用户名和密码错误.</p>
		</c:if>
		<c:if test="${param.logout != null}">
			<p>You have been logged out.</p>
		</c:if>
		<p>
			<label for="username">Username</label> <input type="text" id="username" name="username" />
		</p>
		<p>
			<label for="password">Password</label> <input type="password" id="password" name="password" />
		</p>
		<p>
			 <input type="text" id="authRequest" name="authRequest"  value="${param.authRequest}" />
		</p>
		<button type="submit" class="btn">Log in</button>
	</form>
</body>
</html>