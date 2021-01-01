<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="DBTool.DBUtil"%>
<%@page import="DBTool.Goods"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="encrypt.Key"%>
<%@page import="encrypt.RSA"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>文章添加</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">

<link rel="stylesheet" href="./css/font.css">
<link rel="stylesheet" href="./css/index.css">
<script src="./lib/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript" src="./js/index.js"></script>

<!--<style>
        .imgs{display: none;}
        .picture{display: none;}
    </style>-->

<!--百度编辑器-->
<script src="./ueditor/ueditor.config.js"></script>
<script src="./ueditor/ueditor.all.min.js"></script>
<script src="./ueditor/lang/zh-cn/zh-cn.js"></script>
</head>
<body>
	<%
		String order = Key.genString();
	session.setAttribute("order", order);
	%>
	<div class="layui-card">
		<form class="layui-form layui-form-pane" method="post"
			action="${pageContext.request.contextPath}/goodsadd_servlet"
			onSubmit="return beforeSubmit(this);">
			<div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
				<ul class="layui-tab-title">
					<li class="layui-this">商品信息</li>
				</ul>

				<div class="layui-tab-content">
					<div class="layui-tab-item layui-show">
						<input type="hidden" name="enorder" value=<%=order%>>
						<div class="layui-form-item">
							<label class="layui-form-label"> <span class='x-red'>*</span>商品名称
							</label>
							<div class="layui-input-block">
								<input type="text" name="enname" autocomplete="off" value=""
									placeholder="空制在15个汉字以内" class="layui-input">
							</div>
						</div>

						<div class="layui-form-item">
							<label class="layui-form-label"> <span class='x-red'>*</span>商品售价
							</label>
							<div class="layui-input-block">
								<input type="text" name="enprice" autocomplete="off" value=""
									placeholder="该商品在本店的售价" class="layui-input">
							</div>
						</div>

						<div class="layui-form-item">
							<label class="layui-form-label"> <span class='x-red'>*</span>商品库存
							</label>
							<div class="layui-input-block">
								<input type="text" name="enquantity" autocomplete="off" value=""
									placeholder="该商品本次进货量" class="layui-input">
							</div>
						</div>

						<div class="layui-form-item">
							<label for="link" class="layui-form-label"> <span
								class="x-red">*</span>商品缩略图
							</label>
							<div class="layui-input-inline">
								<button type="button" class="layui-btn" id="test3" onclick="$('#YYZZFBSMJ').click();">
									<i class="layui-icon"></i>上传文件
								</button>
                				<input type="file" name="file" id="YYZZFBSMJ" style="display: none;" onchange="fileType(this)">
							</div>
						</div>

						<div class="layui-form-item">
							<button class="layui-btn" type="submit" lay-filter="add"
								lay-submit="">保存</button>
						</div>
						<!--</form>-->
						<div style="height: 100px;"></div>
					</div>
				</div>
			</div>
		</form>
	</div>
	<script type="text/javascript" src="js/jsencrypt.js"></script>
	<script type="text/javascript" src="js/admin.js"></script>
</body>
</html>