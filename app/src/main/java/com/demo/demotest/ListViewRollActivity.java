package com.demo.demotest;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.demo.demotest.adapter.ListViewScrollAdapter;
import com.demo.demotest.base.BaseActivity;
import com.demo.demotest.domain.RedEnvelopeEntity;
import com.demo.demotest.util.LogSuperUtil;
import com.demo.demotest.util.ScreenInfoUtil;
import com.demo.demotest.util.SysConstant;

/**
 * 
 * 项目名称: DemoBaseTest<br/>
 * 类名称: ListViewRollActivity<br/>
 * 描述: ListView 自动滚动
 * 
 * @author:yefx
 * @Date:2016-1-12下午4:02:11
 */
public class ListViewRollActivity extends BaseActivity implements OnClickListener {

	private ListView m_lv;
	private List<RedEnvelopeEntity> m_dataList;
	private ListViewScrollAdapter m_adapter;
	private Timer m_autoUpdateTimer;
	private int m_index;
	private Button m_btnGetRedEnvelope;
	private RelativeLayout m_relaBottom;
	private List<String> m_nameList;
	private ImageView m_ivRedEnvelope;
	private List<String> m_ownerNameList;

	@Override
	protected void init() {
		setContentView(R.layout.activity_listview_scroll);

	}

	@Override
	protected void initView() {
		setCommonTitleTitle("红包");
		m_lv = (ListView) findViewById(R.id.listview_scroll);
		m_ivRedEnvelope = (ImageView) findViewById(R.id.iv_red_envelope_listview_roll);
		m_relaBottom = (RelativeLayout) findViewById(R.id.rela_bottom_listview_roll);
		m_btnGetRedEnvelope = (Button) findViewById(R.id.btn_get_red_envelope_listview_roll);
	}

	@Override
	protected void initData() {
		m_dataList = new ArrayList<RedEnvelopeEntity>();
		m_adapter = new ListViewScrollAdapter(m_context, m_dataList);
		m_lv.setAdapter(m_adapter);
		m_nameList = new ArrayList<String>();
		m_ownerNameList = new ArrayList<String>();
		fillNameData();
		m_autoUpdateTimer = new Timer();
		m_autoUpdateTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				m_lv.smoothScrollBy(2, 10);
				m_index++;
			}
		}, 0, 10);
	}

	@Override
	protected void initListener() {
		setCommonTitleBackListener();
		setCommonTitleRightListener("抢红包", new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (m_relaBottom.getVisibility() == View.VISIBLE) {
					showToast("已经可以开抢了");
					return;
				}
				setBottomVisible(true);

			}
		});
		m_btnGetRedEnvelope.setOnClickListener(this);
		m_ivRedEnvelope.setOnClickListener(this);
	}

	private void fillNameData() {
		String[] nameArray = getResources().getStringArray(R.array.name_array);
		m_nameList.addAll(Arrays.asList(nameArray));
	}

	private void setBottomVisible(boolean isVisible) {
		if (isVisible) {
			m_relaBottom.setVisibility(View.VISIBLE);

			m_relaBottom.startAnimation(AnimationUtils.loadAnimation(this, R.anim.push_bottom_out));
		} else {

			m_relaBottom.setVisibility(View.GONE);

			m_relaBottom.startAnimation(AnimationUtils.loadAnimation(this, R.anim.push_bottom_in));
		}
	}

	@SuppressLint("NewApi")
	private void fallView(final float p1, final float p2) {
		ObjectAnimator animation = ObjectAnimator.ofFloat(m_ivRedEnvelope, "Y", p1, p2);
		animation.setInterpolator(new OvershootInterpolator());
		animation.setDuration(15000);
		animation.setStartDelay(10);
		animation.end();
		animation.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
				m_ivRedEnvelope.setVisibility(View.VISIBLE);

			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				int left = m_ivRedEnvelope.getLeft() + (int) (p2 - p1);
				int top = m_ivRedEnvelope.getTop();
				int width = m_ivRedEnvelope.getWidth();
				int height = m_ivRedEnvelope.getHeight();
				m_ivRedEnvelope.setVisibility(View.GONE);
				// m_ivRedEnvelope.layout(left, top, left+width, top+height);

			}

			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub

			}
		});
		animation.start();

	}

	private void notifyDataChanged() {
		m_adapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_get_red_envelope_listview_roll:
			//
			ScreenInfoUtil screenUtil = new ScreenInfoUtil(m_context);
			int height = screenUtil.getHeight();
			int width = screenUtil.getWidth();
			fallView(0, height);
			break;
		case R.id.iv_red_envelope_listview_roll:
			getRedEnvelope();
			break;

		default:
			break;
		}

	}

	private void getRedEnvelope() {
		RedEnvelopeEntity entity;

		String owner = m_nameList.get(new Random().nextInt(m_nameList.size()));
		double money = new Random().nextInt(50) / 3.00;
		DecimalFormat df = new DecimalFormat("0.00");
		money=new BigDecimal(df.format(money)).doubleValue();
		m_ivRedEnvelope.clearAnimation();
		if (m_ownerNameList.contains(owner)) {
			if (money > 0) {
				showToast(owner + "又抢到了" + money + "元");
			} else {
				showToast(owner + "又抢,不过啥都没抢到");
				return;
			}
			LogSuperUtil.i(SysConstant.Log.test_roll, owner + "又抢到了" + money);
			int index = m_ownerNameList.indexOf(owner);
			entity = m_dataList.get(index);
			BigDecimal b1 = new BigDecimal(Double.toString(money));
			BigDecimal b2 = new BigDecimal(Double.toString(entity.money));
			entity.money = b1.add(b2).doubleValue();
			LogSuperUtil.i(SysConstant.Log.test_roll, owner + "共有" + entity.money);
		} else {
			if (money > 0) {
				showToast("恭喜，" + owner + "抢到了" + money + "元红包");
			} else {
				showToast("好遗憾，" + owner + "一分钱都没抢到。。。");
				return;
			}
			entity = new RedEnvelopeEntity();
			entity.owner = owner;
			entity.money = money;
			LogSuperUtil.i(SysConstant.Log.test_roll, owner + "首抢" + entity.money);
			m_dataList.add(entity);
			m_ownerNameList.add(owner);
		}
		notifyDataChanged();
	}
}
