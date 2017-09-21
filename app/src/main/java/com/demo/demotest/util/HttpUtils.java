package com.demo.demotest.util;


public class HttpUtils {
	private static String serverUrl="http://192.168.1.101:8080/ServerForListViewAsync/list.xml";
	public static String getServerUrl()
	{
		return serverUrl;
	}
	public static void setServerUrl(String url)
	{
		serverUrl=url;
	}
	public static String serverIPPort="";
	public static String getServerIPPort()
	{
		return serverIPPort;
	}
	public static void setServerIPPort(String ipPort)
	{
		serverIPPort=ipPort;
	}
	private static String serverImageDir="http://192.168.1.101:8080/ServerForListViewAsync/images/";
	public static String getServerImageDir()
	{
		return serverImageDir;
	}
	public static void setServerImageDir(String dir)
	{
		serverImageDir=dir;
	}
}
