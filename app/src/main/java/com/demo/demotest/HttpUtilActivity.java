package com.demo.demotest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.demo.demotest.R;
import com.demo.demotest.base.BaseActivity;
import com.demo.demotest.util.LogUtil;

public class HttpUtilActivity extends BaseActivity implements OnClickListener{

	private String TAG = "HttpUtilActivity";
	private EditText m_etUrl;
	private Button m_btnDownload;
	private final int what_downloading = 11;
	private final int what_succ = 12;
	private final int what_fail = 13;
	private final int what_test_finished = 14;
	private Handler m_handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case what_downloading:
//				LogUtil.i(TAG, "size=" + msg.arg1);
				break;
			case what_fail:
				download();
				break;
			case what_test_finished:
				download();
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void init() {
		setContentView(R.layout.activity_httputil);
	}

	@Override
	protected void initView() {
		m_etUrl = (EditText) findViewById(R.id.et_httputil);
		m_btnDownload = (Button) findViewById(R.id.btn_download_httputil);
	}

	@Override
	protected void initData() {
		m_etUrl.setText(R.string.url_download_default_teacher);
	}

	@Override
	protected void initListener() {
		m_btnDownload.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.btn_download_httputil:
			url = m_etUrl.getText().toString().trim();
			savePath = Environment.getExternalStorageDirectory() + "/test.apk";
			testConnection();
			break;

		default:
			break;
		}
	}

	private void testConnection() {
		new Thread() {

			public void run() {
				try {
					RandomAccessFile fos = null;
					HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
					endPosition = conn.getContentLength();
					LogUtil.i(TAG, "endPosition=" + endPosition);
					conn.disconnect();
					m_handler.sendEmptyMessage(what_test_finished);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}.start();

	}

	private String url, savePath;
	private long startPosition = 0;
	/**服务器端文件的最后字节位置*/
	private int endPosition;
	int currPosition;
	private void download() {
		final File file = new File(savePath);

		if (file.exists()) {
			startPosition = file.length();
		}
		new Thread() {

			public void run() {
				try {
//					RandomAccessFile fos = null;
					HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
					// 设置当前线程下载的起点，终点
					conn.setConnectTimeout(15000);
					conn.setRequestMethod("GET");
//					conn.setAllowUserInteraction(true);
//					conn.setRequestProperty("Range", "bytes=" + startPosition + "-"+endPosition);
//					LogUtil.i(TAG, "totalLen=" + conn.getContentLength());
					// 使用java中的RandomAccessFile 对文件进行随机读写操作
//					fos = new RandomAccessFile(file, "rw");
					// 设置开始写文件的位置
//					fos.seek(startPosition);
					FileOutputStream fos=new FileOutputStream(file);
					if (conn.getResponseCode()==200) {
						BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
						int bufferSize=1024*1024;
						byte[] buffer = new byte[bufferSize];
						int len = -1;
						currPosition= (int) startPosition;
						LogUtil.i(TAG, "开始currLen=" + currPosition);
						while ((len = bis.read(buffer,0,bufferSize))!=-1) {
							fos.write(buffer, 0, len);
							currPosition += len;
							Message msg = Message.obtain();
							msg.arg1 = currPosition;
							msg.what = what_downloading;
							LogUtil.i(TAG, "currPosition=" + currPosition+"本次len="+len);
							m_handler.sendMessageDelayed(msg, 50);
						}
						bis.close();
						fos.close();
						LogUtil.i(TAG, "fos");
					} else {
//						LogUtil.i(TAG, "conn.getResponseCode()=" + conn.getResponseCode());
					}
					if(currPosition<endPosition)
					{
//						m_handler.sendEmptyMessage(what_fail);
					}
				} catch (Exception e) {
					LogUtil.i(TAG, "e=" + e);
					throw new RuntimeException(e);
				}
			}
		}.start();

	}
}
