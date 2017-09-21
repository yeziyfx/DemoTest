package com.demo.demotest;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.demo.demotest.R;
import com.demo.demotest.base.BaseActivity;

/**
 * 
 * 项目名称: DemoBaseTest<br/>
 * 类名称: LinearPressedActivity<br/>
 * 描述: 按钮在布局内的点击效果(布局也设置了点击事件)
 *
 * @author:yefx
 * @Date:2015-8-13下午1:57:24
 */
public class LinearPressedActivity extends BaseActivity implements OnClickListener{

	private LinearLayout m_lin;
	private Button m_btnPressed;
	@Override
	protected void init() {
		setContentView(R.layout.activity_linear_pressed);
	}

	@Override
	protected void initView() {
		m_lin = (LinearLayout) findViewById(R.id.lin_linear_pressed);
		m_btnPressed = (Button) findViewById(R.id.btn_linear_pressed);

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initListener() {
		m_lin.setOnClickListener(this);
		m_btnPressed.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.lin_linear_pressed:
			
			showToast("线性布局被点击了");
			break;
		case R.id.btn_linear_pressed:
			showToast("按钮被点击了");
			break;

		default:
			break;
		}
	}
}
