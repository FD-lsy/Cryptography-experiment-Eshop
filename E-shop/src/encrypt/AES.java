package encrypt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {

	/*
	 * 生成AES秘钥
	 */
	public static String genAESKey(String password) throws NoSuchAlgorithmException {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");// 创建AES的Key生产者
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		random.setSeed(password.getBytes());
		kgen.init(128, random);// 利用用户密码作为随机数初始化出128位的key生产者
		// 加密没关系，SecureRandom是生成安全随机数序列，password.getBytes()是种子，只要种子相同，序列就一样，所以解密只要有password就行

		SecretKey AESKey = kgen.generateKey();// 根据用户密码，生成一个密钥
//		byte[] enCodeFormat = AESKey.getEncoded();// 返回基本编码格式的密钥，如果此密钥不支持编码，则返回null
		// String AESKeyString = parseByte2HexStr(enCodeFormat);// base64编码后的秘钥
		// return AESKeyString;
		String AESKeyB64 = Base64.getEncoder().encodeToString(AESKey.getEncoded());
		return AESKeyB64;
	}

	// 加密
	public static String encryptNew(String content,String AESKeyB64) throws Exception {
		byte[] AESKey = Base64.getDecoder().decode(AESKeyB64);
//		byte[] raw = KEY.getBytes("utf-8");
		SecretKeySpec skeySpec = new SecretKeySpec(AESKey, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// "算法/模式/补码方式"
		// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
		IvParameterSpec ips = new IvParameterSpec(AESKey);
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ips);
		byte[] encrypted = cipher.doFinal(content.getBytes());
		return Base64.getUrlEncoder().encodeToString(encrypted);
	}

	/*
	 * AES加密
	 */
	public static String encrypt(String message, String AESKeyB64) {
		try {
//			byte[] enKey = parseHexStr2Byte(AESKeyString);

			byte[] AESKey = Base64.getDecoder().decode(AESKeyB64);
			SecretKeySpec aeskey = new SecretKeySpec(AESKey, "AES");// 转换为AES专用密钥
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.ENCRYPT_MODE, aeskey);// 初始化为加密模式的密码器
			String ciphertext = Base64.getEncoder().encodeToString(cipher.doFinal(message.getBytes("UTF-8")));
			return ciphertext;
//			return parseByte2HexStr(result);

		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * AES解密
	 */
	public static String decrypt(String ciphertext, String AESKeyB64) {
		try {
//			byte[] encodec = parseHexStr2Byte(ciphertext);
//			byte[] enKey = parseHexStr2Byte(AESKeyString);
			byte[] inputByte = Base64.getDecoder().decode(ciphertext);
			byte[] AESKey = Base64.getDecoder().decode(AESKeyB64);
			SecretKeySpec aesKey = new SecretKeySpec(AESKey, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, aesKey);// 初始化为解密模式的密码器
			String message = new String(cipher.doFinal(inputByte));
//			return parseByte2HexStr(message); // 明文
			return message;

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		String content = "AES测试ABCD~!@#$";
		String password = "123456";
		System.out.println("加密之前：" + content);
		// 生成aesKey
		String aesKey = genAESKey(password);
		System.out.println("秘钥：" + aesKey);
		// 加密
		String encrypt = encryptNew(content, "a7SDfrdDKRBe5FaN2n3Gfg==");
		System.out.println("加密后的内容：" + encrypt);

		// 解密
		String decrypt = decrypt(encrypt, aesKey);
		System.out.println("解密后的内容：" + decrypt);
	}
}