package com.demo.demotest.util;

import android.content.Context;
import android.os.Looper;
import android.os.SystemClock;
import android.widget.Toast;

/**
 * 
 * 项目名称:flood<br/>
 * 类名称:ToastUtil<br/>
 * 描述:单例模式实时显示Toast提示消息
 * 
 * @author:yefx
 * @Date:2015-3-17下午7:16:34
 */
public class ToastUtil {

	private static Toast toast;
	
	/**
	 * 当前工具类的总开关
	 */
	private static boolean isAllowShow = true;

	/**
	 * 
	 * 功能:开关(isAllowShow开启后，此方法功能可用)
	 * 
	 * @param context
	 * @param msg
	 * @author: yefx
	 * @date:2015-3-26下午2:35:57
	 */
	public static void show(Context context, String msg) {
		if (!isAllowShow) {
			return;
		}
		// 开关开启后才往下执行
		if (toast == null) {
			toast = Toast.makeText(context, msg, 0);
		} else {
			toast.setText(msg);
		}
		toast.show();
	}
	public static void show(Context context, int resId) {
		if (!isAllowShow) {
			return;
		}
		String msg=context.getResources().getString(resId);
		// 开关开启后才往下执行
		if (toast == null) {
			toast = Toast.makeText(context,msg, 0);
		} else {
			toast.setText(msg);
		}
		toast.show();
	}


	/**
	 * 
	 * 功能:一直显示Toast
	 * 
	 * @param context
	 * @param msg
	 * @param flag
	 * @author: yefx
	 * @date:2015-4-24下午1:51:07
	 */
	public static void alwaysShow(final Context context, final String msg, final boolean flag) {
		new Thread() {
			public void run() {
				while (true) {
					Looper.prepare();
					SystemClock.sleep(3000);
//					show(context, msg, flag);
					Toast.makeText(context, msg, 0).show();
					Looper.loop();
				}
			};
		}.start();

	}
}
