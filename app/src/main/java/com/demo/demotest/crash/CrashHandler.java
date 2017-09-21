package com.demo.demotest.crash;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.demo.demotest.AppContext;
import com.demo.demotest.R;
import com.demo.demotest.util.Constants;
import com.demo.demotest.util.LogSuperUtil;
import com.demo.demotest.util.LogUtil;
import com.demo.demotest.util.SysConstant;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 *
 * @author user
 *
 */
public class CrashHandler implements UncaughtExceptionHandler {

	public String appName = "未知应用";
	public static final String TAG = "crash CrashHandler";
	// private boolean m_bIsShowCrash=true;
	private String m_strErrorMessage = "没有错误信息,很可能是因为没有文件读写权限，导致异常处理器本身出错.请确保添加了文件读写权限";
	// 系统默认的UncaughtException处理类
	private UncaughtExceptionHandler mDefaultHandler;
	// CrashHandler实例
	private static CrashHandler INSTANCE = new CrashHandler();
	// 程序的Context对象
	private Context mContext;
	// 用来存储设备信息和异常信息
	private Map<String, String> infos = new HashMap<String, String>();

	// 用于格式化日期,作为日志文件名的一部分
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

	/** 保证只有一个CrashHandler实例 */
	private CrashHandler() {
	}

	/** 获取CrashHandler实例 ,单例模式 */
	public static CrashHandler getInstance() {
		return INSTANCE;
	}

	private AppContext m_appContext;

	/**
	 * 初始化
	 *
	 * @param context
	 */
	public void init(AppContext application) {
		m_appContext = application;
		mContext = m_appContext.getApplicationContext();
		appName = m_appContext.getResources().getString(R.string.app_name);
		// 获取系统默认的UncaughtException处理器
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		// 设置该CrashHandler为程序的默认处理器
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * 当UncaughtException发生时会转入该函数来处理
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		boolean bFinished = handleException(ex);
		if (!bFinished && mDefaultHandler != null) {
			LogUtil.i(TAG, "异常未处理完毕");
			// 如果用户没有处理则让系统默认的异常处理器来处理
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				Log.e(TAG, "Line93-error : ", e);
			}
			// 退出程序
			Intent i = new Intent();
			i.putExtra(Constants.Crash.KEY_ERROR, m_strErrorMessage);
			i.setAction(Constants.Crash.ACTION_ERROR_HAPPEN);
			mContext.sendBroadcast(i);

			LogUtil.i(TAG, "已发送广播");
			if (mDefaultHandler != null) {
				mDefaultHandler.uncaughtException(thread, ex);
			} else {
				android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(0);
			}
		}
	}

	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
	 *
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false.
	 */
	private boolean handleException(Throwable ex) {
		LogUtil.d(TAG, "进入handleException方法");
		if (ex == null) {
			return false;
		}
		// 使用Toast来显示异常信息
		new Thread() {

			@Override
			public void run() {
				Looper.prepare();
				Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_LONG).show();
				Looper.loop();
			}
		}.start();
		// 收集设备参数信息
		collectDeviceInfo(mContext);
		// 保存日志文件
		saveCrashInfo2File(ex);
		return true;
	}

	/**
	 * 收集设备参数信息
	 *
	 * @param ctx
	 */
	public void collectDeviceInfo(Context ctx) {
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				String versionName = pi.versionName == null ? "null" : pi.versionName;
				String versionCode = pi.versionCode + "";
				infos.put("versionName", versionName);
				infos.put("versionCode", versionCode);
			}
		} catch (NameNotFoundException e) {
			Log.e(TAG, "Line166-an error occured when collect package info", e);
		}
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				infos.put(field.getName(), field.get(null).toString());
				LogUtil.i(TAG, field.getName() + " : " + field.get(null));
			} catch (Exception e) {
				LogUtil.i(TAG, "an error occured when collect crash info");
			}
		}
	}

	/**
	 * 保存错误信息到文件中
	 *
	 * @param ex
	 * @return 返回文件名称,便于将文件传送到服务器
	 */
	private String saveCrashInfo2File(Throwable ex) {
		long timestamp = System.currentTimeMillis();
		String time = formatter.format(new Date());
		StringBuffer sb = new StringBuffer();
		// 暂不加入硬件信息
		// for (Map.Entry<String, String> entry : infos.entrySet()) {
		// String key = entry.getKey();
		// String value = entry.getValue();
		// sb.append(key + "=" + value + "\n");
		// }
		sb.append(appName + "      " + time + "\n");
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		sb.append(result);
		try {
			String fileName = "crash-" + time + "@" + appName + "-" + timestamp + ".log";
//			crash-2016-03-01-10-29-31@工具大全-1456799371346.log
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				String path = Environment.getExternalStorageDirectory().toString() + "/crash/" + Constants.Crash.subDir;
				LogSuperUtil.i(SysConstant.Log.exception, "存储异常文件的目录是："+path);
//				/storage/sdcard0/crash/demoTest/
				File dir = new File(path);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				FileOutputStream fos = new FileOutputStream(path + fileName);
				fos.write(sb.toString().getBytes());
				fos.close();
				m_strErrorMessage = sb.toString();
				// LogUtil.d(TAG, true, "错误信息是="+m_strErrorMessage);
			}
			return fileName;
		} catch (Exception e) {
			LogUtil.i(TAG, "an error occured while writing file...");
		}
		return null;
	}
}
