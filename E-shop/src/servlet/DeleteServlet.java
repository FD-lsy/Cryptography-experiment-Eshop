package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.Statement;
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

@WebServlet(name = "delete_servlet", urlPatterns = { "/delete_servlet" })
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger=Logger.getLogger(DeleteServlet.class);
	public DeleteServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		doPost(request,response);
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
		int orderS = (int) session.getAttribute("orderDelete");
		try {
			String enorderString = request.getParameter("enorder");
			String orderString = RSA.decryptNormal(enorderString, Key.getMyPrivateKey());
			int order = Integer.parseInt(orderString);
			String engidString = request.getParameter("engid");
			String gidString = RSA.decryptNormal(engidString, Key.getMyPrivateKey());
			int gid = Integer.parseInt(gidString);
			if (order==orderS) {
				Connection conn = DBUtil.getConnection();
				Statement st = conn.createStatement();
				String sql = "delete from database.goods where gid=" + gid;
				st.execute(sql);
				logger.info("管理员将gid为"+gid+"的商品从数据库移除。");
				String a = URLEncoder.encode("删除成功！", "UTF-8");
				out.print("<script>alert(decodeURIComponent('" + a + "') );window.location.href='admin.jsp'</script>");
				out.flush();
				out.close();
			} else {
				// 操作序列号order和session中不同，可能为重放攻击
				String a = URLEncoder.encode("删除失败!", "UTF-8");
				out.print("<script>alert(decodeURIComponent('" + a
						+ "') );window.location.href='admin.jsp'</script>");
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