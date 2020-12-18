<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>e-shop</title>
<style>
#nav>ul>li {
	float: left;
	margin-left: 50px;
}

#login {
	clear: both;
}
</style>
</head>
<body>
	<b>E-shop</b>
	<%
		out.println(request.getAttribute("username"));
	%>
	<%@ include file="header.jsp"%>
	<%@include file="footer.jsp"%>
</body>
</html>