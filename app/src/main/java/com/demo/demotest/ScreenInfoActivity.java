package com.demo.demotest;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.demo.demotest.R;
import com.demo.demotest.base.BaseActivity;

/**
 * 
 * 项目名称: DemoBaseTest<br/>
 * 类名称: ScreenInfoActivity<br/>
 * 描述:屏幕信息 
 *
 * @author:yefx
 * @Date:2015-8-17下午1:27:56
 */
public class ScreenInfoActivity extends BaseActivity implements OnClickListener{
	private TextView tv_width;
	private TextView tv_height;
	private TextView tv_density;
	private TextView tv_densityDpi;
	
	private Button btn_getValue;
	private DisplayMetrics metric;
	
	// 屏幕宽度（像素）
	private int width;
	// 屏幕高度（像素）
	private int height;
	// 屏幕密度（0.75，1.0，1.5）
	private float density;
	// 屏幕密度dpi（120/160/240)
	private int densityDpi;
	/**
	 * 获取相关值
	 */
	private void getValue() {

		width = metric.widthPixels;

		height = metric.heightPixels;

		density = metric.density;

		densityDpi = metric.densityDpi;

		
	}

	@Override
	protected void init() {
		setContentView(R.layout.activity_screen_info);
	}

	@Override
	protected void initView() {
		tv_width = (TextView) findViewById(R.id.tv_width);
		tv_height = (TextView) findViewById(R.id.tv_height);
		tv_density = (TextView) findViewById(R.id.tv_density);
		tv_densityDpi=(TextView) findViewById(R.id.tv_densityDpi);
		
		btn_getValue = (Button) findViewById(R.id.btn_getValue);
	}

	@Override
	protected void initData() {
		metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		getValue();
	}

	@Override
	protected void initListener() {
		btn_getValue.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tv_width.setText("屏幕宽度:" + width + "px");
				tv_height.setText("屏幕高度:" + height + "px");
				tv_density.setText("屏幕密度:" + density);
				tv_densityDpi.setText("屏幕密度dpi："+densityDpi);
			}
		});
		
	}
	@Override
	public void onClick(View v) {
		
	}
}