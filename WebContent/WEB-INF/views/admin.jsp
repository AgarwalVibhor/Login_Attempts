<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ page session="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin Page</title>
</head>
<body>

	<h1>Title : ${title}</h1>
	<br />
	<h1>Message : ${message}</h1>
	<br />
	<c:url value="/j_spring_security_logout" var="logoutUrl" />
	<form id="logoutForm" action="${logoutUrl}" method="POST">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</form>
	
	<script type="text/javascript">
		function formSubmit(){
			document.getElementById("logoutForm").submit();
		}
	</script>
	
	<c:if test="${pageContext.request.userPrincipal.name != null}">
		<h2>
			Admin : ${pageContext.request.userPrincipal.name} | <a href="javascript:formSubmit()">Logout</a>
		</h2>
	</c:if>

</body>
</html>