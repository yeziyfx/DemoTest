package com.demo.demotest;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.demo.demotest.R;
import com.demo.demotest.base.BaseActivity;

public class LoginActivity extends BaseActivity implements OnClickListener{
	private Context m_context;
	private Button m_btnLogin;
	@Override
	public void init() {
		m_context=this;
		setContentView(R.layout.activity_login);
	}

	@Override
	public void initView() {
		m_btnLogin = (Button) findViewById(R.id.btn_login);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		m_btnLogin.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_login:
//			toActivity(TestActivity.class);
			toActivity(ExceptionHappenActivity.class);
//			finish();
			break;

		default:
			break;
		}
	}


}
