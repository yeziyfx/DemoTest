package com.demo.demotest;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
	private TextView mTvTip;
	@Override
	protected void init() {
		setContentView(R.layout.activity_date_to_second);
		setCommonTitleTitle("日期与秒数转换");
	}
	
	

	@Override
	protected void initView() {
		m_etData = (EditText) findViewById(R.id.et_data_calendar_to_time);
		m_btnToDate=(Button) findViewById(R.id.btn_to_date);
		m_btnToTime=(Button) findViewById(R.id.btn_to_time);
		m_tvResult = (TextView) findViewById(R.id.tv_result);
		mTvTip = (TextView) findViewById(R.id.tv_tip_date_to_second);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initListener() {
		m_btnToDate.setOnClickListener(this);
		m_btnToTime.setOnClickListener(this);
		setCommonTitleBackListener();
		m_etData.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			@Override
			public void afterTextChanged(Editable s) {
				String text=s.toString().trim();
				if(!TextUtils.isEmpty(text))
				{
					int len=text.length();
					String str="已输入"+len+"位,";
					if(len<10)
					{
						mTvTip.setText(str+"请继续输入数字");
					}
					else if(len==10)
					{
						mTvTip.setText(str+"当前时间戳单位是秒");
					}
					else if(len==13){
						mTvTip.setText(str+"当前时间戳单位是毫秒");
					}
					else {
						mTvTip.setText(str);
					}
				}
				else {
					mTvTip.setText("");
				}

			}
		});
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
