package com.demo.demotest;


import java.util.ArrayList;

import android.app.Activity;
import android.app.Application;

import com.demo.demotest.crash.CrashHandler;

public class AppContext extends Application {
	/**
	 * 是否显示崩溃日志
	 */
	private boolean m_bIsShowCrash=true;
	@Override
	public void onCreate() {
		super.onCreate();
		//注册错误崩溃日志检测器
		CrashHandler crashHandler = CrashHandler.getInstance();  
        crashHandler.init(this);
	}
}
