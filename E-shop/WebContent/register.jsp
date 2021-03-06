<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>注册页面-</title>
<meta name="keywords" content="注册页面" />
<link rel="stylesheet" href="./css/reset.css" />
<link rel="stylesheet" href="./css/common.css" />
<link rel="stylesheet" href="./css/font-awesome.min.css" />
</head>
<body>
	<div class="wrap login_bg">
		<div class="logo"></div>
		<div class="register_box">
			<div class="login_title">注册</div>
			<form action="${pageContext.request.contextPath}/register_servlet"
				method="post" name="form" onSubmit="return beforeSubmit(this);">

				<div class="form_text_ipt">
					<input name="username" type="text" placeholder="用户名">
				</div>
				<div class="ececk_warning">
					<span>用户名不能为空</span>
				</div>
				<div class="form_text_ipt">
					<input name="password" type="password" id="password" placeholder="密码">
				</div>
				<div class="ececk_warning">
					<span>密码不能为空</span>
				</div>
				<div class="form_text_ipt">
					<input name="repassword" type="password" id="repassword" placeholder="重新输入密码">
				</div>
				<div class="ececk_warning">
					<span>密码不能为空</span>
				</div>
				<input name="enpassword" type="hidden" id="enpassword" value="">
				<input name="enusername" type="hidden" id="enusername" value="">
				<div class="form_btn">
					<input type="submit" name="submit" value="注册">
				</div>
				<div class="form_reg_btn">
					<span>已有帐号？</span><a href="login.jsp">马上登录</a>
				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	<script type="text/javascript" src="js/register.js"></script>
	<script type="text/javascript" src="js/jsencrypt.js"></script>
</body>
</html>
