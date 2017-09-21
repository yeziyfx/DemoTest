package com.demo.demotest.adapter;

import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.demotest.R;
import com.demo.demotest.base.BaseCommontAdapter;
import com.demo.demotest.domain.RedEnvelopeEntity;

/**
 * 
 * 项目名称: DemoBaseTest<br/>
 * 类名称: ListViewScrollAdapter<br/>
 * 描述: ListView自动滚动界面的适配器
 *
 * @author:yefx
 * @Date:2016-1-12下午4:22:46
 */
public class ListViewScrollAdapter extends BaseCommontAdapter<RedEnvelopeEntity> {

	public ListViewScrollAdapter(Context context, List<RedEnvelopeEntity> dataList) {
		super(context, dataList);
		// TODO Auto-generated constructor stub
	}
	@Override
	public int getCount() {
		if(m_dataList.size()==0)
		{
			return 0;
		}
		return Integer.MAX_VALUE;
	}
	@Override
	public View getItemView(Context context, int position, View converntView, ViewGroup parent, List<RedEnvelopeEntity> dataList) {
		RedEnvelopeEntity entity=dataList.get(position%dataList.size());
		ViewHolder vh;
		if(converntView==null)
		{
			vh=new ViewHolder();
			converntView=View.inflate(context, R.layout.item_listview_scroll, null);
			vh.tvOwner=(TextView) converntView.findViewById(R.id.tv_owner_item_scroll);
			vh.tvMoney=(TextView) converntView.findViewById(R.id.tv_money_item_scroll);
			converntView.setTag(vh);
		}
		else
		{
			vh=(ViewHolder) converntView.getTag();
		}
		vh.tvOwner.setText(entity.owner==null?"":entity.owner);
		vh.tvMoney.setText(entity.money+"");
		return converntView;
	}
	class ViewHolder
	{
		TextView tvOwner;
		TextView tvMoney;
	}
}
