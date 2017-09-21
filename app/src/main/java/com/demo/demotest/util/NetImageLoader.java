package com.demo.demotest.util;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public abstract class NetImageLoader {
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			Bitmap bitmap = (Bitmap) msg.obj;
			loadDown(iv, bitmap);
		};
	};
	private Context m_context;
	private File cacheDir;
	public NetImageLoader(Context context,File cacheDir)
	{
		m_context=context;
		this.cacheDir=cacheDir;
	}
	public abstract void loadDown(ImageView imageView,Bitmap bitmap);

	private ImageView iv;
	public void downLoadImage(ImageView imageView,final String imgUrl){
		this.iv = imageView;
//		new Thread(){
//			public void run() {
//				try {
//					URL url = new URL(imgUrl);
//					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//					connection.setConnectTimeout(5000);
//					connection.setReadTimeout(5000);
//					InputStream is = connection.getInputStream();
//					Bitmap bitmap = BitmapFactory.decodeStream(is);
//					Thread.sleep(1000);
//					Message message = Message.obtain();
//					message.obj = bitmap;
//					handler.sendMessage(message);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			};
//		}.start();
		new Thread()
		{
			public void run()
			{
				Message message = Message.obtain();
				message.obj = new AsyncImageLoader(m_context).getImageBitmap(iv,imgUrl, cacheDir);
				handler.sendMessage(message);
			}
		}.start();


	}
}