package com.demo.demotest.util;

import java.security.MessageDigest;

public class MD5Util {
	/**
	 * 获取md5加密后的信息
	 * @param content
	 * @return
	 */
	public static String getMD5(String content)
	{
		String md5="";
		try {
			MessageDigest digest=MessageDigest.getInstance("MD5");
			digest.update(content.getBytes());
			md5=getHashString(digest);
		} catch (Exception e) {
			LogUtil.i("md5", "MD5Util-getMD5方法出错了");
		}
		return md5;
	}
	/**
	 * 获取算法中的哈希字符串
	 * @param digest
	 * @return
	 */
	private static String getHashString(MessageDigest digest)
	{
		StringBuilder sb=new StringBuilder();
		for(byte b:digest.digest())
		{
			sb.append(Integer.toHexString((b>>4)&0xf));
			sb.append(Integer.toHexString(b&0xf));
		}
		return sb.toString();
	}
}
