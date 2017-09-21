package com.demo.demotest;

import java.util.ArrayList;
import java.util.List;

import android.widget.GridView;

import com.demo.demotest.R;
import com.demo.demotest.adapter.GridViewItemHeightAdapter;
import com.demo.demotest.base.BaseActivity;
/**
 * 测试GridView的item中布局显示问题
 * @author Administrator
 *
 */
public class GridViewItemHeightActivity extends BaseActivity {
	
	private GridView m_gridview;
	private List<String> m_dataList;
	private GridViewItemHeightAdapter m_adapter;

	@Override
	protected void init() {
		setContentView(R.layout.activity_gridview_item_height);
	}

	@Override
	protected void initView() {
		m_gridview = (GridView) findViewById(R.id.gridview_gridview_item_height);
	}

	@Override
	protected void initData() {
		m_dataList = new ArrayList<String>();
		for(int i=0;i<3;i++)
		{
			m_dataList.add("说明:item"+i);
		}
		m_adapter = new GridViewItemHeightAdapter(m_context, m_dataList);
		m_gridview.setAdapter(m_adapter);
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub

	}

}
