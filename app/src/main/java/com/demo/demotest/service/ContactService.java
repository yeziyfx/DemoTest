package com.demo.demotest.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.util.LruCache;
import android.util.Xml;

import com.demo.demotest.domain.Contact;
import com.demo.demotest.util.HttpUtils;
import com.demo.demotest.util.LogUtil;
import com.demo.demotest.util.MD5Util;

public class ContactService {
	/**
	 * 利用pull解析器对xml文件进行解析
	 * @param xmlStream xml文件输入流
	 * @return
	 * @throws Exception
	 */
	public static List<Contact> parseXML(InputStream xmlStream) throws Exception
	{
		List<Contact> list=new ArrayList<Contact>();
		Contact contact=null;
		XmlPullParser pullParser=Xml.newPullParser();
		pullParser.setInput(xmlStream, "utf-8");
		int event=pullParser.getEventType();
		while(event!=XmlPullParser.END_DOCUMENT)
		{
			switch (event) {
			case XmlPullParser.START_TAG:
				if("contact".equals(pullParser.getName()))
				{
					contact=new Contact();
					contact.id=new Integer(pullParser.getAttributeValue(0));
				}
				else if("name".equals(pullParser.getName()))
				{
					contact.name=pullParser.nextText();
				}
				else if("img".equals(pullParser.getName()))
				{
					contact.imageUrl=pullParser.getAttributeValue(0);
				}
				break;
			case XmlPullParser.END_TAG:
				if("contact".equals(pullParser.getName()))
				{
					list.add(contact);
					contact=null;  
				}
			default:
				break;
			}
			event=pullParser.next();
		}
		return list;
	}
	/**
	 * 从服务器端获取联系人信息(费时操作)
	 * @return
	 * @throws Exception
	 */
	public static List<Contact> getContacts() throws Exception
	{
		List<Contact> list=new ArrayList<Contact>();
		String xmlUrl=HttpUtils.getServerUrl();
		HttpURLConnection conn=(HttpURLConnection) new URL(xmlUrl).openConnection();
		conn.setConnectTimeout(15000);
		conn.setRequestMethod("GET");
		if(conn.getResponseCode()==200)
		{
			list= parseXML(conn.getInputStream());
		}
		else
		{
			LogUtil.i("exception", "ContactService-getContacts出错啦,code="+conn.getResponseCode());
		}
		return list;
	}
	public static List<Contact> getContactsTest() throws Exception
	{
		List<Contact> list=new ArrayList<Contact>();
		Contact contact=new Contact();
		contact.id=11;
		contact.name="woqu";
		contact.imageUrl="http://192.168.103.244:8010/FileUpload/download.ashx?href=2017\\11\\13\\18210039043_636461409112607111.jpg";
		list.add(contact);
		return list;
	}
	/**
	 * (暂时不用)
	 * @param path 网络路径
	 * @param cacheDir 缓存目录
	 * @return
	 * @throws Exception
	 */
	public static Uri getImage(String path,File cacheDir) throws Exception
	{
		Uri uri=null;
		File localFile=new File(cacheDir,MD5Util.getMD5(path)+path.substring(path.lastIndexOf(".")));
		if(localFile.exists())
		{
			uri=Uri.fromFile(localFile);
		}
		else
		{
			HttpURLConnection conn=(HttpURLConnection) new URL(path).openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			if(conn.getResponseCode()==200)
			{
				FileOutputStream out=new FileOutputStream(localFile);
				InputStream input=conn.getInputStream();
				byte[] buffer=new byte[1024];
				int len=-1;
				while((len=input.read(buffer))!=-1)
				{
					out.write(buffer, 0, len);
				}
				input.close();
				out.close();
				uri=Uri.fromFile(localFile);
			}
		}
		return uri;
	}
	
}
