package com.demo.demotest.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.demotest.R;
import com.demo.demotest.base.BaseCommontAdapter;
import com.demo.demotest.util.LogUtil;

public class GridViewItemHeightAdapter extends BaseCommontAdapter {
	private boolean m_flag=true;
	public GridViewItemHeightAdapter(Context context, List dataList) {
		super(context, dataList);
	}

	@Override
	public View getItemView(Context context, int position, View converntView,ViewGroup parent,
			List dataList) {
		ViewHolder vh;
		if(converntView==null)
		{
			converntView=View.inflate(context, R.layout.item_gridview_gridview_item_height, null);
			vh=new ViewHolder();
			vh.iv=(ImageView) converntView.findViewById(R.id.iv_item_gridview_item_height);
			vh.ivType=(ImageView) converntView.findViewById(R.id.iv_type_item_gridview_item_height);
			vh.tv=(TextView) converntView.findViewById(R.id.tv_item_gridview_item_height);
			converntView.setTag(vh);
		}
		else
		{
			vh=(ViewHolder) converntView.getTag();
		}
		if(position==0)
		{
			if(m_flag)
			{
				m_flag=false;
			}
			else
			{
				return converntView;
			}
		}
		LogUtil.i("item", "item"+position);
		return converntView;
	}
	class ViewHolder
	{
		ImageView iv;
		ImageView ivType;
		TextView tv;
	}

}
