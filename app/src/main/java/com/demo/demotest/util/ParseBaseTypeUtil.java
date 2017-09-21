package com.demo.demotest.util;

import android.text.TextUtils;
/**
 * 
 * 项目名称: FloodSurvey<br/>
 * 类名称: ParseBaseTypeUtil<br/>
 * 描述: 将字符串转换成基本数据类型
 *
 * @author:
 * @Date:
 */
public class ParseBaseTypeUtil {
	/**
	 * String 转换int
	 * 
	 * @param value
	 * @return
	 */
	public static int parseToInteger(String value) {
		return TextUtils.isEmpty(value.toString().trim()) ? 0 : Integer
				.valueOf(value.toString().trim());
	}

	/**
	 * String 转换double
	 * 
	 * @param value
	 * @return
	 */
	public static double parseToDouble(String value) {
		return TextUtils.isEmpty(value.toString().trim()) ? 0.00 : Double
				.valueOf(value.toString().trim());
	}
	/**
	 * String 转换float
	 * 
	 * @param value
	 * @return
	 */
	public static float parseToFloat(String value) {
		return TextUtils.isEmpty(value.toString().trim()) ? 0.00f: Float
				.valueOf(value.toString().trim());
	}
	/**
	 * String 转换long类型
	 * 
	 * @param value
	 * @return
	 */
	public static long parseToLong(String value) {
		return TextUtils.isEmpty(value.toString().trim()) ? 0 : Long
				.valueOf(value.toString().trim());
	}
}
