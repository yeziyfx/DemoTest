package com.demo.demotest.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.demotest.R;
import com.demo.demotest.base.BaseCommontAdapter;

/**
 * 
 * 项目名称:DemoBaseTest<br/>
 * 类名称:ListViewFragmentAdapter<br/>
 * 描述:Fragment中含有的ListView的适配器
 *
 * @author:yfx
 * @Date:2016-3-23下午8:05:48
 */
public class ListViewFragmentAdapter extends BaseCommontAdapter<String> {

	public ListViewFragmentAdapter(Context context, List<String> dataList) {
		super(context, dataList);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getItemView(Context context, int position, View converntView, ViewGroup parent, List<String> dataList) {
		String content=m_dataList.get(position);
		ViewHolder vh;
		if(converntView==null)
		{
			vh=new ViewHolder();
			converntView=View.inflate(context, R.layout.item_listview_fragment, null);
			vh.tv=(TextView) converntView.findViewById(R.id.tv_item_first_fragment);
			converntView.setTag(vh);
		}
		else
		{
			vh=(ViewHolder) converntView.getTag();
		}
		vh.tv.setText(content==null?"":content);
		return converntView;
	}
	class ViewHolder
	{
		public TextView tv;
	}
}
