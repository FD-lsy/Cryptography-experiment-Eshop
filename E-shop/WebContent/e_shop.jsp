<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>电子商城</title>
<link rel="stylesheet" href="./css/eshop.css" type="text/css">

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
	<div id="wrap" class="clearfix">
		<header id="header">
			<div class="row">
				<img src="images/logo.jpg" alt="lsy电子商城" class="logo">
				<div class="fr">
					<div class="search_text">搜索商品</div>
					<div class="head_search">
						<input id="keywords" type="text" class="head_form" value=""
							placeholder="输入关键字" onkeydown="if(event.keyCode==13){search();}">
						<button type="button" class="head_search_btn" id="search">
						</button>
					</div>
				</div>
			</div>
		</header>
		<!-- banner -->

		<!-- 商品信息 -->
		<section id="recommend">
			<div class="row">
				<div class="rec_title">
					<img src="images/recommend_title.png" width="161" height="35">
					<br> best sales Recommend
				</div>
				<ul class="rec_list">
					<li>
						<div class="rec_pic">
							<img src="images/g1.jpg" data-original="#" width="220"
								height="220">
						</div>
						<div class="rec_title2">
							<a href="javascript:void(0);" title="OPPO R9	全网通 手机"> OPPO R9
								全网通 手机</a>
						</div>
						<div class="rec_tag">售价 ¥2999</div>
					</li>

					<li>
						<div class="rec_pic">
							<img src="images/g2.jpg" data-original="#" width="220"
								height="220">
						</div>
						<div class="rec_title2">
							<a href="javascript:void(0);" title="魅族 MEIZU  魅蓝note2 联通版 手机">
								魅族 MEIZU 魅蓝note2 </a>
						</div>
						<div class="rec_tag">售价 ¥1099</div>
					</li>

					<li>
						<div class="rec_pic">
							<img src="images/g3.jpg" data-original="#" width="220"
								height="220">
						</div>
						<div class="rec_title2">
							<a href="javascript:void(0);" title="魅族 MEIZU  MX5 PRO 公开版 手机">
								魅族 MEIZU MX5 PRO </a>
						</div>
						<div class="rec_tag">售价 ¥2899</div>
					</li>

					<li>
						<div class="rec_pic">
							<img src="images/g4.jpg" data-original="#" width="220"
								height="220">
						</div>
						<div class="rec_title2">
							<a href="javascript:void(0);" title="华为 HUAWEI 荣耀X2 移动联通版 平板电脑">
								华为 HUAWEI 荣耀X2 移动联 </a>
						</div>
						<div class="rec_tag">售价 ¥1988</div>
					</li>

					<li>
						<div class="rec_pic">
							<img src="images/g5.jpg" data-original="#" width="220"
								height="220">
						</div>
						<div class="rec_title2">
							<a href="javascript:void(0);" title="华为 HUAWEI 荣耀 7I   移动版 手机">
								华为 HUAWEI 荣耀 7I </a>
						</div>
						<div class="rec_tag">售价 ¥1699</div>
					</li>

					<li>
						<div class="rec_pic">
							<img src="images/g6.jpg" data-original="#" width="220"
								height="220">
						</div>
						<div class="rec_title2">
							<a href="javascript:void(0);" title="华硕笔记本电脑 轻薄本 2020新款">
								华硕笔记本电脑 轻薄本 2020新款 </a>
						</div>
						<div class="rec_tag">售价 ¥5580</div>
					</li>

					<li>
						<div class="rec_pic">
							<img src="images/g7.jpg" data-original="#" width="220"
								height="220">
						</div>
						<div class="rec_title2">
							<a href="javascript:void(0);" title="新品笔记本电脑 新款绿色清新版">
								新品笔记本电脑 新款绿色清新版</a>
						</div>
						<div class="rec_tag">售价 ¥5380</div>
					</li>

					<li>
						<div class="rec_pic">
							<img src="images/g8.jpg" data-original="#" width="220"
								height="220">
						</div>
						<div class="rec_title2">
							<a href="javascript:void(0);" title="二手旧款iPad 八成新"> 二手旧款iPad
								八成新</a>
						</div>
						<div class="rec_tag">售价 ¥2499</div>
					</li>
				</ul>
			</div>
		</section>
		<!-- 商品信息 end -->

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