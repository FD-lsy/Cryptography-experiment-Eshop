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

@WebServlet(name = "goodsadd_servlet", urlPatterns = { "/goodsadd_servlet" })
public class GoodsAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GoodsAddServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		if (session.getAttribute("username") == null) {
			String a = URLEncoder.encode("请先登录！", "UTF-8");
			out.print("<script>alert(decodeURIComponent('" + a + "') );window.location.href='login.jsp'</script>");
			out.flush();
			out.close();
		}
		try {
			String orderS = (String) session.getAttribute("order");
			String order = RSA.decryptNormal(request.getParameter("enorder"), Key.getMyPrivateKey());
			
//			String name = RSA.decryptNormal(request.getParameter("enname"), Key.getMyPrivateKey());
			String name = new String(request.getParameter("enname").getBytes("iso-8859-1"), "utf-8");
			String priceString = RSA.decryptNormal(request.getParameter("enprice"), Key.getMyPrivateKey());
			float price = Float.parseFloat(priceString);
			String quantity1 = RSA.decryptNormal(request.getParameter("enquantity"), Key.getMyPrivateKey());
			int quantity = Integer.parseInt(quantity1);
			String link = "images\\\\"+new String(request.getParameter("file").getBytes("iso-8859-1"), "utf-8");
//			System.out.println(link);
			
			Connection conn = DBUtil.getConnection();
			Statement st = conn.createStatement();
			if (orderS.equals(order)) {
				String sql = "select * from goods where name= '" + name + "'";
				ResultSet rs = st.executeQuery(sql);
				if (rs.next()) {
					//添加的商品名称重复
				} else {
					// 若没有该商品，添加该商品
					String sql1 = "insert into goods (name,price,quantity,link) "
							+ "values ('" + name + "'," + price + ","+quantity+",'"+link+"')";
					st.execute(sql1);
					String a = URLEncoder.encode("添加商品成功!", "UTF-8");
					out.print("<script>alert(decodeURIComponent('" + a
							+ "') );window.close();</script>");
					out.flush();
					out.close();
				}
			} else {
				//操作序列号
				String a = URLEncoder.encode("添加商品失败!", "UTF-8");
				out.print("<script>alert(decodeURIComponent('" + a
						+ "') );window.close();</script>");
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
