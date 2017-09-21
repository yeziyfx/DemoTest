package com.demo.demotest;

import java.net.InetAddress;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.demo.demotest.base.BaseActivity;
import com.demo.demotest.util.ExceptionUtil;

/**
 * 
 * 项目名称: DemoBaseTest<br/>
 * 类名称: InternetAvailableActivity<br/>
 * 描述: 网络连通可用性
 * 
 * @author:yefx
 * @Date:2016-1-18上午10:28:30
 */
public class InternetAvailableActivity extends BaseActivity implements OnClickListener {

	private TextView m_tvAvailable;
	private Button m_btnCheck;
	private boolean m_bIsChecking=false;
	private Handler m_handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if ((Boolean)msg.obj) {
				m_tvAvailable.setText("网络可用，请任性的上网");
			} else {
				m_tvAvailable.setText("当前网络不可用哦");
			}
			m_bIsChecking=false;
		}
	};

	@Override
	protected void init() {
		setContentView(R.layout.activity_internet_available);

	}

	@Override
	protected void initView() {
		setCommonTitleTitle("检测网络可用性");
		m_tvAvailable = (TextView) findViewById(R.id.tv_internet_available);
		m_btnCheck = (Button) findViewById(R.id.btn_check_available);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initListener() {
		setCommonTitleBackListener();
		m_btnCheck.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_check_available:
			if(m_bIsChecking)
			{
				m_tvAvailable.setText("正在检测网络可用性，请稍等。。。");
				return;
			}
			new Thread() {

				public void run() {
					m_bIsChecking=true;
					checkAvailable();
//					checkInternetAccess();
				}
			}.start();
			
			break;

		default:
			break;
		}

	}

	private void checkAvailable() {

		Process process = null;
		Runtime runTime = Runtime.getRuntime();
		Message msg=Message.obtain();
		try {
//			 runTime.exec("cmd.exe");
			process = runTime.exec("ping -n 1 -w 1000 www.baidu.com");
			int result = process.waitFor();
			msg.obj=isAvailable(result);
			m_handler.sendMessage(msg);
			return;
		} catch (Exception e) {
			ExceptionUtil.handleRuntimeException(e);
		}
		msg.obj=false;
		m_handler.sendMessage(msg);
	}

	private boolean isAvailable(int result) {
		if (result == 0) {
			return true;
		} else {
			return false;
		}
	}
	private void checkInternetAccess() {
        boolean hasInternetAccess = false;
        Message msg=Message.obtain();
        try {
                //I set google but you can try anything "reliable"...
                //isReachable(1) the timeout in seconds
            hasInternetAccess = InetAddress.getByName("www.baidu.com").isReachable(1000);                         
            msg.obj=hasInternetAccess;
            m_handler.sendMessage(msg);
            return;
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionUtil.handleRuntimeException(e);
        }
        msg.obj=false;
        m_handler.sendMessage(msg);
    }
}
