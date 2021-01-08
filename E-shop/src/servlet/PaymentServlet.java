package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import encrypt.Key;
import encrypt.RSA;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

@WebServlet(name = "payment_servlet", urlPatterns = { "/payment_servlet" })
public class PaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger=Logger.getLogger(GoodsModifyServlet.class);
	public PaymentServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		doPost(request,response);
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String payId = request.getParameter("send_pay_id");
		String signature = request.getParameter("send_signature");
		if (payId == null || signature == null) {
			String a = URLEncoder.encode("跳转支付页面失败！", "UTF-8");
			out.print("<script>alert(decodeURIComponent('" + a
					+ "') );window.location.href='shopping_cart.jsp'</script>");
			out.flush();
			out.close();
		} else {
			// 用商城公钥解密得到账单号pid
			String pid;
			try {
				pid = RSA.decrypt(payId, Key.getMyPrivateKey());
//			System.out.println(pid);
//			System.out.println(payId);
//			System.out.println(signature);
				boolean sign = RSA.verify(payId, signature, Key.getBankPublicKey());
				if (sign) {
					logger.info("获得payid并对银行验签成功，跳转支付页面。");
					response.sendRedirect(Key.getIP()+"pay/" + pid + "/");
				} else {
					String a = URLEncoder.encode("跳转支付页面失败！请重新登录", "UTF-8");
					out.print("<script>alert(decodeURIComponent('" + a
							+ "') );window.location.href='shopping_cart.jsp'</script>");
					out.flush();
					out.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}