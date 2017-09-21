package com.demo.demotest.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.demotest.R;
import com.demo.demotest.base.BaseCommontAdapter;
import com.demo.demotest.domain.NewsEntity;

/**
 * 
 * 项目名称: DemoBaseTest<br/>
 * 类名称: PaginationListViewAdapter<br/>
 * 描述: 分页加载的ListView的适配器
 *
 * @author:yefx
 * @Date:2016-2-19下午3:40:01
 */
public class PaginationListViewAdapter extends BaseCommontAdapter<NewsEntity> {

	public PaginationListViewAdapter(Context context, List<NewsEntity> dataList) {
		super(context, dataList);
	}

	@Override
	public View getItemView(Context context, int position, View converntView, ViewGroup parent, List<NewsEntity> dataList) {
		NewsEntity newsEntity=dataList.get(position);
		ViewHolder vh;
		if(converntView==null)
		{
			vh=new ViewHolder();
			converntView=View.inflate(m_context, R.layout.item_listview_pagination, null);
			vh.tvTitle=(TextView) converntView.findViewById(R.id.tv_title_item_pagination);
			vh.tvContent=(TextView) converntView.findViewById(R.id.tv_content_item_pagination);
			converntView.setTag(vh);
		}
		else
		{
			vh=(ViewHolder) converntView.getTag();
		}
		vh.tvTitle.setText(newsEntity.title==null?"":newsEntity.title);
		vh.tvContent.setText(newsEntity.content==null?"":newsEntity.content);
		return converntView;
	}
	class ViewHolder
	{
		TextView tvTitle;
		TextView tvContent;
	}
}
