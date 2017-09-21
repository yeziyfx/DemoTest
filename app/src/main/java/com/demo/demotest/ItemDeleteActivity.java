package com.demo.demotest;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.demo.demotest.base.BaseActivity;
import com.demo.demotest.view.MyListView;
import com.demo.demotest.view.MyListView.OnItemDeleteListener;

/**
 * 
 * 项目名称: DemoBaseTest<br/>
 * 类名称: ItemDeleteActivity<br/>
 * 描述: 条目滑动删除
 *
 * @author:yefx
 * @Date:2016-1-11上午11:32:17
 */
public class ItemDeleteActivity extends BaseActivity {
	private MyListView myListView;
	private List<String> contentList=new LinkedList<String>();
	private MyAdapter adapter;
	@Override
	protected void init() {
		setContentView(R.layout.activity_item_delete);

	}

	@Override
	protected void initView() {
		myListView=(MyListView) findViewById(R.id.listview_item_delete);
		setCommonTitleTitle("条目滑动删除");
	}

	@Override
	protected void initData() {
		setData();
		adapter=new MyAdapter(m_context);
		myListView.setAdapter(adapter);

	}

	@Override
	protected void initListener() {
		setCommonTitleBackListener();
		myListView.setOnItemDeleteListener(new OnItemDeleteListener() {
			
			@Override
			public void onItemDelete(int selectedItem) {
				contentList.remove(selectedItem);
				adapter.notifyDataSetChanged();
				
			}
		});

	}
	private void setData()
	{
		for(int i=0;i<50;i++)
		{
			contentList.add("item"+i);
		}
	}
	class MyAdapter extends BaseAdapter
	{
		private Context context;
		public MyAdapter(Context context)
		{
			this.context=context;
		}
		@Override
		public int getCount() {
			return contentList.size();
		}

		@Override
		public Object getItem(int position) {
			return contentList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView==null)
			{
				convertView=View.inflate(m_context, R.layout.item_listview_item_delete, null);
			}
			TextView tv=(TextView) convertView.findViewById(R.id.tv_item_delete);
			tv.setText(contentList.get(position));
			return convertView;
		}
		
	}
}
