package com.demo.demotest.adapter;

import java.util.List;

import com.demo.demotest.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewItemHeightAdapter<T> extends BaseAdapter {
	private Context m_context;
	private List<T> m_dataList;
	public ListViewItemHeightAdapter(Context context,List<T> dataList)
	{
		this.m_context=context;
		this.m_dataList=dataList;
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
		ViewHolder vh;
		if(arg1==null)
		{
			vh=new ViewHolder();
			arg1=View.inflate(m_context, R.layout.layout_item_listview_height, null);
			vh.iv=(ImageView) arg1.findViewById(R.id.iv_item);
			vh.tvPicName=(TextView) arg1.findViewById(R.id.tv_pic_name);
			vh.tvPicDesc=(TextView) arg1.findViewById(R.id.tv_pic_desc);
			vh.btnDel=(Button) arg1.findViewById(R.id.btn_delete);
			arg1.setTag(vh);
		}
		else
		{
			vh=(ViewHolder) arg1.getTag();
		}
		return arg1;
	}
	class ViewHolder
	{
		ImageView iv;
		TextView tvPicName;
		TextView tvPicDesc;
		Button btnDel;
	}

}
