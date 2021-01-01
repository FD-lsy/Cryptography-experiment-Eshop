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
<title>购物车页面</title>
<link rel="stylesheet" href="./css/eshop.css" type="text/css">
<link rel="stylesheet" href="./css/common.css" type="text/css">
<script>
	function testButton() {
		window.location = "e_shop.jsp";
	}
</script>
<body>
	<%
		List<Goods> goodsList = new ArrayList<Goods>();
	//num是String类型的八位序列号
	String order = Key.genString();
	session.setAttribute("order", order);
	float amount = 0;
	if (session.getAttribute("uid") != null) {
		List<Integer> id = new ArrayList<Integer>();
		List<Integer> num = new ArrayList<Integer>();
		int uid = (int) session.getAttribute("uid");
		try {
			int i = 0;
			Connection conn = DBUtil.getConnection();
			Statement st = conn.createStatement();
			String sql = "select* from shoppingcart where uid=" + uid;
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
		id.add(rs.getInt("gid"));
		num.add(rs.getInt("number"));
		Goods g = new Goods();
		i++;
			}
			for (i = 0; i < id.size(); i++) {
		Goods g = new Goods();
		g.setGid(id.get(i));
		g.setNumber(num.get(i));
		String sql1 = "select* from goods where gid=" + id.get(i);
		rs = st.executeQuery(sql1);
		if (rs.next()) {
			g.setName(rs.getString("name"));
			g.setPrice(rs.getFloat("price"));
			g.setQuantity(rs.getInt("quantity"));
			g.setLink(rs.getString("link"));
		}
		amount += g.getNumber() * g.getPrice();
		if (g.getNumber() > 0) {
			goodsList.add(g);
		}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DBUtil.Close();
		}
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
				<div class="hello">! 您的购物车界面：</div>
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
			</div>
		</header>
		<!-- banner -->

		<!-- 商品信息 -->
		<section id="recommend">
			<div class="row">
				<div class="rec_title_c">
					<button type="button" onclick="testButton()" class="back fr mr50">返回购物页面
					</button>
					Your Shopping Cart!
				</div>
				<ul class="rec_list">

					<%
						for (int i = 0; i < goodsList.size(); i++) {
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
						<div class="rec_tag_c">
							价格 ¥<%=goodsList.get(i).getPrice()%>
							× 数量
							<%=goodsList.get(i).getNumber()%>
						</div>
						<form action=" ${pageContext.request.contextPath}/cart_servlet"
							method="post" class="form_sub">
							<%
								out.print("<input type='hidden' name='engid' value='"
									+ RSA.encrypt(goodsList.get(i).getGid() + "", Key.getMyPublicKey()) + "'>");
							out.print("<input type='hidden' name='ennumber' value='"
									+ RSA.encrypt(goodsList.get(i).getNumber() + "", Key.getMyPublicKey()) + "'>");
							out.print("<input type='hidden' name='enquantity' value='"
									+ RSA.encrypt(goodsList.get(i).getQuantity() + "", Key.getMyPublicKey()) + "'>");
							out.print("<input type='hidden' name='enorder' value='" + RSA.encrypt(order, Key.getMyPublicKey()) + "'>");
							%>
							<input type="submit" name="submit" value="移出购物车">
						</form>
					</li>
					<%
						}
					%>
				</ul>
			</div>
		</section>
		<!-- 商品信息 end -->
		<%
			out.print("<footer id='footer' style='padding-top:" + ((goodsList.size()-1) / 4 + 1) * 350 + "px'>");
		%>
		<div class="row">
			<h2 class="center">
				总金额为：<%=amount%>￥</h2>
			<form action=" ${pageContext.request.contextPath}/send_m_servlet"
				method="post">
				<%
					out.print("<input type='hidden' name='amount' value='" + amount + "'>");
				%>
				<input type="submit" class="submit" name="submit" value="确认支付">
			</form>
		</div>
		<%
			out.print("</footer>");
		%>
	</div>
</body>
</html>