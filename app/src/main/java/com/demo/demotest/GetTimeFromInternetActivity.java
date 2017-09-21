package com.demo.demotest;

import java.net.URL;
import java.net.URLConnection;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.demo.demotest.R;
import com.demo.demotest.base.BaseActivity;
import com.demo.demotest.util.DateUtil;

/**
 * 
 * 项目名称: DemoBaseTest<br/>
 * 类名称: GetTimeFromInternetActivity<br/>
 * 描述: 获取网络时间
 *
 * @author:yefx
 * @Date:2015-8-17下午1:56:12
 */
public class GetTimeFromInternetActivity extends BaseActivity implements OnClickListener{

	private Button m_btnGetTime;
	private TextView m_tvTime;
	private Handler m_handler=new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			m_tvTime.setText("当前网络时间:"+DateUtil.getAsFormat1FromSecond((Long)msg.obj)+"\n("+msg.obj+")");
			;
		}
	};
	@Override
	protected void init() {
		setContentView(R.layout.activity_get_time_from_internet);
		setCommonTitleTitle("获取网络时间");
	}

	@Override
	protected void initView() {
		m_btnGetTime = (Button) findViewById(R.id.btn_get_time_internet);
		m_tvTime = (TextView) findViewById(R.id.tv_time_internet);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initListener() {
		m_btnGetTime.setOnClickListener(this);
		setCommonTitleBackListener();
	}
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.btn_get_time_internet:
			
			new Thread()
			{
				public void run()
				{
					long time=getTime();
					Message msg=Message.obtain();
					msg.obj=time;
					m_handler.sendMessage(msg);
				}
			}.start();
			break;

		default:
			break;
		}
		
	}
	/**
	 * 
	 * 功能:获取网络时间戳( 单位：秒)
	 * @return 
	 * @author: yefx
	 * @date:2015-8-17下午1:58:05
	 */
	private long getTime() {
		long time=-1;
		try {
			URL url=new URL("http://www.baidu.com");//取得资源对象
		    URLConnection uc=url.openConnection();//生成连接对象
//		    uc.setConnectTimeout(5000);/
		    uc.connect(); //发出连接
		    time=uc.getDate(); //取得网站日期时间
		    time=time/1000;
		} catch (Exception e) {
			throw new RuntimeException(e);
//			return -1;
		}
//	       Date date=new Date(ld); //转换为标准时间对象
		return time;
		
	}

}
