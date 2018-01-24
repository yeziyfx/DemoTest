package com.demo.demotest.util;

import android.util.Log;

/**
 * 描述:<br/>Log工具类，可实现自己开关Log日志输出;<br/>
 * @author:yefx
 * @Date:2015
 */
public class LogSuperUtil {
    private static boolean isOpen=true;
    public static void i(String tag,String message)
    {
	if(isOpen)
	{
	    StackTraceElement caller1=Thread.currentThread().getStackTrace()[4];
	    StackTraceElement caller2=Thread.currentThread().getStackTrace()[3];
	    String tip=generateTag(caller2);
	    //			tip+="\n"+generateTag(calle2);
	    Log.i(tag,  tip+"->"+message);
	}
    }
    public static void e(String tag,String message)
    {
	if(isOpen)
	{
	    StackTraceElement caller1=Thread.currentThread().getStackTrace()[4];
	    StackTraceElement calle2=Thread.currentThread().getStackTrace()[3];
	    String tip=generateTag(caller1)+"\n"+generateTag(calle2);
	    Log.e(tag,  tip+"->"+message);
	}
    }
    public static void d(String tag,String message)
    {
	if(isOpen)
	{
	    StackTraceElement caller1=Thread.currentThread().getStackTrace()[4];
	    StackTraceElement calle2=Thread.currentThread().getStackTrace()[3];
	    String tip=generateTag(caller1)+"\n"+generateTag(calle2);
	    Log.d(tag,  tip+"->"+message);
	}
    }
	private static String generateTag(StackTraceElement stack) {
		String tagFormat = "%s.%s%s";//%s.%s(Line:%d)
		String tag = "";
		String className = stack.getClassName();
		String methodName = stack.getMethodName();
		String strStack=stack.toString();
		tag = String.format(tagFormat, className, methodName,
				strStack.substring(strStack.lastIndexOf("(")));//stack.getLineNumber()
		return tag;
	}
    private static String generateTag2(StackTraceElement stack)
    {
	String tagFormat="%s.%s(Line:%d)";
	String tag="";
	String className=stack.getClassName();
	String methodName=stack.getMethodName();
	tag=String.format(tagFormat,className,methodName,stack.getLineNumber());
	return tag;
    }

    public static void v(String tag, String message) {
	if (isOpen) {
	    StackTraceElement caller1=Thread.currentThread().getStackTrace()[4];
	    StackTraceElement calle2=Thread.currentThread().getStackTrace()[3];
	    String tip=generateTag(caller1)+"\n"+generateTag(calle2);
	    Log.v(tag,  tip+"->"+message);
	}
    }
}

