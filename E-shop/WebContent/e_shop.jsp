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
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>电子商城</title>
<link rel="stylesheet" href="./css/eshop.css" type="text/css">
<link rel="stylesheet" href="./css/common.css" type="text/css">
<script>
	function testButton() {
		window.location = "shopping_cart.jsp";

	}
</script>
</head>
<body>
	<%
		List<Goods> goodsList = new ArrayList<Goods>();
	//num是String类型的八位序列号
	String num = Key.genString();
	session.setAttribute("num", num);
	try {
		Connection conn = DBUtil.getConnection();
		Statement st = conn.createStatement();
		String sql = "select* from goods";
		ResultSet rs = st.executeQuery(sql);
		while (rs.next()) {
			/* System.out.print("找到！！"); */
			Goods g = new Goods();
			g.setGid(rs.getInt("gid"));
			g.setName(rs.getString("name"));
			g.setPrice(rs.getFloat("price"));
			g.setQuantity(rs.getInt("quantity"));
			g.setLink(rs.getString("link"));
			if(g.getQuantity()!=0){
				goodsList.add(g);
			}
		}
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		DBUtil.Close();
	}
	%>
	<div id="wrap">
		<header id="header">
			<div class="row">
				<img src="images/logo.jpg" alt="lsy电子商城" class="logo">
				<%
					if (session.getAttribute("username") != null) {
				%>
				<div class="hello">Hello</div>

				<div class="username"><%=session.getAttribute("username")%></div>
				<div class="hello">! Welcome to Lsy E-shop</div>
				<%
					}
				%>
				<%
					if (session.getAttribute("username") == null) {
				%>
				<div class="hello">Hello</div>
				<div class="hello">
					<a href=login.jsp>请先登录</a>
				</div>
				<%
					}
				%>
				<div class="fr">
					<div class="head_search">
						<input id="keywords" type="text" class="head_input" value=""
							placeholder="输入关键字" onkeydown="if(event.keyCode==13){search();}">
						<button type="button" class="head_search_btn" id="search">
						</button>
					</div>
				</div>
				<div class="search_text">搜索商品</div>
			</div>
		</header>
		<!-- banner -->

		<!-- 商品信息 -->
		<section id="recommend">
			<div class="row">
				<div class="rec_title">
					<button type="button" onclick="testButton()" class="button fr mr50">查看购物车</button>
					<img src="images/recommend_title.png" class="img" width="161"
						height="35"> <br> best sales Recommend
				</div>
				<ul class="rec_list">

					<%
						for (int i = 0; i < goodsList.size() && goodsList.get(i).getQuantity() != 0; i++) {
					%>
					<li>
						<div class="rec_pic">
							<%
								out.print("<img src='" + goodsList.get(i).getLink() + "'width='220' height='220'>");
							%>
						</div>
						<div class="rec_title2">
							<b> <%=goodsList.get(i).getName()%>
							</b>
						</div>
						<div class="rec_tag">
							售价 ¥<%=goodsList.get(i).getPrice()%></div>
						<form action="${pageContext.request.contextPath}/eshop_servlet"
							method="post" class="form_sub">
							<%
								out.print("<input type='hidden' name='engid' value='"
									+ RSA.encrypt(goodsList.get(i).getGid() + "", Key.getMyPublicKey()) + "'>");
							out.print("<input type='hidden' name='enquantity' value='"
									+ RSA.encrypt(goodsList.get(i).getQuantity() + "", Key.getMyPublicKey()) + "'>");
							out.print("<input type='hidden' name='ennum' value='" + RSA.encrypt(num, Key.getMyPublicKey()) + "'>");
							%>
							<input type="submit" name="submit" value="加入购物车">
						</form>
					</li>
					<%
						}
					%>

				</ul>
			</div>
		</section>
		<!-- 商品信息 end -->
		<h1>&nbsp;</h1>
		<%
			out.print("<footer id='footer' style='padding-top:" + ((goodsList.size()-1)/4+1) * 340 + "px'>");
		%>
		<div class="row">
			<ul class="foot_service">
				<li class="s1">卓越品质 <br> <span class="font14 font_grey">
						100%正品保障 </span>
				</li>
				<li class="s2">金牌服务 <br> <span class="font14 font_grey">
						为您呈现最优服务 </span>
				</li>
				<li class="s3">手续简单 <br> <span class="font14 font_grey">
						专业人员上门办理 </span>
				</li>
				<li class="s4">轻松还款 <br> <span class="font14 font_grey">
						足不出户轻松还款 </span>
				</li>
				<li class="s5">专业配送 <br> <span class="font14 font_grey">
						快速安全 </span>
				</li>
			</ul>
		</div>
		<%
			out.print("</footer>");
		%>
	</div>
</body>
</html>