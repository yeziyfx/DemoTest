package com.demo.demotest;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.demo.demotest.R;
import com.demo.demotest.base.BaseActivity;

/**
 * 
 * 项目名称: DemoBaseTest<br/>
 * 类名称: ToastAndLogActivity<br/>
 * 描述: 测试是不是Toast显示完毕才执行下面代码
 *
 * @author:yefx
 * @Date:2015-5-18下午2:28:00
 */
public class ToastAndLogActivity extends BaseActivity implements OnClickListener{

	private TextView m_tvMsg;
	private Button m_btnTest;
	private int m_iCount=0;
	@Override
	protected void init() {
		setContentView(R.layout.activity_toast_and_log);
	}

	@Override
	protected void initView() {
		m_tvMsg = (TextView) findViewById(R.id.tv_msg);
		m_btnTest = (Button) findViewById(R.id.btn_test);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initListener() {
		m_btnTest.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_test:
			run();
			break;

		default:
			break;
		}
	}
	private void run()
	{
		showToast("toast提示");
		m_iCount++;
		m_tvMsg.setText("第"+m_iCount+"次文字改变了");
	}

}
