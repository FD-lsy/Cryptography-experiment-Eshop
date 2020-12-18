package servlet;

import java.io.IOException;
import java.io.PrintWriter;
//import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DBTool.DBUtil;

@WebServlet("/register_servlet")
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public RegisterServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		// 向服务器发送请求获取到参数
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		try {
			Connection conn = DBUtil.getConnection();
			Statement st = conn.createStatement();
			String sql = "select * from database.user where username='" + username + "' and password='"
					+ password + "'";
			String sql1 = "insert into database.user(username,password,admin) values('" + username + "','"+ password+"',0)";
			ResultSet rs = st.executeQuery(sql);
			//用户尚未注册
			if(!rs.next()) {
				st.execute(sql1);
				out.print("<script language='javascript'>alert('succcess');window.location.href='/E-shop/eshop/register.jsp';</script>");
				request.setAttribute("flag", "0");
				request.getRequestDispatcher("/eshop/login.jsp").forward(request, response);
				out.close();
			}
			//用户已存在
			else {
				out.print("<script language='javascript'>alert('Wrong');window.location.href='/E-shop/eshop/register.jsp';</script>");
				//request.setAttribute("flag", "0");
				//request.getRequestDispatcher("/eshop/login.jsp").forward(request, response);
				out.flush();
				out.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DBUtil.Close();
		}
	}
}
