package com.demo.demotest.receiver;

import com.demo.demotest.ExceptionShowActivity;
import com.demo.demotest.util.Constants;
import com.demo.demotest.util.LogUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
/**
 * 
 * 项目名称: DemoBase<br/>
 * 类名称: ErrorHappenReceiver<br/>
 * 描述: 错误发生的广播接收者
 *
 * @author:yefx
 * @Date:2015-4-30上午10:37:16
 */
public class ErrorHappenReceiver extends BroadcastReceiver {
	private final String TAG="demo ErrorHappenReceiver";
	//
	private String m_strMsg=null;
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		LogUtil.i(TAG,  "框架自带接收器--onReceive");
		if(!Constants.Crash.ISSHOWERROR)
		{
			return;
		}
		if(arg1!=null)
		{
			String strAction=arg1.getAction();
			m_strMsg=arg1.getStringExtra(Constants.Crash.KEY_ERROR);
			if(m_strMsg==null)
			{
				m_strMsg="接收者未接收到CrashHandler传来的信息";
			}
			LogUtil.i(TAG, "strAction="+strAction);
			if(strAction.equals(Constants.Crash.ACTION_ERROR_HAPPEN))
			{
				Intent i=new Intent(arg0,ExceptionShowActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				i.putExtra(Constants.Crash.KEY_ERROR, m_strMsg);
				arg0.startActivity(i);
				LogUtil.i(TAG,  "startActivity");
			}
		}
	}

}
