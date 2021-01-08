package encrypt;

import java.util.Base64;
import javax.crypto.Cipher;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSA {

	/**
	 * 用于封装随机产生的公钥与私钥
	 */
	private static Map<Integer, String> keyMap = new HashMap<Integer, String>();

	/**
	 * 随机生成密钥对
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	public static void genKeyPair() throws NoSuchAlgorithmException {
		// KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		// 初始化密钥对生成器，密钥大小为96-1024位
		keyPairGen.initialize(1024, new SecureRandom());
		// 生成一个密钥对，保存在keyPair中
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate(); // 得到私钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic(); // 得到公钥
		String publicKeyString = new String(Base64.getEncoder().encodeToString(publicKey.getEncoded()));
		// 得到私钥字符串
		String privateKeyString = new String(Base64.getEncoder().encodeToString((privateKey.getEncoded())));
		// 将公钥和私钥保存到Map
		keyMap.put(0, publicKeyString); // 0表示公钥
		keyMap.put(1, privateKeyString); // 1表示私钥
	}

	/**
	 * RSA公钥加密
	 * 
	 * @param message   消息（需要加密的字符串）
	 * @param publicKey 公钥
	 * @return ciphertext 密文
	 * @throws Exception
	 */
	public static String encrypt(String message, String publicKey) throws Exception {
		// base64编码的公钥
		byte[] decoded = Base64.getDecoder().decode(publicKey);
		RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA")
				.generatePublic(new X509EncodedKeySpec(decoded));
		// RSA加密
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		String ciphertext = Base64.getUrlEncoder().encodeToString(cipher.doFinal(message.getBytes("UTF-8")));
		return ciphertext;
	}

	/**
	 * RSA私钥解密
	 * 
	 * @param ciphertext 密文
	 * @param privateKey 私钥
	 * @return message 明文
	 * @throws Exception
	 */
	public static String decrypt(String ciphertext, String privateKey) throws Exception {
		// 64位解码加密后的字符串
		byte[] inputByte = Base64.getUrlDecoder().decode(ciphertext);
		// base64编码的私钥
		byte[] decoded = Base64.getDecoder().decode(privateKey);
		RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA")
				.generatePrivate(new PKCS8EncodedKeySpec(decoded));
		// RSA解密
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, priKey);
		String message = new String(cipher.doFinal(inputByte));
		return message;
	}

	public static String decryptNormal(String ciphertext, String privateKey) throws Exception {
		// 64位解码加密后的字符串
		byte[] inputByte = Base64.getDecoder().decode(ciphertext);
		// base64编码的私钥
		byte[] decoded = Base64.getDecoder().decode(privateKey);
		RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA")
				.generatePrivate(new PKCS8EncodedKeySpec(decoded));
		// RSA解密
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, priKey);
		String message = new String(cipher.doFinal(inputByte));
		return message;
	}
	
	/**
	 * RSA私钥签名
	 * 
	 * @param message    消息
	 * @param privateKey 私钥
	 * @return 签名
	 * @throws Exception
	 */
	public static String sign(String message, String privatekey) throws Exception {
		byte[] decoded = Base64.getDecoder().decode(privatekey);
		PrivateKey pri_Key = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
		Signature sign = Signature.getInstance("SHA256WithRSA");
		sign.initSign(pri_Key);
		sign.update(message.getBytes());
		byte[] signInfo = sign.sign();

		return Base64.getUrlEncoder().encodeToString(signInfo);

	}

	/**
	 * RSA公钥验签
	 * 
	 * @param message   消息
	 * @param publickey 公钥
	 * @param signature 签名
	 * @return true or false
	 * @throws Exception
	 */
	public static boolean verify(String message, String signature, String publickey) throws Exception {
		// base64编码的公钥
		byte[] decoded = Base64.getDecoder().decode(publickey);
		PublicKey pubKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
		Signature sign = Signature.getInstance("SHA256WithRSA");
		sign.initVerify(pubKey);
		sign.update(message.getBytes());
		byte[] inputByte = Base64.getUrlDecoder().decode(signature);
		return sign.verify(inputByte);
	}

	public static boolean verifyNormal(String message, String signature, String publickey) throws Exception {
		// base64编码的公钥
		byte[] decoded = Base64.getDecoder().decode(publickey);
		PublicKey pubKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
		Signature sign = Signature.getInstance("SHA256WithRSA");
		sign.initVerify(pubKey);
		sign.update(message.getBytes());
		byte[] inputByte = Base64.getDecoder().decode(signature);
		return sign.verify(inputByte);
	}
	
	public static void main(String[] args) throws Exception {
//		long temp = System.currentTimeMillis();
		String m = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDf7cQzFGr"
				+ "PgBkg+D6M1U7XdddN gIlFiATQFcwvi24ZDcSu2ynFzuuoJU"
				+ "RPIuqM8XNwATFIZ/pU4ZmouBUgzFwshUJg UFQArscNQFD1E"
				+ "KmHuKY0pmi2dFHhxt51YOrSu1ozBOI1u/NM4OYX4KBzhVc6o"
				+ "4pS iej1zqBLr3iNaDE4kwIDAQAB";
		String s = "CPA2yD+OVY6dyhUaHxGDEkb1senvHS0+9WhDPbSQfARc7Mi"
				+ "QwEr/HUrHIwBWPVv+5VbPfnLQTezj7VowtWPvjuk57G2B8OJ"
				+ "M9FFE+RmZCSUCg5PsCRemj9oNz6A5qrX6BCKjLWolZAjOVVl"
				+ "s4eqNJk3iBKNfCr3ckyVthaxOoKU=";
		String CApk = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDCeNFi"
				+ "COKGGC+fA7vvWT998Lxy7XI+L8AWIzmyXDQP4AlWTzCmcmDy"
				+ "X+WWC7v05i/Uq9x2FwCCYVgARZqm5NTsAeWIFib6cz1V4V3H"
				+ "oI8dYfi7KR67n9qCHx5ah/D1HCIldDaOXSPlCs9TkDBlm1Ai"
				+ "Zv9hISsG9xIPiS+M7sVjYwIDAQAB";
//		System.out.println(encrypt("",Key.getMyPublicKey()));
		System.out.println(verifyNormal(m,s,CApk));
		// 生成公钥和私钥
//		genKeyPair();
//		keyMap.put(0, Key.getBankPublicKey()); // 0表示公钥
//		keyMap.put(1, Key.getMyPrivateKey());
		// 加密字符串
//		System.out.println("公钥:" + keyMap.get(0));
//		System.out.println("私钥:" + keyMap.get(1));
//		System.out.println("生成密钥消耗时间:" + (System.currentTimeMillis() - temp) / 1000.0 + "秒");
//		String message = "RSA测试ABCD~!@#$";
//		System.out.println("原文:" + message);
//		temp = System.currentTimeMillis();
//		String messageEn = encrypt(message, keyMap.get(0));
//		System.out.println("密文:" + messageEn);
//		System.out.println("加密消耗时间:" + (System.currentTimeMillis() - temp) / 1000.0 + "秒");
//		temp = System.currentTimeMillis();
//      String messageDe = decrypt(messageEn, keyMap.get(1));
//      System.out.println("解密:" + messageDe);
//      System.out.println("解密消耗时间:" + (System.currentTimeMillis() - temp) / 1000.0 + "秒");
//
//		String messageSign = sign(message, keyMap.get(1));
//		System.out.println("签名:" + messageSign);
//		System.out.println("签名消耗时间:" + (System.currentTimeMillis() - temp) / 1000.0 + "秒");
//      boolean messageVerify = verify(message,messageSign,keyMap.get(0));
//		System.out.println("验签:" + messageVerify);
//		System.out.println("验签消耗时间:" + (System.currentTimeMillis() - temp) / 1000.0 + "秒");
	}

}
