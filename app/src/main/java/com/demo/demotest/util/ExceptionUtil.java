package com.demo.demotest.util;

/**
 * 
 * 项目名称: PiePlus<br/>
 * 类名称: ExceptionUtil<br/>
 * 描述:
 * 
 * @author:yefx
 * @Date:2015-10-12下午2:44:10
 */
public class ExceptionUtil {

	/** 是否抛出异常 */
	private static boolean flag = true;

	/**
	 * 
	 * 功能:处理运行时异常
	 * 
	 * @param e
	 * @author: yefx
	 * @date:2015-10-12下午2:44:19
	 */
	public static void handleRuntimeException(Exception e) {
		if (flag) {
			throw new RuntimeException(e);
		}
	}
}
