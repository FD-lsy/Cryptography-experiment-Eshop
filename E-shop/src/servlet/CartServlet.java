package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import DBTool.DBUtil;
import encrypt.Key;
import encrypt.RSA;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.Statement;

@WebServlet(name = "cart_servlet", urlPatterns = { "/cart_servlet" })
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger=Logger.getLogger(CartServlet.class);
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
		HttpSession session = request.getSession();
		String orderSession = (String) session.getAttribute("order");
		int uid = (int) session.getAttribute("uid");
		try {
			//解密得到序列号order、商品的gid、库存number、购物车中该商品的数量quantity
			String order = RSA.decrypt(request.getParameter("enorder"), Key.getMyPrivateKey());
			String gid1 = RSA.decrypt(request.getParameter("engid"), Key.getMyPrivateKey());
			int gid = Integer.parseInt(gid1);
			String number1 = RSA.decrypt(request.getParameter("ennumber"), Key.getMyPrivateKey());
			int number = Integer.parseInt(number1);
			String quantity1 = RSA.decrypt(request.getParameter("enquantity"), Key.getMyPrivateKey());
			int quantity = Integer.parseInt(quantity1);
			if (session.getAttribute("username") == null) {
				String a = URLEncoder.encode("请先登录！", "UTF-8");
				out.print("<script>alert(decodeURIComponent('" + a + "') );window.location.href='login.jsp'</script>");
				out.flush();
				out.close();
			}
			//序列号和session中序列号相同，其实从session中获得uid和username已经可以放重放攻击，增加序列号多一层保障
			else if (order.equals(orderSession)) {
				if (number == 0) {
					//不会出现此情况，以防万一还是写上
					String a = URLEncoder.encode("购物车暂无该商品！", "UTF-8");
					out.print("<script>alert(decodeURIComponent('" + a
							+ "') );window.location.href='shopping_cart.jsp'</script>");
					out.flush();
					out.close();
				} else {
					response.setContentType("text/html; charset=UTF-8");
					Connection conn = DBUtil.getConnection();
					Statement st = conn.createStatement();
					// 将shoppingcart表的uid、gid行number减一
					String sql = "update shoppingcart set number=" + (number - 1) + " where gid=" + gid + " and uid="
							+ uid;
					st.execute(sql);

					// 将goods表的gid库存加一
					String sql1 = "update goods set quantity=" + (quantity + 1) + " where gid = " + gid;
					st.execute(sql1);
					logger.info("将uid为"+uid+"的用户购物车中gid为"+gid+"的商品数量减一，该商品库存加一。");
					String a = URLEncoder.encode("移出购物车成功!", "UTF-8");
					out.print("<script>alert(decodeURIComponent('" + a
							+ "') );window.location.href='shopping_cart.jsp'</script>");
					out.flush();
					out.close();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DBUtil.Close();
		}
	}
}