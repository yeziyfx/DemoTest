package com.demo.demotest.base;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.demotest.R;
import com.demo.demotest.util.ParseBaseTypeUtil;
import com.demo.demotest.util.ToastUtil;

public abstract class BaseActivity extends Activity{
	protected Context m_context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		m_context=this;
		init();
		
		initView();
		initData();
		initListener();
		
	}
	/**
	 * 
	 * 功能: 基本初始化操作
	 * @author: yefx
	 * @date:2015-3-31上午10:19:40
	 */
	protected abstract void init();
	protected abstract void initView();
	protected abstract void initData();
	protected abstract void initListener();
	public void toActivity(Class clazz)
	{
		Intent intent=new Intent(this,clazz);
		startActivity(intent);
	}
	/**
	 * 
	 * 功能:获取TextView 、EditText的字符串值
	 * 
	 * @param
	 * @return
	 * @author: yefx
	 * @date:2015-5-13上午11:06:01
	 */
	protected String getTextValue(TextView tv) {
		String text = "";
		if (tv != null) {
			text = tv.getText().toString().trim();
		}
		return text;
	}

	/**
	 * 
	 * 功能:将TextView 、EditText的字符串值以double类型的形式获取其值
	 * 
	 * @param tv
	 * @return
	 * @author: yefx
	 * @date:2015-5-13下午6:48:23
	 */
	protected double getTextDoubleValue(TextView tv) {
		return ParseBaseTypeUtil.parseToDouble(getTextValue(tv));
	}

	protected float getTextFloatValue(TextView tv) {
		return ParseBaseTypeUtil.parseToFloat(getTextValue(tv));
	}
	protected int getTextIntValue(TextView tv) {
		return ParseBaseTypeUtil.parseToInteger(getTextValue(tv));
	}
	protected void showToast(String toastMsg)
	{
		ToastUtil.show(m_context, toastMsg);
	}
	protected void setCommonTitleTitle(String title)
	{
		TextView tvTitle= (TextView) findViewById(R.id.tv_title_title_bar_common);
		tvTitle.setText(title==null?"":title);
	}
	protected void setCommonTitleBackListener()
	{
		ImageView ivBack= (ImageView) findViewById(R.id.iv_back_title_bar_common);
		ivBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	protected void setCommonTitleRightListener(String text,OnClickListener clickListener)
	{
		TextView tvRight=(TextView) findViewById(R.id.tv_right_common_title);
		tvRight.setVisibility(View.VISIBLE);
		tvRight.setText(text==null?"":text);
		tvRight.setOnClickListener(clickListener);
	}
}
