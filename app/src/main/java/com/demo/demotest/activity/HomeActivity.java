package com.demo.demotest.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.demo.demotest.AlwaysToastActivity;
import com.demo.demotest.DateToSecondActivity;
import com.demo.demotest.DemicalActivity;
import com.demo.demotest.EventActivity;
import com.demo.demotest.ExceptionActivity;
import com.demo.demotest.ExpandableListViewActivity;
import com.demo.demotest.GetTimeFromInternetActivity;
import com.demo.demotest.GridViewItemHeightActivity;
import com.demo.demotest.GridViewMenuActivity;
import com.demo.demotest.HTMLParseActivity;
import com.demo.demotest.HttpUtilActivity;
import com.demo.demotest.InternetAvailableActivity;
import com.demo.demotest.ItemDeleteActivity;
import com.demo.demotest.JsonTestActivity;
import com.demo.demotest.LinearPressedActivity;
import com.demo.demotest.ListViewAsyncActivity;
import com.demo.demotest.ListViewCheckBoxActivity;
import com.demo.demotest.ListViewEditTextActivity;
import com.demo.demotest.ListViewItemHeightActivity;
import com.demo.demotest.ListViewRollActivity;
import com.demo.demotest.MultiThreadDownloadFileActivity;
import com.demo.demotest.PaginationListViewActivity;
import com.demo.demotest.ProgressBarFocusActivity;
import com.demo.demotest.R;
import com.demo.demotest.RefreshListViewActivity;
import com.demo.demotest.RefreshRotateAnimationActivity;
import com.demo.demotest.SDPathTestActivity;
import com.demo.demotest.ScreenInfoActivity;
import com.demo.demotest.SuperLogActivity;
import com.demo.demotest.TabHostActivity;
import com.demo.demotest.ToastAndLogActivity;
import com.demo.demotest.VideoPlayerActivity;
import com.demo.demotest.ViewPagerActivity;
import com.demo.demotest.Write2XMLActivity;
import com.demo.demotest.adapter.HomeGridViewAdapter;
import com.demo.demotest.base.BaseActivity;
import com.demo.demotest.ui.ClickSpanTextViewActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * 项目名称: DemoBaseTest<br/>
 * 类名称: HomeActivity<br/>
 * 描述: 主界面
 *
 * @author:yefx
 * @Date:2015-5-18上午10:15:55
 */
public class HomeActivity extends BaseActivity{
	private ListView m_listView;
	private HomeGridViewAdapter<String> m_gvAdapter;
	private List<String> m_dataList;
	private Class[] m_clazzs;
	@Override
	protected void init() {
		setContentView(R.layout.activity_home);
	}

	@Override
	protected void initView() {
		m_listView=(ListView) findViewById(R.id.listview_home);
	}

	@Override
	protected void initData() {
		setCommonTitleTitle("功能列表");
		m_dataList=new ArrayList<String>();
		String[] strArr=getResources().getStringArray(R.array.function);
		m_dataList=Arrays.asList(strArr);
		m_clazzs=new Class[]{
				ItemRefreshActivity.class,
				CalendarActivity.class,
			GridViewMenuActivity.class,
			ClickSpanTextViewActivity.class,
				TabHostActivity.class,
				VideoPlayerActivity.class,
				PaginationListViewActivity.class,
				ExpandableListViewActivity.class,
				HTMLParseActivity.class,
				InternetAvailableActivity.class,
				SDPathTestActivity.class,
				ListViewRollActivity.class,
				ItemDeleteActivity.class,
				RefreshListViewActivity.class,
				EventActivity.class,
				DemicalActivity.class
				,ListViewItemHeightActivity.class
				,ExceptionActivity.class
				,AlwaysToastActivity.class
				,ToastAndLogActivity.class
				,DateToSecondActivity.class
				,GridViewItemHeightActivity.class
				,ListViewAsyncActivity.class
				,Write2XMLActivity.class
				,SuperLogActivity.class
				,GetTimeFromInternetActivity.class
				,ViewPagerActivity.class
				,HttpUtilActivity.class
				,MultiThreadDownloadFileActivity.class
				,ProgressBarFocusActivity.class
				,LinearPressedActivity.class
				,ScreenInfoActivity.class
				,RefreshRotateAnimationActivity.class
				,JsonTestActivity.class
				,ListViewCheckBoxActivity.class
				,ListViewEditTextActivity.class};
		m_gvAdapter=new HomeGridViewAdapter<String>(m_context,m_dataList);
		m_listView.setAdapter(m_gvAdapter);
	}

	@Override
	protected void initListener() {
		setCommonTitleBackListener();
		m_listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(position>=m_clazzs.length)
				{
					showToast("功能暂未开放");
				}
				else
				{
					toActivity(m_clazzs[position]);
				}
			}
		});
	}


}
