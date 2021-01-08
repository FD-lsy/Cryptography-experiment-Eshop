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

@WebServlet(name = "goods_modify_servlet", urlPatterns = { "/goods_modify_servlet" })
public class GoodsModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger=Logger.getLogger(GoodsModifyServlet.class);
	public GoodsModifyServlet() {
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
			String orderSession = (String) session.getAttribute("orderModify");
			String order = RSA.decryptNormal(request.getParameter("enorder"), Key.getMyPrivateKey());
			String name = new String(request.getParameter("enname").getBytes("iso-8859-1"), "utf-8");
			String priceString = RSA.decryptNormal(request.getParameter("enprice"), Key.getMyPrivateKey());
			float price = Float.parseFloat(priceString);
			String quantityS = RSA.decryptNormal(request.getParameter("enquantity"), Key.getMyPrivateKey());
			int quantity = Integer.parseInt(quantityS);
			String gidS = RSA.decryptNormal(request.getParameter("engid"), Key.getMyPrivateKey());
			int gid = Integer.parseInt(gidS);

			Connection conn = DBUtil.getConnection();
			Statement st = conn.createStatement();
			if (orderSession.equals(order)) {
				String sql = "update goods set name='" + name + "',price=" + price + ",quantity=" + quantity
						+ " where gid=" + gid;
				st.execute(sql);
				logger.info("将gid为"+gid+"的商品信息修改为：(name:" + name + ",price:" + price + ",quantity:"+quantity+")");
				String a = URLEncoder.encode("修改商品信息成功!", "UTF-8");
				out.print("<script>alert(decodeURIComponent('" + a + "') );window.close();</script>");
				out.flush();
				out.close();
			} else {
				// 操作序列号
				String a = URLEncoder.encode("修改商品信息商品失败!", "UTF-8");
				out.print("<script>alert(decodeURIComponent('" + a + "') );window.close();</script>");
				out.flush();
				out.close();
			}
		} catch (

		Exception ex) {
			ex.printStackTrace();
		} finally {
			DBUtil.Close();
		}
	}
}
