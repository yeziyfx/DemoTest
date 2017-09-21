package com.demo.demotest;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.demo.demotest.R;
import com.demo.demotest.base.BaseActivity;

public class ExceptionActivity extends BaseActivity implements OnClickListener{
	private Context m_context;
	
	private AppContext m_appContext;

	private Button m_btnGoException;
	@Override
	public void init() {
		m_context=this;
		m_appContext=(AppContext) getApplication();
		
		setContentView(R.layout.activity_main);
	}

	@Override
	public void initView() {
		m_btnGoException = (Button) findViewById(R.id.btn_go_exception);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		m_btnGoException.setOnClickListener(this);
	}


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn_go_exception:
			toActivity(ExceptionHappenActivity.class);
			break;
			
		default:
			break;
		}
	}


}
