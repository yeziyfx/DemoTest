package com.demo.demotest;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.demo.demotest.R;
import com.demo.demotest.base.BaseActivity;
/**
 * 
 * 项目名称: DemoBaseTest<br/>
 * 类名称: AlwaysToastActivity<br/>
 * 描述: 一直显示Toast提示(目标)
 *
 * @author:yefx
 * @Date:2015-5-18下午2:02:41
 */
public class AlwaysToastActivity extends BaseActivity implements OnClickListener{
	private Button m_btnLogin;
	private int count = 0;
	//Handler
	private final int WHAT_OK=99;
	private Handler m_hanlder=new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			if(msg.what==WHAT_OK)
			{
//				m_btnLogin.setText("正在第"+count+"次尝试登录");
				showToast("正在第"+count+"次尝试登录");
				count++;
				if(count<=10)
				{
					
				}
				m_hanlder.sendEmptyMessageDelayed(WHAT_OK, 2000);
			}
		}
	};
	@Override
	protected void init() {
		m_context = this;
	}
	@Override
	protected void initView() {
		setContentView(R.layout.activity_write2xml);
		m_btnLogin = (Button) findViewById(R.id.btn_login);
	}
	@Override
	protected void initData() 
	{
		alwaysShowByHanlder();
	}
	private void alwaysShowByHanlder()
	{
		m_hanlder.sendEmptyMessage(WHAT_OK);
	}
	private void alwaysShowByThread(){
		while (count<=10) {
			new Thread() {
				public void run() {
					Looper.prepare();
//					Toast.makeText(m_context, "正在定位。。。", 0).show();
					count++;
					try {
						Thread.sleep(2000);
						m_btnLogin.setText("登录" + count);
					} catch (Exception e) {
						showToast("出错啦");
					}
					Looper.loop();
				};
			}.start();
		}
	}
	@Override
	protected void initListener() {
		m_btnLogin.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:
			test();
		default:
			break;
		}
	}
	private void test()
	{
		showToast("测试重置");
		m_btnLogin.setText("测试");
		count=0;
		if(m_hanlder!=null)
		{
			m_hanlder.removeMessages(WHAT_OK);
		}
		initData();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(m_hanlder!=null)
		{
			m_hanlder.removeMessages(WHAT_OK);
		}
	}

}
