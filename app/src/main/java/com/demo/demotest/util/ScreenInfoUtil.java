package com.demo.demotest.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;


public class ScreenInfoUtil {
	private DisplayMetrics metrics;
	private Display display;
	public ScreenInfoUtil(Context context)
	{
		metrics = new DisplayMetrics();
		display=((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
//		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
	}
	public int getWidth()
	{
//		return metrics.widthPixels;
		return display.getWidth();
	}
	public int getHeight()
	{
//		return metrics.heightPixels;
		return display.getHeight();
	}
}
