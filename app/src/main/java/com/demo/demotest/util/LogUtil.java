package com.demo.demotest.util;

import android.util.Log;

/**
 * 描述:<br/>Log工具类，可实现自己开关Log日志输出;<br/>
 *     特别地，当LogUtil的开关关闭时，所有含有两个参数的日志输出方法均失效，<br/>
 *     而此时含有三个参数的日志输出方法要看第三个参数是否为true,true则仍然输出,
 *     false则失效；<br/>
 *     当LogUtil开关开启后，所有日志都输出，无论含有三个参数的方法的第三个参数是否为true。<br/>
 * @author:yefx
 * @Date:2015
 */
public class LogUtil {
    private static boolean isOpen=true;
    public static void i(String tag,String message)
    {
	if(isOpen)
	{
	    StackTraceElement caller=Thread.currentThread().getStackTrace()[3];
	    String tip=generateTag(caller);
	    Log.i(tag,  tip+"->"+message);
	}
    }
    public static void e(String tag,String message)
    {
	if(isOpen)
	{
	    Log.e(tag, message);
	}
    }
    public static void d(String tag,String message)
    {
	if(isOpen)
	{
	    Log.d(tag, message);
	}
    }
    private static String generateTag(StackTraceElement stack)
    {
	String tagFormat="%s.%s(Line:%d)";
	String tag="";
	String className=stack.getClassName();
	String methodName=stack.getMethodName();
	tag=String.format(tagFormat,className,methodName,stack.getLineNumber());
	return tag;
    }
}

