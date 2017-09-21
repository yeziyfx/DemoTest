package com.demo.demotest.util;

import java.io.File;

import android.os.Environment;

/**
 * 资源路径管理中心
 * @author Administrator
 *
 */
public class ResPathCenter {
	private static ResPathCenter resPathCenter;
	private String sdCardRoot="";
	private ResPathCenter()
	{
		sdCardRoot=SDCardUtil.getStandardStoragePath()+File.separator;
	}
	public static ResPathCenter getInstance()
	{
		if(resPathCenter==null)
		{
			synchronized (ResPathCenter.class) {
				resPathCenter=new ResPathCenter();
			}
		}
		return resPathCenter;
	}
	/**
	 * 获取sd卡根目录（最后带一个"/"）
	 * @return
	 */
	public String getSDCardRoot()
	{
		return sdCardRoot;
	}
	public String getSDCardFilePath(String filePath)
	{
		return sdCardRoot+filePath;
	}
	public String getHTMLDirPath()
	{
		return sdCardRoot+"AHTML"+File.separator;
	}
}
