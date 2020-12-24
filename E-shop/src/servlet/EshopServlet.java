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
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet(name = "eshop_servlet", urlPatterns = { "/eshop_servlet" })
public class EshopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EshopServlet() {
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
		String gid = request.getParameter("gid");
		String quantity1 = request.getParameter("quantity");
		int quantity = Integer.parseInt(quantity1);
		HttpSession session = request.getSession();
		if(session.getAttribute("username")==null) {
			String a = URLEncoder.encode("请先登录！", "UTF-8");
			out.print("<script>alert(decodeURIComponent('" + a + "') );window.location.href='login.jsp'</script>");
			out.flush();
			out.close();
		}
		int uid = (int) session.getAttribute("uid");
		response.setContentType("text/html; charset=UTF-8");
		try {
			Connection conn = DBUtil.getConnection();
			Statement st = conn.createStatement();

			if (quantity == 0) {
				String a = URLEncoder.encode("该商品暂无库存", "UTF-8");
				out.print("<script>alert(decodeURIComponent('" + a + "') );window.location.href='e_shop.jsp'</script>");
				out.flush();
				out.close();
			} else {
				// 将goods表的gid库存减一.(若库存为0，eshop.jsp实现了不展示该商品)
				String sql = "update goods set quantity=" + (quantity - 1) + " where gid=" + gid;
				st.execute(sql);
				// 若有库存，查看shoppingcart表有没有uid和gid行
				String sql1 = "select * from shoppingcart where uid= " + uid + " and gid = " + gid;
				ResultSet rs = st.executeQuery(sql1);
				if (rs.next()) {
					int number = rs.getInt("number");
					// 若有该行，number加一
					String sql2 = "update shoppingcart set number=" + (number + 1) + " where gid=" + gid + " and uid ="
							+ uid;
					st.execute(sql2);
				} else {
					// 若没有该行，添加该行
					String sql3 = "insert into shoppingcart(uid,gid,number) values(" + uid + "," + gid + ",1)";
					st.execute(sql3);
				}
				String a = URLEncoder.encode("加入购物车成功!", "UTF-8");
				out.print("<script>alert(decodeURIComponent('" + a
						+ "') );window.location.href='e_shop.jsp'</script>");
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