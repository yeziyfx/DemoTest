package com.demo.demotest.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {
	private final String format1="yyyy-MM-dd HH:mm:ss";
	private final String format2="yyyy-MM-dd";
	/**
	 * 
	 * 功能:将类似"2014-10-31 08:00:00"这中格式的日期字符串转换成毫秒数(相对于格林威治时间1970年01月01日00:00:00而言)
	 * @param strDate 格式为:"2014-10-31 08:00:00"
	 * @return (单位:毫秒)
	 * @author: yefx
	 * @date:2015-4-29上午10:01:16
	 */
	public static long convertToLong(String strDate)
	{
		long time=0;
		String[] arrDT=strDate.split(" ");// "2014-10-31" "08:00:00"
		String[] arrDate=arrDT[0].split("-");// "2014" "10" "31"
		String[] arrTime=arrDT[1].split(":");// "08" "00" "00"
		int year=realValueOfInt(arrDate[0])-1;
		int month=realValueOfInt(arrDate[1])-1;
		int day=realValueOfInt(arrDate[2])-1;
		int hour=realValueOfInt(arrTime[0]);
		int minute=realValueOfInt(arrTime[1]);
		int second=realValueOfInt(arrTime[2]);
		Calendar c=Calendar.getInstance();
		c.set(year+1, month, day+1,hour, minute, second);
		time=c.getTimeInMillis();
		return time;
	}
	/**
	 * 
	 * 功能:将数字字符串去掉首位的0(当前默认首只有一个0，即只去掉第一个0，类似02)，获取其对应整型值
	 * @param str 数字字符串
	 * @return -1,则不合法的数字字符串
	 * @author: yefx
	 * @date:2015-4-29上午10:07:48
	 */
	private static int realValueOfInt(String str)
	{
		int n=-1;
		int length=-1;
		if(str!=null)
		{
			length=str.length();
			if(str.indexOf("0")==0)
			{
				str=str.substring(1, str.length());
			}
			n=Integer.valueOf(str);
		}
		return n;
	}
	public static long getTimeFromFormat1(String strDate)
	{
		long time=0;
		String[] arrDT=strDate.split(" ");// "2014-10-31" "08:00:00"
		String[] arrDate=arrDT[0].split("-");// "2014" "10" "31"
		String[] arrTime=arrDT[1].split(":");// "08" "00" "00"
		int year=realValueOfInt(arrDate[0]);
		int month=realValueOfInt(arrDate[1])-1;
		int day=realValueOfInt(arrDate[2]);
		int hour=realValueOfInt(arrTime[0]);
		int minute=realValueOfInt(arrTime[1]);
		int second=realValueOfInt(arrTime[2]);
		Calendar c=Calendar.getInstance();
		c.set(year, month, day,hour, minute, second);
		time=c.getTimeInMillis();
		return time;
	}
	public static long getTimeAsSecondFromFormat1(String strDate)
	{
		return getTimeFromFormat1(strDate)/1000;
	}
	public static long getTimeFromFormat2(String strDate)
	{
		long time=-1;
		String[] arr=strDate.split("-");
		int year=Integer.parseInt(arr[0]);
		int month=Integer.parseInt(arr[1])-1;
		int day=Integer.parseInt(arr[2]);
		Calendar c=Calendar.getInstance();
		c.set(year, month, day, 0, 0, 0);
		return c.getTimeInMillis();
	}
	public static long getTimeAsSecondFromFormat2(String strDate)
	{
		return getTimeFromFormat2(strDate)/1000;
	}
	public static String getAsFormat1From(long time)
	{
		String str="";
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		str=formatter.format(new Date(time));
		return str;
	}
	public static String getAsFormat1FromSecond(long time)
	{
		return getAsFormat1From(time*1000);
	}
	public static String getAsFormat1FromSecond(String time)
	{
		return getAsFormat1From(Long.parseLong(time)*1000);
	}
	/**
	 * 
	 * 功能:暂不使用
	 * @param intStr
	 * @return 
	 * @author: yefx
	 * @date:2015-7-13下午4:10:15
	 */
	private int getRealIntValue(String intStr)
	{
		int value=-1;
		while(intStr.indexOf("0")==0)
		{
			intStr=intStr.substring(intStr.indexOf("0")+1);
		}
		if(intStr.length()==0)
		{
			value=0;
		}
		else
		{
			value=Integer.parseInt(intStr);
		}
		return value;
	}
}
