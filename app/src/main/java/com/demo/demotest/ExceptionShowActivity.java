package com.demo.demotest;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.demotest.R;
import com.demo.demotest.base.BaseActivity;
import com.demo.demotest.util.Constants;
import com.demo.demotest.util.ToastUtil;

public class ExceptionShowActivity extends BaseActivity implements OnClickListener{
	private final String TAG="demo ExceptionShowActivity";
	private boolean m_bIsLogOpen=true;
	private Context m_context;
	private ImageView m_btnMore;
	private TextView m_tvTitle;
	private TextView m_tvErrorMsg;
	//
	private String m_strMessage;
	@Override
	public void init() {
		m_context=this;
		setContentView(R.layout.activity_error_show);
		Intent i=getIntent();
		if(i!=null)
		{
			m_strMessage=i.getStringExtra(Constants.Crash.KEY_ERROR);
			if(m_strMessage==null)
			{
				m_strMessage="未传过来错误日志";
			}
		}
	}

	@Override
	public void initView() {
		m_btnMore=(ImageView) findViewById(R.id.iv_more_title_bar_common);
		m_tvErrorMsg = (TextView) findViewById(R.id.tv_error_message);
	}

	@Override
	public void initData() {
		m_tvTitle.setText("错误日志展示：");
		m_tvErrorMsg.setText(m_strMessage);
	}

	@Override
	public void initListener() {
		m_btnMore.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		default:
			break;
		}
	}

}
