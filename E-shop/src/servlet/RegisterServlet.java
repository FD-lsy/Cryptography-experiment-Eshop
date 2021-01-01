package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
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
import encrypt.Key;
import encrypt.RSA;

@WebServlet(name = "register_servlet", urlPatterns = { "/register_servlet" })
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public RegisterServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			String username = RSA.decryptNormal(request.getParameter("enusername"), Key.getMyPrivateKey());
			String password = RSA.decryptNormal(request.getParameter("enpassword"), Key.getMyPrivateKey());
//			System.out.println(request.getParameter("username"));
//			System.out.println(request.getParameter("password"));
			Connection conn = DBUtil.getConnection();
			Statement st = conn.createStatement();
			String sql = "select * from database.user where username='" + username + "'";
			String sql1 = "insert into database.user(username,password,admin) values('" + username + "','" + password
					+ "',0)";
			ResultSet rs = st.executeQuery(sql);
			// 用户已存在
			if (rs.next()) {
				String a = URLEncoder.encode("用户名重复，请重新输入！", "UTF-8");
				out.print("<script>alert(decodeURIComponent('" + a + "') );window.location.href='register.jsp'</script>");
				// request.setAttribute("flag", "0");
				// request.getRequestDispatcher("/login.jsp").forward(request, response);
				out.flush();
				out.close();

			}
			// 用户尚未注册
			else {
				st.execute(sql1);
				String a = URLEncoder.encode("注册成功！即将进入登录页面，请重新登录", "UTF-8");
				out.print("<script>alert(decodeURIComponent('" + a + "') );window.location.href='login.jsp'</script>");
				// request.getRequestDispatcher("/login.jsp").forward(request, response);
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
