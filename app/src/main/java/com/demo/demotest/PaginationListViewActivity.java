package com.demo.demotest;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ListView;

import com.demo.demotest.adapter.PaginationListViewAdapter;
import com.demo.demotest.base.BaseActivity;
import com.demo.demotest.domain.NewsEntity;
import com.demo.demotest.util.LogSuperUtil;
import com.demo.demotest.util.SysConstant;

/**
 * 
 * 项目名称: DemoBaseTest<br/>
 * 类名称: PaginationListViewActivity<br/>
 * 描述: 分页加载的ListView
 *
 * @author:yefx
 * @Date:2016-2-19下午3:23:17
 */
public class PaginationListViewActivity extends BaseActivity implements OnScrollListener,OnClickListener{

	private ListView m_lv;
	private List<NewsEntity> m_dataList;
	private PaginationListViewAdapter m_adapter;
	private static final int COUNT_MAX=30;
	private static final int COUNT_PAGE=12;
	private boolean m_bIsScrollToButtom=false;
	private View m_viewLoaderMore;
	private Button m_btnLoaderMore;
	private Handler m_handler=new Handler();
	@Override
	protected void init() {
		setContentView(R.layout.activity_pagination_listview);

	}

	@Override
	protected void initView() {
		setCommonTitleTitle("分页加载的ListView");
		m_lv = (ListView) findViewById(R.id.lv_pagination);
		m_viewLoaderMore=View.inflate(m_context, R.layout.layout_loadmore_pagination, null);
		m_btnLoaderMore = (Button) m_viewLoaderMore.findViewById(R.id.btn_loadmore_pagination);
		m_btnLoaderMore.setText("查看更多。。。");
	}

	@Override
	protected void initData() {
		m_dataList = new ArrayList<NewsEntity>();
		m_adapter = new PaginationListViewAdapter(m_context, m_dataList);
		m_lv.setAdapter(m_adapter);
		initBaseData(0,COUNT_PAGE);
	}

	@Override
	protected void initListener() {
		setCommonTitleBackListener();
		m_lv.setOnScrollListener(this);
		m_btnLoaderMore.setOnClickListener(this);
	}
	private void notifyDataChanged()
	{
		m_btnLoaderMore.setText("查看更多。。。");
		m_lv.addFooterView(m_viewLoaderMore);
		m_adapter.notifyDataSetChanged();
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if(SCROLL_STATE_IDLE==scrollState&&m_bIsScrollToButtom)
		{
			//可以自动加载更多哦
		}
		
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		LogSuperUtil.i(SysConstant.Log.pagination, "firstVisibleItem="+firstVisibleItem+",visibleItemCount="+visibleItemCount+",totalItemCount="+totalItemCount);
		if(firstVisibleItem+visibleItemCount==totalItemCount)
		{
			m_bIsScrollToButtom=true;
		}
		else
		{
			m_bIsScrollToButtom=false;
		}
		
	}
	private void initBaseData(int begin,int count)
	{
		NewsEntity newsEntity;
		for(int i=begin;i<begin+count;i++)
		{
			newsEntity=new NewsEntity();
			newsEntity.title="超级大宇宙飞船惊现土卫"+(i+1);
			newsEntity.content="据凤凰号最新发回的数据，2月18日22点31分，长达5万公里的超级大宇宙飞船惊现土卫"+(i+1)+",难道是外星人来了吗？";
			m_dataList.add(newsEntity);
		}
		notifyDataChanged();
	}
	private void loadMore()
	{
		final int index=m_dataList.size();
		if(index<COUNT_MAX)
		{
			m_btnLoaderMore.setText("正在加载更多，请稍等");
//			m_lv.removeFooterView(m_viewLoaderMore);

			m_handler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					if(index+COUNT_PAGE<COUNT_MAX)
					{
						initBaseData(index,COUNT_PAGE);
					}
					else
					{
						initBaseData(index,COUNT_MAX-index);
					}
				}
			}, 2000);
			
		}
		else
		{
			showToast("没有更多数据了");
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_loadmore_pagination:
					loadMore();
			break;

		default:
			break;
		}
		
	}
}
