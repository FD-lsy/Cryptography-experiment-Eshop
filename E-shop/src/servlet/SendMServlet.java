package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DBTool.DBUtil;
import encrypt.AES;
import encrypt.Key;
import encrypt.RSA;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.security.MessageDigest;

@WebServlet(name = "send_m_servlet", urlPatterns = { "/send_m_servlet" })
public class SendMServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public SendMServlet() {
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
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		int uid = 0;
		//用户未登录
		if(session.getAttribute("uid")==null) {
			String a = URLEncoder.encode("请先登录！", "UTF-8");
			out.print("<script>alert(decodeURIComponent('" + a
					+ "') );window.location.href='login.jsp'</script>");
			out.flush();
			out.close();
		}else {
			//从session读取uid
			uid = (int)session.getAttribute("uid");
		}
		String amount = request.getParameter("amount");
		String card = "5555 5555 5555 5555";
		String dealid = Key.genString();
		session.setAttribute("dealid", dealid);
		//RSA加密aes秘钥
		try {
			Connection conn = DBUtil.getConnection();
			Statement st = conn.createStatement();
			String sql = "select * from database.user where uid=" + uid;
			//发送订单信息前将uid,0,0,dealid存入completedOrder表(0表示订单未完成，-1表示订单已完成)
			//之后确认支付后将0,0改为-1,-1表示该uid的dealid已完成,再加上uid与dealid对应的gid和number
			String sql1 = "insert into database.completedOrder(uid,gid,number,dealid) values (" + uid + ",0,0,'"+ dealid + "')";
			st.execute(sql1);
			ResultSet rs = st.executeQuery(sql);
			//如果用户存在且购物车有商品：读取用户密码，发送订单信息
			if(rs.next()&&!amount.equals("0.0")&&uid!=0) {
				String password = rs.getString("password");
				//使用用户密码生成aes秘钥
				String aes_key = AES.genAESKey(password);
				//用aes秘钥加密金额和商城卡号
				String amount_c = AES.encryptNew(amount, aes_key);
				String card_c = AES.encryptNew(card, aes_key);
				//用md5算法为金额和卡号生成摘要并对摘要签名
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] hash_oi = md.digest((amount_c+card_c).getBytes());
				String hash = bytesToHexString(hash_oi);
				String signature = RSA.sign(hash,Key.getMyPrivateKey());
				//rsa算法加密订单号和aes秘钥
				String did = RSA.encrypt(dealid, Key.getBankPublicKey());
				String aes = RSA.encrypt(aes_key,Key.getBankPublicKey());
				response.sendRedirect(Key.getIP()+"deal/?amount=" + amount_c
						+ "&card=" + card_c + "&aes_key="+aes+"&deal_identify="+did
						+"&signature="+signature);
			}else {
				String a = URLEncoder.encode("您的购物车为空，请先购物！", "UTF-8");
				out.print("<script>alert(decodeURIComponent('" + a
						+ "') );window.location.href='e_shop.jsp'</script>");
				out.flush();
				out.close();
			}
//			response.sendRedirect("http://172.20.62.202:8001/authen/test/");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.Close();
		}
		//生成签名
	}
	/* *
	 * Convert byte[] to hex string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)
	 *来转换成16进制字符串。  
	 * @param src byte[] data  
	 * @return hex string  
	 */     

	public static String bytesToHexString(byte[] src){  
	    StringBuilder stringBuilder = new StringBuilder("");  
	    if (src == null || src.length <= 0) {  
	        return null;  
	    }  
	    for (int i = 0; i < src.length; i++) {  
	        int v = src[i] & 0xFF;  
	        String hv = Integer.toHexString(v);  
	        if (hv.length() < 2) {  
	            stringBuilder.append(0);  
	        }  
	        stringBuilder.append(hv);  
	    }  
	    return stringBuilder.toString();  
	} 
}