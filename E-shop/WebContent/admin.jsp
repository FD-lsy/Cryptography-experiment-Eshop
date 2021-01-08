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
<html class="x-admin-sm">
<head>
    <meta charset="UTF-8">
    <title>商品列表</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <link rel="stylesheet" href="./css/font.css">
    <link rel="stylesheet" href="./css/index.css">
    <script src="./lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="./js/index.js"></script>

</head>
<body>
<%
	String gid = "-1";
	List<Goods> goodsList = new ArrayList<Goods>();
	//orderDelete是String类型的八位序列号
	int orderDelete = Key.genInt();
	session.setAttribute("orderDelete", orderDelete);
	try {
		Connection conn = DBUtil.getConnection();
		Statement st = conn.createStatement();
		String sql = "select* from goods";
		ResultSet rs = st.executeQuery(sql);
		while (rs.next()) {
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
<div class="x-nav">
          <span class="layui-breadcrumb">
            <a href="">首页：商品列表</a>
          </span>
    <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right" onclick="location.reload()" title="刷新">
        <i class="layui-icon layui-icon-refresh" style="line-height:30px"></i></a>
</div>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">
                    <button class="layui-btn mr50" onclick="xadmin.open('添加商品','goods_add.jsp')"><i class="layui-icon"></i>添加</button>
                </div>
                <div class="layui-card-body layui-table-body layui-table-main">
                    <table class="layui-table layui-form">
                        <thead>
                        <tr>
                            <th>序列号</th>
                            <th>商品名称</th>
                            <th>价格</th>
                            <th>库存</th>
                            <th>商品图片链接</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
						for (int i = 0; i < goodsList.size(); i++) {
							gid = goodsList.get(i).getGid()+"";
							String engid = RSA.encrypt(goodsList.get(i).getGid()+"", Key.getMyPublicKey());
						%>
						<tr>
                            <td>
                                <%=gid%>
                            </td>

                            <td >
                                <%=goodsList.get(i).getName()%>
                            </td>
                            <td >
                                <%=goodsList.get(i).getPrice()%>
                            </td>
                            <td>
                                <%=goodsList.get(i).getQuantity()%>
                            </td>
                            <td >
                                <%=goodsList.get(i).getLink()%>
                            </td>
                            <td class="td-manage">
                           		<a title='编辑' href='javascript:;' onclick="xadmin.open('编辑','goods_modify.jsp?engid=<%=engid%>')"
                           		 class='ml-5' style='text-decoration:none'>
                            	<i class="layui-icon">&#xe642;</i>
                            	</a>
                             
                            <%out.print("<a title='删除' href='javascript:;' onclick='deletegood("+ goodsList.get(i).getGid()+
                             	","+orderDelete+")' style='text-decoration:none'><i class='layui-icon'>&#xe640;</i></a>"); %>
                            </td>
                        </tr>
                            <%
						}
                            %>
                        </tbody>
                    </table>
                </div>
                <div class="layui-card-body ">
                    <div class="page">
                        <div>
                            <a class="prev" href="">&lt;&lt;</a>
                            <span class="current">1</span>
                            <a class="num" href="">2</a>
                            <a class="num" href="">3</a>
                            <a class="num" href="">…</a>
                            <a class="num" href="">10</a>
                            <a class="next" href="">&gt;&gt;</a></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="js/jsencrypt.js"></script>
<script type="text/javascript" src="js/admin.js"></script>
</html>


