package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DBTool.DBUtil;
import encrypt.Key;
import encrypt.RSA;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;



@WebServlet(name = "login_servlet", urlPatterns = { "/login_servlet" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		String enusername = request.getParameter("enusername");
		String enpassword = request.getParameter("enpassword");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		try {
			String password = RSA.decryptNormal(enpassword, Key.getMyPrivateKey());
			String username = RSA.decryptNormal(enusername, Key.getMyPrivateKey());
			session.setAttribute("username",username);
//			System.out.println(request.getParameter("username"));
//			System.out.println(request.getParameter("password"));
			Connection conn = DBUtil.getConnection();
			Statement st = conn.createStatement();
			String sql = "select * from database.user where username='" + username + "' and password='" + password
					+ "'";
			ResultSet rs = st.executeQuery(sql);
			// 用户存在且为普通用户
			if (rs.next()) {
				session.setAttribute("uid",rs.getInt("uid"));
				if (rs.getInt("admin") == 0) {
					// 重定向
					request.setAttribute("username", username);
					request.getRequestDispatcher("/e_shop.jsp").forward(request, response);
				}
				// 用户存在且为管理员
				else if (rs.getInt("admin") == 1) {
					String a = URLEncoder.encode("您将进入管理员页面", "UTF-8");
					out.print("<script>alert(decodeURIComponent('" + a
							+ "') );window.location.href='admin.jsp'</script>");
					out.flush();
					out.close();
				}
			}
			// 用户不存在
			else {
				String a = URLEncoder.encode("用户名或密码错误，请重新输入！", "UTF-8");
				out.print("<script>alert(decodeURIComponent('" + a + "') );window.location.href='login.jsp'</script>");
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