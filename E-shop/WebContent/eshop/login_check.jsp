<!-- 暂时无用 -->
<%@page import="java.sql.*"%>
<%@page import="DBTool.DBUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
//请求获取login.jsp的用户名username和password
String username = request.getParameter("username");
String password = request.getParameter("password");
//连接数据库
Connection conn = DBUtil.getConnection();
Statement st = conn.createStatement();
//搜索此username和password在数据库是否存在
String sql = "select * from user where username='" + username + "'and password='" + password + "'";
//运行sql语句，并把得到的结果放入结果集ResultSet中
ResultSet rs = st.executeQuery(sql);
//判断这个结果集是否存在，一般username只有一个
if (rs.next()) {
	//设置一个username，将后面username其内容赋值给前面一个username，以便下一个页面使用
	request.setAttribute("username", username);
	//跳转页面到e_shop.jsp
	request.getRequestDispatcher("e_shop.jsp").forward(request, response);
} else {
	//设置一个error,将后面的字赋给这个error，以便先一个跳转页面的使用，request的作用域有限
	request.setAttribute("error", "用户名或密码错误!!!");
	request.getRequestDispatcher("login.jsp").forward(request, response);
}
conn.close();
rs.close();
%>

