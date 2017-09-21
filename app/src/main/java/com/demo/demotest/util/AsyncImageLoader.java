package com.demo.demotest.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.demo.demotest.R;

public class AsyncImageLoader {
	private Context m_context;

	public AsyncImageLoader(Context context) {
		this.m_context = context;
		initMemCache();
		// init();
	}

	// 内存缓存默认 5M
	static final int MEM_CACHE_DEFAULT_SIZE = 5 * 1024 * 1024;
	// 一级内存缓存基于 LruCache
	private LruCache<String, SoftReference<Bitmap>> memCache;

	// private HashMap<String, Bitmap> memCache;
	private ExecutorService sExecutorService;
	/**
	 * 初始化内存缓存
	 */
	private void initMemCache() {
		memCache = new LruCache<String, SoftReference<Bitmap>>(MEM_CACHE_DEFAULT_SIZE) {
			@SuppressLint("NewApi")
			@Override
			protected int sizeOf(String key, SoftReference<Bitmap> bitmap) {
				return bitmap.get().getByteCount();
			}
		};
	}

	// private void init()
	// {
	// memCache=new HashMap<String,Bitmap>();
	// }
	/**
	 * 从内存缓存中拿
	 * 
	 * @param url
	 */
	private Bitmap getBitmapFromMem(String url) {
		if(url==null||memCache==null)
		{
			LogUtil.i("exception", "getBitmapFromMem-url="+url+"memCache="+memCache);
			return null;
		}
		SoftReference<Bitmap> bitmap = memCache.get(url);
		if(bitmap!=null)
		{
			return bitmap.get();
		}
		return null;
	}

	/**
	 * 加入到内存缓存中
	 * 
	 * @param url
	 * @param bitmap
	 */
	public void putBitmapToMem(String url, SoftReference<Bitmap> bitmap) {
		memCache.put(url, bitmap);
	}

	/**
	 * (需在子线程中使用)<br/>
	 * 获取图片的bitmap,如果图片存在于内存缓存中，则取出，如果存在本地缓存中，就返回该图片的Bitmap,
	 * 否则从网络上下载图片缓存到本地并返回图片的Bitmap)
	 * 
	 * @param url
	 *            网络路径
	 * @param cacheDir
	 *            本地下载缓存目录
	 * @return bitmap
	 * @throws Exception
	 */
	public Bitmap getImageBitmap(ImageView iv,String url, File cacheDir) {
		Bitmap bitmap = null;
		// 先从内存中拿
		bitmap = getBitmapFromMem(url);
		if (bitmap != null) {
			// Log.i("loader", url+"imageUrl exists in memory");
			return bitmap;
		}
		// 从本地缓存中取
		File localFile = new File(cacheDir, MD5Util.getMD5(url)
				+ url.substring(url.lastIndexOf(".")));
		if (localFile.exists()) {
			try {
				InputStream input = new FileInputStream(localFile);
				BitmapFactory.Options options=new BitmapFactory.Options();
			     options.inJustDecodeBounds = false;
			     options.inSampleSize = 10;   //width，hight设为原来的十分一
			     bitmap=BitmapFactory.decodeStream(input,null,options);
				memCache.put(url, new SoftReference<Bitmap>(bitmap));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		// 从网络下载
		else {
			iv.setImageResource(R.drawable.default_empty_photo);
			if(sExecutorService == null || sExecutorService.isShutdown() || sExecutorService.isTerminated()){  
	            sExecutorService = Executors.newFixedThreadPool(3);  
	        }
			try {
				HttpURLConnection conn = (HttpURLConnection) new URL(url)
						.openConnection();
				conn.setConnectTimeout(5000);
				conn.setRequestMethod("GET");
				if (conn.getResponseCode() == 200) {
					FileOutputStream out = new FileOutputStream(localFile);
					InputStream input = conn.getInputStream();
					byte[] buffer = new byte[1024];
					int len = -1;
					while ((len = input.read(buffer)) != -1) {
						out.write(buffer, 0, len);
					}
					BitmapFactory.Options options=new BitmapFactory.Options();
				     options.inJustDecodeBounds = false;
				     options.inSampleSize = 10;   //width，hight设为原来的十分一
				     bitmap=BitmapFactory.decodeStream(input,null,options);
					// 也可以在下载完毕后单独处理
					input.close();
					out.close();
					memCache.put(url,new SoftReference<Bitmap>(bitmap));

				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return bitmap;
	}
}
