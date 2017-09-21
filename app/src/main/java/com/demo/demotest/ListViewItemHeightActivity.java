package com.demo.demotest;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.demo.demotest.R;
import com.demo.demotest.adapter.ListViewItemHeightAdapter;
import com.demo.demotest.base.BaseActivity;

/**
 * ListView的item高度指定
 * 
 * @author Administrator
 * 
 */
public class ListViewItemHeightActivity extends BaseActivity implements OnClickListener{

	private ListView m_lv;
	private List<String> m_dataList;
	private ListViewItemHeightAdapter<String> m_lvAdapter;
	@Override
	protected void init() {
		setContentView(R.layout.activity_listview_item_height);
	}

	@Override
	protected void initView() {
		m_lv = (ListView) findViewById(R.id.lv_listview_item_height);
		setCommonTitleTitle("item高度");
	}

	@Override
	protected void initData() {
		m_dataList = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			m_dataList.add("item" + (i + 1));
		}
		m_lvAdapter = new ListViewItemHeightAdapter<String>(m_context, m_dataList);
		m_lv.setAdapter(m_lvAdapter);
	}

	@Override
	protected void initListener() {
		setCommonTitleBackListener();
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		default:
			break;
		}
	}

}
