<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ page session="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Page</title>
<style type="text/css">
	.error {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #a94442;
	background-color: #f2dede;
	border-color: #ebccd1;
}

.msg {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #31708f;
	background-color: #d9edf7;
	border-color: #bce8f1;
}
</style>
</head>
<body onload="document.loginForm.username.focus();">
	<h1>Spring Security Login Form - Database Authentication</h1>
	<br />
	<c:if test="${not empty error}">
		<h1 align="center" class="error">${error}</h1>
	</c:if>
	<c:if test="${not empty message}">
		<h1 align="center" class="msg">${message}</h1>
	</c:if>

	<form name="loginForm" action='<c:url value="/j_spring_security_check" />' method="POST">
		
		<table border="0" align="center">
		
			<tr>
				<td colspan="2" align="center"><h1>Enter Login Credentials : </h1></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>USERNAME : </td>
				<td><input type="text" name="username" /></td>
			</tr>
			<tr>
				<td>PASSWORD : </td>
				<td><input type="password" name="password" /></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit" value="Login" /></td>
			</tr>
		
		</table>
		
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		
	</form>
</body>
</html>