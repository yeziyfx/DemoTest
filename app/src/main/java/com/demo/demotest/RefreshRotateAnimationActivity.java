package com.demo.demotest;

import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.demo.demotest.R;
import com.demo.demotest.base.BaseActivity;

/**
 * 
 * 项目名称: DemoBaseTest<br/>
 * 类名称: RefreshRotateAnimationActivity<br/>
 * 描述: 刷新旋转动画
 *
 * @author:yefx
 * @Date:2015-8-28下午1:30:27
 */
public class RefreshRotateAnimationActivity extends BaseActivity implements OnClickListener{
	
	private Button m_btnStart;
	private Button m_btnStop;
	private Animation m_rotateAni;
	private ImageView m_ivRefresh;
	private AnimationDrawable m_animDrawable;
	private int m_animMode=1;
	@Override
	protected void init() {
		setContentView(R.layout.activity_refresh_rotate_animation);

	}

	@Override
	protected void initView() {
		m_btnStart = (Button) findViewById(R.id.btn_start_refresh);
		m_btnStop = (Button) findViewById(R.id.btn_stop_refresh);
		m_ivRefresh = (ImageView) findViewById(R.id.iv_refresh_rotate_animation);
		m_ivRefresh.setBackgroundResource(R.drawable.icon_connecting);

	}

	@Override
	protected void initData() {
		setCommonTitleTitle("刷新动画");

	}

	@Override
	protected void initListener() {
		m_btnStart.setOnClickListener(this);
		m_btnStop.setOnClickListener(this);
		setCommonTitleBackListener();
	}
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.btn_start_refresh:
			startRefresh();
//			startFrameRefresh();
			break;
		case R.id.btn_stop_refresh:
			stopRefresh();
//			stopFrameRefresh();
			break;

		default:
			break;
		}
	}
	private void startRefresh()
	{
		showToast("开始刷新");
		m_rotateAni = AnimationUtils.loadAnimation(m_context, R.anim.rotate_loading);
		//这两句是不行的
//		m_ivRefresh.setAnimation(m_rotateAni);
//		m_rotateAni.start();
		m_ivRefresh.startAnimation(m_rotateAni);
	}
	private void stopRefresh()
	{
		showToast("停止刷新");
//		m_ivRefresh.clearAnimation();
		m_rotateAni.cancel();
	}
	private void startFrameRefresh()
	{
		m_ivRefresh.setBackgroundResource(R.drawable.frame_refresh);
		m_animDrawable=(AnimationDrawable) m_ivRefresh.getBackground();
		m_animDrawable.start();
	}
	private void stopFrameRefresh()
	{
		m_animDrawable.stop();
	}
}
