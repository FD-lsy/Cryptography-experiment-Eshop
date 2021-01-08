package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

@WebServlet(name = "success_servlet", urlPatterns = { "/success_servlet" })
public class SuccessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger=Logger.getLogger(SuccessServlet.class);
	public SuccessServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)Completed orders
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		request.getRequestDispatcher("/e_shop.jsp").forward(request, response);
		PrintWriter out = response.getWriter();
//		System.out.println(dealidReturn);
		try {
			String dealidReturn = request.getParameter("send_deal_identify");
			String dealid = RSA.decrypt(dealidReturn, Key.getMyPrivateKey());
			List<Integer> gidList = new ArrayList<Integer>();
			List<Integer> numList = new ArrayList<Integer>();
			int gid = -1;
			int num = -1;
			int uid = 0;
			int id = -1;
			try {
				Connection conn = DBUtil.getConnection();
				Statement st = conn.createStatement();
				String sql = "select * from database.completedOrder where dealid='" + dealid +"'";
				ResultSet rs = st.executeQuery(sql);
				if (rs.next()) {
					gid = rs.getInt("gid");
					num = rs.getInt("number");
					uid = rs.getInt("uid");
					id = rs.getInt("id");
				} else {
					// 返回的订单号错误（不是未完成订单号也不是已完成订单号）
					String a = URLEncoder.encode("确认支付完成的订单号错误!", "UTF-8");
					out.print("<script>alert(decodeURIComponent('" + a
							+ "') );window.location.href='login.jsp'</script>");
					out.flush();
					out.close();
				}
				if (gid == 0 && num == 0) {
					String sql1 = "select * from database.shoppingcart where uid=" + uid;
					rs = st.executeQuery(sql1);
					while (rs.next()) {
						if(rs.getInt("number")!=0) {
							gidList.add(rs.getInt("gid"));
							numList.add(rs.getInt("number"));
						}
					}
					String sql2 = "delete from database.shoppingcart where uid=" + uid;
					st.execute(sql2);
					for (int i = 0; i < gidList.size(); i++) {
						String sql3 = "insert into database.completedOrder(uid,gid,number,dealid) values (" + uid + ","
								+ gidList.get(i) + "," + numList.get(i) + ",'" + dealid + "')";
						st.execute(sql3);
					}
					String sql4 = "update database.completedOrder set gid=-1,number=-1 where id=" + id;
					st.execute(sql4);
					
					
					String sql5 = "select * from database.user where uid=" + uid;
					rs = st.executeQuery(sql5);
					if (rs.next()) {
						String username = rs.getString("username");
						HttpSession session = request.getSession();
						session.setAttribute("username", username);
						session.setAttribute("uid", uid);
					}
					logger.info("订单"+dealid+"支付成功，uid为"+uid+"的用户的购物车已清空，该用户已购买商品列表已更新。");
					String a = URLEncoder.encode("订单支付成功，购物车已清空！", "UTF-8");
					out.print("<script>alert(decodeURIComponent('" + a
							+ "') );window.location.href='e_shop.jsp'</script>'</script>");
					out.flush();
					out.close();

				} else {
					// 返回的dealid对应gid或num为0，订单已完成，可能为重放攻击，不处理即可
				}

			} finally {
				DBUtil.Close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
