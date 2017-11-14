package com.demo.demotest.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.demotest.R;
import com.demo.demotest.ViewPagerActivity;
import com.demo.demotest.util.LogSuperUtil;

public class Fragment3 extends Fragment {
	private ViewPagerActivity m_activity;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		m_activity=(ViewPagerActivity) activity;
		LogSuperUtil.i("mychar","Fragment3--");
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contentView= View.inflate(m_activity, R.layout.fragment_3,null);
		return contentView;
	}
}
