package nether.common;

import java.security.MessageDigest;

public class MD5Util {
//	private static final String slat = "$$%^NEthEr##MS";
	
	/**
	 * md5加密
	 * @param data
	 * @return hexString
	 */
	public static String md5(String data) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] res = md.digest(data.getBytes("utf-8"));
			return toHexString(res);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return new String();
	}
	
	/**
     * 将加密后的字节数组，转换成16进制的字符串
     * @param md5
     * @return
     */
    private static String toHexString(byte[] md5) {
        StringBuilder sb = new StringBuilder();
//        System.out.println("md5.length: " + md5.length);
        for (byte b : md5) {
            sb.append(Integer.toHexString(b & 0xff));
        }
        return sb.toString();
    }
}
