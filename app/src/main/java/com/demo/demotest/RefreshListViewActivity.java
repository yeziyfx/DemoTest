package com.demo.demotest;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.demo.demotest.base.BaseActivity;
import com.demo.demotest.view.RefreshListView;
import com.demo.demotest.view.RefreshListView.OnRefreshListener;

/**
 * 
 * 项目名称: DemoBaseTest<br/>
 * 类名称: RefreshListViewActivity<br/>
 * 描述: 下拉刷新示例
 *
 * @author:yefx
 * @Date:2016-1-7下午5:07:25
 */
public class RefreshListViewActivity extends BaseActivity implements OnRefreshListener{

	private RefreshListView rListView;
	private List<String> textList;
	private MyAdapter adapter;
	private int indexHeader=0;
	private int indexFooter=0;
	@Override
	protected void init() {
		setContentView(R.layout.activity_refresh_listview);
	}

	@Override
	protected void initView() {
		rListView = (RefreshListView) findViewById(R.id.refreshlistview);
		setCommonTitleTitle("下拉刷新");
	}

	@Override
	protected void initData() {
		textList = new ArrayList<String>();
		for(int i=0;i<50;i++)
		{
			textList.add("屋檐如悬崖，风铃如沧海。一身琉璃白，透明着尘埃。这是一条ListView的数据"+i);
		}
		adapter=new MyAdapter();
		rListView.setAdapter(adapter);
		rListView.setOnRefreshListener(this);

	}

	@Override
	protected void initListener() {
		setCommonTitleBackListener();

	}
	private class MyAdapter extends BaseAdapter
	{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return textList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder vh;
			if(convertView==null)
			{
				vh=new ViewHolder();
				convertView=View.inflate(m_context, R.layout.item_listview_refresh, null);
				vh.tv=(TextView) convertView.findViewById(R.id.tv_item_refresh);
				convertView.setTag(vh);
			}
			else
			{
				vh=(ViewHolder) convertView.getTag();
			}
			vh.tv.setText(textList.get(position));
			vh.tv.setSelected(true);
			return convertView;
		}
		
	}
	class ViewHolder
	{
		public TextView tv;
	}
	@Override
	public void onDownPullRefresh() {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				SystemClock.sleep(2000);
				for(int i=indexHeader;i<2+indexHeader;i++)
				{
					textList.add(0,"这是下拉刷新出来的数据"+i);
				}
				indexHeader=indexHeader+2;
				return null;
			}
			@Override
			protected void onPostExecute(Void result) {
				adapter.notifyDataSetChanged();
				rListView.hideHeaderView();
			}
		}.execute(new Void[]{});
		
	}

	@Override
	public void onLoadingMore() {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				SystemClock.sleep(3000);
				for(int i=indexFooter;i<2+indexFooter;i++)
				{
					textList.add("这是加载更多出来的数据"+i);
				}
				indexFooter=indexFooter+2;
				return null;
			}
			@Override
			protected void onPostExecute(Void result) {
				adapter.notifyDataSetChanged();
				rListView.hideFooterView();
			}
		}.execute(new Void[]{});
		
	}
}
