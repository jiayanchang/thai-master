package com.magic.thai.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5CryptoUtils {

	public static String create(String passwordSource) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		md.update(passwordSource.getBytes()); // MD5加密算法只是对字符数组而不是字符串进行加密计算，得到要加密的对象
		byte[] bs = md.digest(); // 进行加密运算并返回字符数组
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bs.length; i++) { // 字节数组转换成十六进制字符串，形成最终的密文
			int v = bs[i] & 0xff;
			if (v < 16) {
				sb.append(0);
			} else {
				sb.append(Integer.toHexString(v));
			}
		}
		return sb.toString();
	}

	public static String token(String plainText) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(plainText.getBytes());
		byte b[] = md.digest();
		int i;
		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}
		return buf.toString().substring(8, 24);
	}

}
