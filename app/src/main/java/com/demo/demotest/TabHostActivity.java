package com.demo.demotest;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.TabWidget;
import android.widget.TextView;

import com.demo.demotest.base.BaseActivity;
import com.demo.demotest.fragment.AlwaysContactsFragment;
import com.demo.demotest.fragment.MyContactsFragment;
import com.demo.demotest.fragment.StrangersFragment;

public class TabHostActivity extends FragmentActivity {

	private FragmentTabHost mTabHost = null;;
	private View mViewIndicator = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_tab_host);

		mTabHost = (FragmentTabHost) findViewById(R.id.mytabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.mytabcontent);

		// 添加tab名称和图标
		mViewIndicator = getIndicatorView("我的联系人", R.layout.indicator_my_contact);
		mTabHost.addTab(mTabHost.newTabSpec("myContact").setIndicator(mViewIndicator), MyContactsFragment.class, null);

		mViewIndicator = getIndicatorView("陌生人", R.layout.indicator_strangers);
		mTabHost.addTab(mTabHost.newTabSpec("stranger").setIndicator(mViewIndicator), StrangersFragment.class, null);

		mViewIndicator = getIndicatorView("常联系人", R.layout.indicator_alwayscontact);
		mTabHost.addTab(mTabHost.newTabSpec("alwaysContact").setIndicator(mViewIndicator), AlwaysContactsFragment.class, null);
	}

	private View getIndicatorView(String name, int layoutId) {
		View v = getLayoutInflater().inflate(layoutId, null);
		TextView tv = (TextView) v.findViewById(R.id.tabText);
		tv.setText(name);
		return v;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mTabHost = null;
	}

}
