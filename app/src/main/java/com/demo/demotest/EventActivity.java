package com.demo.demotest;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

import com.demo.demotest.base.BaseActivity;
import com.demo.demotest.util.LogSuperUtil;
import com.demo.demotest.util.SysConstant;

/**
 * 
 * 项目名称: DemoBaseTest<br/>
 * 类名称: EventActivity<br/>
 * 描述: 事件传递、分发
 *
 * @author:yefx
 * @Date:2016-1-5上午11:11:39
 */
public class EventActivity extends BaseActivity {

	private Button m_btnEvent;

	@Override
	protected void init() {
		setContentView(R.layout.activity_event);
		
	}

	@Override
	protected void initView() {
		m_btnEvent = (Button) findViewById(R.id.btn_event);

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initListener() {
		setCommonTitleBackListener();
		m_btnEvent.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LogSuperUtil.i(SysConstant.Log.test_event, "onClick execute");
				
			}
		});
		m_btnEvent.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				LogSuperUtil.i(SysConstant.Log.test_event, "onTouch execute, action=" + event.getAction()+MotionEvent.ACTION_DOWN); 
				return true;
			}
		});
	}



}
