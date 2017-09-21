package com.demo.demotest.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import android.os.Environment;


public class SDCardUtil {

	/**
	 * 
	 * 功能:获取标准的存储路径，有内置卡就返回内存卡路径；
	 * @return 
	 * @author: yefx
	 * @date:2016-1-27上午9:30:36
	 */
	public static String getStandardStoragePath()
	{
		String path=null;
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			path=getSecondStoragePath();
		}
		else
		{
			path= getExternalSDCardStoragePath();
		}
		return path;
	}
	/**
	 * 
	 * 功能:获取手机存储根目录下文件的路径
	 * @param fileName
	 * @return 
	 * @author: yefx
	 * @date:2016-3-14上午11:08:01
	 */
	public static String getStorageRootFilePath(String fileName)
	{
		return getStandardStoragePath()+File.separator+fileName;
	}
	/**
	 * 
	 * 功能:获取可移除的外置存储卡路径
	 * @return 
	 * @author: yefx
	 * @date:2016-1-29下午1:40:27
	 */
	public static String getSecondStoragePath()
	{
		String secondPath=null;
		secondPath= Environment.getExternalStorageDirectory().getAbsolutePath();
		return secondPath;
	}
	private static String getExternalSDCardStoragePath() {
		File file = new File("/system/etc/vold.fstab");
		FileReader fr = null;
		BufferedReader br = null;
		String path = null;
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
		}
		try {
			if (fr != null) {
				br = new BufferedReader(fr);
				String s = br.readLine();
				while (s != null) {
					if (s.startsWith("dev_mount")) {
						String[] tokens = s.split("\\s");
						path = tokens[2];
						if (!Environment.getExternalStorageDirectory()
								.getAbsolutePath().equals(path)) {
							break;
						}
					}
					s = br.readLine();
				}
			}
		} catch (IOException e) {
		} finally {
			try {
				if (fr != null) {
					fr.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
			}
		}
		return path;
	}

}
