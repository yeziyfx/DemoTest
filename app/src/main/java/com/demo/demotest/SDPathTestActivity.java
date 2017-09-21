package com.demo.demotest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.text.Html;
import android.text.format.Formatter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.demo.demotest.base.BaseActivity;
import com.demo.demotest.util.LogSuperUtil;
import com.demo.demotest.util.SysConstant;

/**
 * 
 * 项目名称: DemoBaseTest<br/>
 * 类名称: SDPathTestActivity<br/>
 * 描述: SD卡路径测试
 * 
 * @author:yefx
 * @Date:2016-1-18上午9:57:14
 */
@SuppressLint("NewApi")
public class SDPathTestActivity extends BaseActivity implements OnClickListener {

	private TextView m_tvPath;
	private Button m_btnGetPath;
	private Button m_btnGetSecPath;
	File file = new File("/system/etc");

	@Override
	protected void init() {
		setContentView(R.layout.activity_sd_path);
	}

	@Override
	protected void initView() {
		setCommonTitleTitle("存储路径测试");
		m_tvPath = (TextView) findViewById(R.id.tv_sd_path);
		m_btnGetPath = (Button) findViewById(R.id.btn_sd_path);
		m_btnGetSecPath = (Button) findViewById(R.id.btn_second_path);
	}

	@Override
	protected void initData() {

	}

	@Override
	protected void initListener() {
		setCommonTitleBackListener();
		m_btnGetPath.setOnClickListener(this);
		m_btnGetSecPath.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_sd_path:
			getSDPath();
			
			break;
		case R.id.btn_second_path:
			// getInnerStoragePath(file);
//			http://imgpolitics.gmw.cn/attachement/jpg/site2/20160304/bc305baebed8184320740b.jpg
			getSelfStoragePath();

			break;

		default:
			break;
		}

	}

	private void getSDPath() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			String sdPath = Environment.getExternalStorageDirectory().getPath();
			if (sdPath != null) {
				String strTotalSpace = Formatter.formatFileSize(m_context, file.getTotalSpace());
				String strFreeSpace = Formatter.formatFileSize(m_context, file.getFreeSpace());
				m_tvPath.setText("SD卡已挂载，其路径是:" + sdPath +" , 总空间："+strTotalSpace+",剩余空间:"+strFreeSpace);
			}
			else
			{
				m_tvPath.setText("SD卡已挂载，但其路径不存在");
			}
		} else {
			// getInnerStoragePath(file);
			getSelfStoragePath();
		}

	}

	private void getInnerStoragePath(File parentFile) {
		// File file = new File("/system/etc/vold.fstab");
		// File file = new File("/system/etc/");
		String secondPath = null;
		if (!parentFile.exists()) {
			// LogSuperUtil.i(SysConstant.Log.test_sd_path, " - file not found!!");
		} else {
			// LogSuperUtil.i(SysConstant.Log.test_sd_path, " - file exist");
			for (File f : parentFile.listFiles()) {
				if (f.isFile()) {

					if (f.getName().endsWith("fstab")) {
						LogSuperUtil.i(SysConstant.Log.test_sd_path, " fileName=" + f.getName());
					}
				} else {
					LogSuperUtil.i(SysConstant.Log.test_sd_path, f.getName() + " is dir");
					getInnerStoragePath(f);
				}
			}
		}
		// m_tvPath.setText("内置卡路径是:"+secondPath);
	}

	/**
	 * 
	 * 功能: 获取机身存储路径
	 * 
	 * @author: yefx
	 * @date:2016-3-3下午5:56:06
	 */
	private void getSelfStoragePath() {
		File file = new File("/system/etc/vold.fstab");
		FileReader fr = null;
		BufferedReader br = null;
		String selfStroagePath = null;
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
			LogSuperUtil.i(SysConstant.Log.test_sd_path, " - file not found!!");
		}
		try {
			if (fr != null) {
				br = new BufferedReader(fr);
				String s = br.readLine();
				while (s != null) {
					LogSuperUtil.i(SysConstant.Log.test_sd_path, "vold.fstab : " + s);
					if (s.startsWith("dev_mount")) {
						String[] tokens = s.split("\\s");
						selfStroagePath = tokens[2];
						if (!Environment.getExternalStorageDirectory().getAbsolutePath().equals(selfStroagePath)) {
							break;
						}
					}
					s = br.readLine();
				}
			}
		} catch (IOException e) {
			LogSuperUtil.i(SysConstant.Log.test_sd_path, " - IOException");
		} finally {
			try {
				if (fr != null) {
					fr.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				LogSuperUtil.i(SysConstant.Log.test_sd_path, "- finally - IOException");
			}
		}
		if (selfStroagePath != null) {
			String strTotalSpace = Formatter.formatFileSize(m_context, file.getTotalSpace());
			String strFreeSpace = Formatter.formatFileSize(m_context, file.getFreeSpace());
			m_tvPath.setText("机身存储路径是:" + selfStroagePath+" , 总空间："+strTotalSpace+",剩余空间:"+strFreeSpace);
		}
		else
		{
			m_tvPath.setText(Html.fromHtml("<p><font color=red>机身存储路径不存在</font></p>"));
//			m_tvPath.setText("机身存储路径不存在");
		}
		
	}
}
