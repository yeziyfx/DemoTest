package com.demo.demotest.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.demo.demotest.R;
import com.demo.demotest.ViewPagerActivity;
import com.demo.demotest.adapter.ListViewFragmentAdapter;


public class Fragment1 extends Fragment {
	private ViewPagerActivity m_activity;
	private ListView m_lv;
	private List<String> m_dataList;
	private ListViewFragmentAdapter m_adapter;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		m_activity=(ViewPagerActivity) activity;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		View contentView= inflater.inflate(R.layout.fragment_1, container, false);
		View contentView= View.inflate(m_activity,R.layout.fragment_1, null);
		m_lv = (ListView) contentView.findViewById(R.id.lv_first_fragment);
		initData();
		return contentView;
	}
	private void initData()
	{
		m_dataList = new ArrayList<String>();
		for(int i=0;i<30;i++)
		{
			m_dataList.add("第"+(i+1)+"条数据");
		}
		m_adapter = new ListViewFragmentAdapter(m_activity, m_dataList);
		m_lv.setAdapter(m_adapter);
	}
}
