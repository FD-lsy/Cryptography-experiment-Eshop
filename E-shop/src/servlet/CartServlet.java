package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DBTool.DBUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.Statement;

@WebServlet(name = "cart_servlet", urlPatterns = { "/cart_servlet" })
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CartServlet() {
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
		PrintWriter out = response.getWriter();
		String gid1 = request.getParameter("gid");
		int gid = Integer.parseInt(gid1);
		String number1 = request.getParameter("number");
		int number = Integer.parseInt(number1);
		String quantity1 = request.getParameter("quantity");
		int quantity = Integer.parseInt(quantity1);

		HttpSession session = request.getSession();
		if (session.getAttribute("username") == null) {
			String a = URLEncoder.encode("请先登录！", "UTF-8");
			out.print("<script>alert(decodeURIComponent('" + a + "') );window.location.href='login.jsp'</script>");
			out.flush();
			out.close();
		} else {
			int uid = (int) session.getAttribute("uid");
			response.setContentType("text/html; charset=UTF-8");
			try {
				Connection conn = DBUtil.getConnection();
				Statement st = conn.createStatement();
				// 将shoppingcart表的uid、gid行number减一
				String sql = "update shoppingcart set number=" + (number - 1) + " where gid=" + gid + " and uid="
						+ uid;
				st.execute(sql);

				// 将goods表的gid库存加一
				String sql1 = "update goods set quantity=" + (quantity + 1) + " where gid = " + gid;
				st.execute(sql1);
				String a = URLEncoder.encode("移出购物车成功!", "UTF-8");
				out.print("<script>alert(decodeURIComponent('" + a + "') );window.location.href='shopping_cart.jsp'</script>");
				out.flush();
				out.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				DBUtil.Close();
			}
		}
	}
}