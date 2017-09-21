package com.demo.demotest;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.demo.demotest.R;
import com.demo.demotest.base.BaseActivity;
import com.demo.demotest.util.DateUtil;


public class DateToSecondActivity extends BaseActivity implements OnClickListener{
	private Button m_btnToDate;
	private TextView m_tvResult;
	private Button m_btnToTime;
	private EditText m_etData;
	@Override
	protected void init() {
		setContentView(R.layout.activity_date_to_second);
		
	}
	
	

	@Override
	protected void initView() {
		m_etData = (EditText) findViewById(R.id.et_data_calendar_to_time);
		m_btnToDate=(Button) findViewById(R.id.btn_to_date);
		m_btnToTime=(Button) findViewById(R.id.btn_to_time);
		m_tvResult = (TextView) findViewById(R.id.tv_result);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initListener() {
		m_btnToDate.setOnClickListener(this);
		m_btnToTime.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.btn_to_date:
			toDate();
			break;
		case R.id.btn_to_time:
			toTime();
			break;

		default:
			break;
		}
	}
	private void toTime() {
		m_etData.setText(""+DateUtil.getTimeAsSecondFromFormat1(m_etData.getText().toString().trim()));
		
	}

	private void toDate() {
		m_etData.setText(DateUtil.getAsFormat1FromSecond(m_etData.getText().toString().trim()));
		
	}

	private void run()
	{
		long beginTime=DateUtil.convertToLong("2015-05-19 08:00:00");
		long endTime=DateUtil.convertToLong("2015-05-19 08:00:00");
		m_tvResult.setText("bt="+beginTime+"\net="+endTime);
	}

}
