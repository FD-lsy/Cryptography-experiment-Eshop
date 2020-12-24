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
<script >
function testButton(){
	window.location="shopping_cart.jsp";
}
</script>

<script type="text/javascript" src="js/index/scrolltext.js"></script>
<script type="text/javascript" src="js/index/jquery.min.js"></script>
<script type="text/javascript" src="js/index/jquery.cookie.js"></script>
<script type="text/javascript" src="js/index/juzi.cookie.js"></script>
<script type="text/javascript" src="js/index/juzi.js"></script>
<script type="text/javascript" src="js/index/zdc.js"></script>
<script type="text/javascript" src="js/index/banner.js"></script>
<script type="text/javascript" src="js/index/jquery.scrollTo.js"></script>
</head>
<body>
	<%
		List<Goods> goodsList = new ArrayList<Goods>();
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
			goodsList.add(g);
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
					<img src="images/recommend_title.png" width="161" height="35">
					<br> best sales Recommend
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
						<form action=" ${pageContext.request.contextPath}/eshop_servlet"
							method="post" class="form_sub">
							<%
							out.print("<input type='hidden' name='gid' value='" + goodsList.get(i).getGid() + "'>");
							out.print("<input type='hidden' name='quantity' value='" + goodsList.get(i).getQuantity() + "'>");
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
		<footer id="footer">
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
		</footer>
	</div>
	<script>
		$(function() {
			//代码初始化
			var size = $('.rotaion_list li').size();
			for (var i = 2; i <= size; i++) {
				var li = "<span>" + i + "</span >";
				$('.yx-rotation-focus').append(li);
			}
			;
			//手动控制轮播图
			$('.rotaion_list li').eq(0).show();
			$('.yx-rotation-focus span').eq(0).addClass('hover');
			$('.yx-rotation-focus span').mouseover(
					function() {
						$(this).addClass('hover').siblings().removeClass(
								'hover');
						var index = $(this).index();
						i = index;
						$('.rotaion_list li').eq(index).stop().fadeIn(300)
								.siblings().stop().fadeOut(300);
					})
			//自动播放
			var i = 0;
			var t = setInterval(move, 1500);
			//自动播放核心函数
			function move() {
				i++;
				if (i == size) {
					i = 0;
				}
				$('.yx-rotation-focus span').eq(i).addClass('hover').siblings()
						.removeClass('hover');
				$('.rotaion_list li').eq(i).fadeIn(300).siblings().fadeOut(300);
			}

			//向右播放核心函数
			function moveL() {
				i--;
				if (i == -1) {
					i = size - 1;
				}
				$('.yx-rotation-focus span').eq(i).addClass('hover').siblings()
						.removeClass('hover');
				$('.rotaion_list li').eq(i).fadeIn(300).siblings().fadeOut(300);
			}
			$('.yx-rotaion .left').click(function() {
				moveL();
			})
			$('.yx-rotaion .right').click(function() {
				move();
			})
			//鼠标移入移除
			$('.yx-rotaion').hover(function() {
				clearInterval(t);
			}, function() {
				t = setInterval(move, 1500);
			})
		})
	</script>

</body>

</html>