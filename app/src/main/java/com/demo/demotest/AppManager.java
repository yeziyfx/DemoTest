package com.demo.demotest;

import java.util.Stack;

import android.app.Activity;

/**
 * 
 * 项目名称: SmartSchoolTeacher<br/>
 * 类名称: AppManager<br/>
 * 描述: Activity管理器
 *
 * @author:yefx
 * @Date:2015-7-28下午2:35:20
 */
public class AppManager {
	private static Stack<Activity> activityStack;
	private static AppManager instance;
	private AppManager()
	{
		
	}
	public static AppManager getActivityManagerInstance()
	{
		if(instance==null)
		{
			instance=new AppManager();
			activityStack=new Stack<Activity>();
		}
		return instance;
	}
	/**
	 * 
	 * 功能: 移除当前Activity(即栈顶Activity)
	 * @author: yefx
	 * @date:2015-7-28下午3:04:40
	 */
	public void removeCurrentActivity()
	{
		Activity activity=activityStack.lastElement();
		if(activity!=null)
		{
			activity.finish();
			activity=null;
		}
	}
	/**
	 * 
	 * 功能:移除指定Activity
	 * @param activity 
	 * @author: yefx
	 * @date:2015-7-28下午3:06:14
	 */
	public void removeActivity(Activity activity)
	{
		if(activity!=null)
		{
			activity.finish();
			activityStack.remove(activity);
			activity=null;
		}
	}
	/**
	 * 
	 * 功能:获取当前Activity(即栈顶Activity)
	 * @return 
	 * @author: yefx
	 * @date:2015-7-28下午3:07:16
	 */
	public Activity getCurrentActivity()
	{
		
		Activity activity=activityStack.lastElement();
		return activity;
	}
	/**
	 * 
	 * 功能:添加Activity到栈中
	 * @param activity 
	 * @author: yefx
	 * @date:2015-7-28下午3:08:56
	 */
	public void addActivity(Activity activity)
	{
		activityStack.add(activity);
	}
	/**
	 * 
	 * 功能:移除其他所有Activity,只保留clazz类型的实例Activity
	 * @param clazz 
	 * @author: yefx
	 * @date:2015-7-28下午3:22:42
	 */
	public void removeAllActivityExceptOne(Class clazz)
	{
		for(Activity activity:activityStack)
		{
			if(!activity.getClass().equals(clazz))
			{
				removeActivity(activity);
				break;
			}
		}
	}
	/**
	 * 
	 * 功能:移除所有Activity 
	 * @author: yefx
	 * @date:2015-7-28下午3:09:22
	 */
	public void removeAllActivity()
	{
		for(Activity activity:activityStack)
		{
			removeActivity(activity);
		}
	}
}
