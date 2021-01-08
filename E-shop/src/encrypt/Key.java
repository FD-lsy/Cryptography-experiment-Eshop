package encrypt;

import java.util.Random;

public class Key {
	private static String myPrivateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAOy2JgqJ4/GwQk6I"
			+ "uyQWTHBwY8JJQgcThyY7RZbMLrTNFBgSRTFFq7BChVt77rCJWBLIOffK97N90yNr"
			+ "zBM9pSw+HeIZgk9YDFmUfl+O/kbTavvctsUkuC95QiNKDKQ3nSya3pLj5/NeDjWe"
			+ "unlVqyD231I3OONTtdNhIb7vkS4nAgMBAAECgYAQCT+m1h7xotm9x/M5p3hn46Kh"
			+ "KQ+xOrLyCvDqHYy1JfGMYex7Fo0M5KzJJ6piHk6E/eM4yht2zE7kpusNNfoTAF0j"
			+ "4/nqt1quf9lRcjkj5Uo6s+fdYJ9941ndPl/Dr6rbDesXkp7YOIEHv7xdvv9l5VcZ"
			+ "fDY+wclP/9eZfqKlUQJBAO99svCSQFr+CtYz8rQxbZaFssjh0bhSeAs82y0h1ieD"
			+ "zEFyO/yYlanDVM6sapGWz1Mm76VIf34LxFp5QAs7xzECQQD9B2Zcy2yBt8JPogDJ"
			+ "xqSvWlDryH/Oe/aEyB5OMlkB/UEhQ4HZDbhJdpRPxj5Qdo7jrMRcxeMKPIbpPIIb"
			+ "eiTXAkEA2wQbRsKZNB2rj2UGih/B1qwCao+JmYVh3vUSg92z2YNAooFztndcN1o5"
			+ "DqDC/M3sYGD1PDRm08CzGwJTvXI3oQJBAOdB7iJ9oexlAOk7TbEdJsNFrpPxKi6U"
			+ "SdlN9bTOe19AEljOlEzHJ1mtURwgOi/cS/h2nPmVu05Nx5hAP0HRvv0CQQCca80f"
			+ "zCYrg2/SdtxM+BmBm/8RVbWaM8YbdB9zuM1xhwFNHDqFRJPp0Jd40I9U9DBHgliA"
			+ "ojQpqmiyiphs59hy";
	private static String myPublicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDstiYKiePxsEJOiLskFkxwcGPC"
			+ "SUIHE4cmO0WWzC60zRQYEkUxRauwQoVbe+6wiVgSyDn3yvezfdMja8wTPaUsPh3i"
			+ "GYJPWAxZlH5fjv5G02r73LbFJLgveUIjSgykN50smt6S4+fzXg41nrp5Vasg9t9S"
			+ "NzjjU7XTYSG+75EuJwIDAQAB";
	private static String bankPublicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDf7cQzFGrPgBkg+D6M1U7XdddN"
			+ "gIlFiATQFcwvi24ZDcSu2ynFzuuoJURPIuqM8XNwATFIZ/pU4ZmouBUgzFwshUJg"
			+ "UFQArscNQFD1EKmHuKY0pmi2dFHhxt51YOrSu1ozBOI1u/NM4OYX4KBzhVc6o4pS"
			+ "iej1zqBLr3iNaDE4kwIDAQAB";
	
	private static String ip = "http://172.20.107.123" + ":8001/authen/";
			
	private static final String[] GENERATE_SOURCE = new String[]{"0", "1", "2", "3", "4", "5", "6", "7",
            "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};
	private static final int STR_LEN = GENERATE_SOURCE.length;
	
	public static String getMyPrivateKey() {
		return myPrivateKey;
	}
	
	public static String getMyPublicKey() {
		return myPublicKey;
	}
	
	public static String getBankPublicKey() {
		return bankPublicKey;
	}
	
	public static String getIP() {
		return ip;
	}
	
	/**
     * 生成随机字符串，generateByRandom的简化版
     * @param count 随机字符串的长度
     * @param source 源字符集
     * @return
     */
	public static String genString() {
		StringBuilder sb = new StringBuilder(8);
		for (int i = 0; i < 8; i++) {
			final int index = new Random().nextInt(STR_LEN);
			sb.append(GENERATE_SOURCE[index]);
		}
		return sb.toString();
	}
	
	public static int genInt(){
        int max=10000,min=1;
        return (int)(Math.random()*(max-min)+min);
    }
	
	public static void main(String[] args) {
		System.out.println(genString());
	}
}
