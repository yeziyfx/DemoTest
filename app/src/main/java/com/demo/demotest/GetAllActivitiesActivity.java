package com.demo.demotest;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.demo.demotest.base.BaseActivity;
import com.demo.demotest.util.LogSuperUtil;
import com.demo.demotest.util.SysConstant;


/**
 *
 * 项目名称: DemoBaseTest<br/>
 * 类名称: GetAllActivitiesActivity<br/>
 * 描述: 获取所有活动
 *
 * @author:yefx
 * @Date:2016-1-18下午2:55:52
 */
public class GetAllActivitiesActivity extends BaseActivity implements OnClickListener{

	private Button m_btnGet;

	@Override
	protected void init() {
		setContentView(R.layout.activity_get_all_activities);
	}

	@Override
	protected void initView() {
		setCommonTitleTitle("获取所有活动");
		m_btnGet = (Button) findViewById(R.id.btn_get_all_activities);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initListener() {
		setCommonTitleBackListener();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_get_all_activities:
			getAllActivities(m_context);
			break;

		default:
			break;
		}

	}
	private void getAllActivities(Context context)
	{
		 ActivityManager am = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);

		    // get the info from the currently running task
		    List<RunningTaskInfo> taskList = am.getRunningTasks(10);
		    RunningTaskInfo taskInfo;
		    for(int i=0;i<taskList.size();i++)
		    {
		    	taskInfo=taskList.get(i);
		    	ComponentName componentInfo = taskInfo.topActivity;
		    	LogSuperUtil.i(SysConstant.Log.test_activities, "id="+taskInfo.id+",top="+componentInfo.getClassName());
		    }

	}

}
