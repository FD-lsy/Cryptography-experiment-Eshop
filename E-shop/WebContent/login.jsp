<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<title>登录页面-</title>
<meta name="keywords" content="登录页面" />
<meta name="description" content="哈尔滨工业大学密码学实验1180300802李尚宇" />
<link rel="stylesheet" href="./css/reset.css" />
<link rel="stylesheet" href="./css/common.css" />
<link rel="stylesheet" href="./css/font-awesome.min.css" />
</head>
<body>
	<div class="wrap login_bg">
		<div class="logo"></div>
		<div class="login_box">
			<div class="login_title">登录</div>
			<form action=" ${pageContext.request.contextPath}/login_servlet"
				method="post" name="form" onSubmit="return beforeSubmit(this);">
				<div class="form_text_ipt">
					<input type="text" name="username" placeholder="用户名">
				</div>
				<div class="ececk_warning">
					<span>用户名不能为空</span>
				</div>
				<div class="form_text_ipt">
					<input type="password" name="password" placeholder="密码">
				</div>
				<div class="ececk_warning">
					<span>密码不能为空</span>
				</div>
				<div class="form_btn">
					<input type="submit" name="submit" value="登录">
				</div>
				<div class="form_reg_btn">
					<span>还没有帐号？</span><a href="register.jsp">马上注册</a>
				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript" src="js/login.js"></script>
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	<div style="text-align: center;"></div>
</body>
</html>
