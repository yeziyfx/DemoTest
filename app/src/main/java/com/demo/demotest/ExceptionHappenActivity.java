package com.demo.demotest;

import com.demo.demotest.R;
import com.demo.demotest.base.BaseActivity;

public class ExceptionHappenActivity extends BaseActivity {
	private String s;
	@Override
	public void init() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_error_happen);
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		System.out.print(s.equals("nothing"));
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub

	}

}
