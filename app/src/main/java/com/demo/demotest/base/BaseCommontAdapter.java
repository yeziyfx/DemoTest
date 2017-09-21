package com.demo.demotest.base;

import java.util.List;

import com.demo.demotest.R;
import com.demo.demotest.util.LogSuperUtil;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 
 * 项目名称: DemoBaseTest<br/>
 * 类名称: BaseCommontAdapter<br/>
 * 描述: BaseAdapter的进一步封装类，只需要实现getItemView方法即可
 *
 * @author:yefx
 * @Date:2015-5-18上午9:56:03
 */
public abstract class BaseCommontAdapter<T> extends BaseAdapter {
	protected Context m_context;
	protected List<T> m_dataList;
	public BaseCommontAdapter(Context context,List<T> dataList)
	{
		m_context=context;
		m_dataList=dataList;
	}
	@Override
	public int getCount() {
		return m_dataList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		return getItemView(m_context,arg0,arg1,arg2,m_dataList);
	}
	public abstract View getItemView(Context context,int position,View converntView,ViewGroup parent,List<T> dataList);
}
