package com.demo.demotest;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.demo.demotest.R;
import com.demo.demotest.base.BaseActivity;
import com.demo.demotest.util.LogUtil;


public class SuperLogActivity extends BaseActivity implements OnClickListener{
	private Button m_btnGenerateLog;

	@Override
	protected void init() {
		setContentView(R.layout.activity_super_log);
	}

	@Override
	protected void initView() {
		m_btnGenerateLog = (Button) findViewById(R.id.btn_generate_log);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initListener() {
		m_btnGenerateLog.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_generate_log:
			generateLog();
			break;

		default:
			break;
		}
	}

	private void generateLog() {
		LogUtil.i("file", "生成日志");
	}

}
