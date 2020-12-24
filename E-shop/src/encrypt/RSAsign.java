package encrypt;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAsign {

	public static final String KEY_ALGORITHM = "RSA";
	public static final String SIGNATURE_ALGORITHM = "SHA256WithRSA";
	
	public static void main(String[] args) throws Exception {
		String s = "hello world";
		String pubKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALyJy3rlD9EtWqVBzSIYxRRuFWRVn3juht2nupDCBSsWi7uKaRu3W0gn5y6aCacArtCkrf0EehwYRm0A4iHf8rkCAwEAAQ==";
		// PKCS8格式私钥(可由PKCS1格式转换)
		String priKey = "MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAvInLeuUP0S1apUHNIhjFFG4VZFWfeO6G3ae6kMIFKxaLu4ppG7dbSCfnLpoJpwCu0KSt/QR6HBhGbQDiId/yuQIDAQABAkEAqm/y15UtOE7Ey/HxLCqyNqbRhdN1h5AxsT0IhgYvP+PhWGc3hRElMwNCdiNaJBh04R1iK6wmKoi3DSjkdU6IAQIhAPRL9khAdPMxjy5tpswNWeaDjNJrlUKEnItQUkoHqve5AiEAxZIDz235HcUgLg9ApYK4spOpzLDGCCgfO3FxmrUEUwECIEaLjQIOQvdbT1p75Ze1H0nWoRq+YGrF+qKsPicMkc1ZAiARlNTR+K9afthGQQU3tVJKUemiVXjJ8QgWehnp8oHYAQIhANsC2fEVjWv94Oy2c8I9qhuX+yfNtvZ2m+Kmf2o4JFrR";
		String sign = "cPz4BuUiKXDDBXjTx5VcMFgDFdCKVfn50Idv7pYhmiivrmx94zk0Fpk6IbKjReiqaNfRhEqGCIVpdFNiKLVKfA==";
		String m = "hello world";
		String after_sign = sign(s.getBytes(), priKey);
		System.out.println(after_sign);
		boolean ok = verify(m.getBytes(), pubKey, sign);
		System.out.println(ok);
	}
 
	public static String sign(byte[] data, String privateKey) throws Exception {
		// 解密由 base64 编码的私钥
		final Base64.Encoder encoder = Base64.getEncoder();
		final Base64.Decoder decoder = Base64.getDecoder();
		byte[] keyBytes = decoder.decode(privateKey);
	
		// 构造 PKCS8EncodedKeySpec 对象
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
	
		// KEY_ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
	
		// 取私钥对象
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
	
		// 用私钥对信息生成数字签名
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(priKey);
		signature.update(data);
 
		return encoder.encodeToString(signature.sign());
	}
 
	public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
 
		// 解码由 base64 编码的公钥
		final Base64.Decoder decoder = Base64.getDecoder();
		final byte[] keyBytes = publicKey.getBytes("UTF-8");
 
		// 构造 X509EncodedKeySpec 对象
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoder.decode(keyBytes));
 
		// KEY_ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
 
		// 取公钥匙对象
		PublicKey pubKey = keyFactory.generatePublic(keySpec);
 
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(pubKey);
		signature.update(data);
 
		// 验证签名是否正常
		return signature.verify(decoder.decode(sign));
	}
 
	

}
