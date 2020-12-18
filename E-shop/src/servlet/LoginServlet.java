package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import DBTool.DBUtil;

@WebServlet("/login_servlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			Connection conn = DBUtil.getConnection();
			Statement st = conn.createStatement();
			String sql = "select * from database.user where username='" + username + "' and password='" + password
					+ "'";
			ResultSet rs = st.executeQuery(sql);
			//用户存在且为普通用户
			if (rs.next() && rs.getInt("admin") == 0) {
				// 重定向和传参
				request.setAttribute("username", username);
				request.getRequestDispatcher("/eshop/e_shop.jsp").forward(request, response);
			} 
			//用户存在且为管理员
			else if (rs.next() && rs.getInt("admin") == 1) {
				out.print("<input type=\"button\" onclick=\"logout()\" value=\"注销\"/>");					
				out.close();
			} 
			//用户不存在
			else {
				request.setAttribute("error", "用户名或密码错误!");
				request.getRequestDispatcher("/eshop/login.jsp").forward(request, response);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DBUtil.Close();
		}
	}
}