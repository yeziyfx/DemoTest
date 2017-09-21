package com.demo.demotest;

import java.util.ArrayList;
import java.util.List;

import android.widget.ListView;

import com.demo.demotest.adapter.ListViewEditTextAdpater;
import com.demo.demotest.base.BaseActivity;


public class ListViewEditTextActivity extends BaseActivity {
	
	private ListView m_lv;
	private List<String> m_dataList;
	private ListViewEditTextAdpater m_adapter;

	@Override
	protected void init() {
		setContentView(R.layout.activity_listview_edittext);

	}

	@Override
	protected void initView() {
		m_lv = (ListView) findViewById(R.id.listview_listview_edittext);

	}

	@Override
	protected void initData() {
		m_dataList=new ArrayList<String>();
		m_adapter = new ListViewEditTextAdpater(m_context, m_dataList);
		m_lv.setAdapter(m_adapter);
		setData();
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub

	}
	private void setData()
	{
		for(int i=0;i<30;i++)
		{
			m_dataList.add(i+"");
		}
		notifyDataChanged();
	}
	private void notifyDataChanged()
	{
		m_adapter.notifyDataSetChanged();
	}

}
