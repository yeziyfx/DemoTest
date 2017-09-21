package com.demo.demotest.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.demo.demotest.R;
import com.demo.demotest.base.BaseCommontAdapter;
import com.demo.demotest.domain.HTMLParseEntity;
import com.demo.demotest.domain.ImgEntity;
import com.demo.demotest.util.LogSuperUtil;
import com.demo.demotest.util.SysConstant;
/**
 * 
 * 项目名称: DemoBaseTest<br/>
 * 类名称: HTMLParseAdapter<br/>
 * 描述: HTML解析的ListView的适配器
 *
 * @author:yefx
 * @Date:2016-2-1下午4:37:43
 */
public class HTMLParseAdapter extends BaseAdapter {
	private Context m_context;
	private List<ImgEntity> m_dataList;
	public HTMLParseAdapter(Context context, List<ImgEntity> dataList) {
		m_context=context;
		m_dataList=dataList;
	}


	class ViewHolder {

		public TextView tvTitle;
		public TextView tvContent;
	}

	@Override
	public int getCount() {
		LogSuperUtil.i(SysConstant.Log.element, "m_dataList.size="+m_dataList.size());
		return m_dataList.size();
	}

	@Override
	public Object getItem(int position) {
		return m_dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LogSuperUtil.i(SysConstant.Log.element, "position="+position);
		ImgEntity entity = m_dataList.get(position);
		ViewHolder vh;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = View.inflate(m_context, R.layout.item_listview_html_parse, null);
			vh.tvTitle = (TextView) convertView.findViewById(R.id.tv_title_item_html_parse);
			vh.tvContent = (TextView) convertView.findViewById(R.id.tv_content_item_html_parse);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.tvTitle.setText(entity.productid== null ? "" : entity.productid);
		vh.tvContent.setText(entity.imageName==null?"":entity.imageName);
		return convertView;
	}
}
