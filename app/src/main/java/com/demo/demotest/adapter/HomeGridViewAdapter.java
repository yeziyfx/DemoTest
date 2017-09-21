package com.demo.demotest.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.demotest.R;
import com.demo.demotest.base.BaseCommontAdapter;

public class HomeGridViewAdapter<T> extends BaseCommontAdapter<T> {
	public HomeGridViewAdapter(Context context, List<T> dataList) {
		super(context, dataList);
	}
	@Override
	public View getItemView(Context context, int position,View converntView,ViewGroup parent, List<T> dataList) {
		ViewHolder vh;
		if(converntView==null)
		{
			vh=new ViewHolder();
			converntView=View.inflate(context, R.layout.layout_item_gridview_home, null);
			vh.tv=(TextView) converntView.findViewById(R.id.tv_item);
			converntView.setTag(vh);
		}
		else
		{
			vh=(ViewHolder) converntView.getTag();
		}
		vh.tv.setText(dataList.get(position)+"");
		return converntView;
	}

	class ViewHolder
	{
		TextView tv;
	}
}
