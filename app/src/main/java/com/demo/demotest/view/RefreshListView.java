package com.demo.demotest.view;

import java.text.SimpleDateFormat;

import com.demo.demotest.R;
import com.demo.demotest.util.SysConstant;

import android.content.Context;
import android.content.pm.Signature;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class RefreshListView extends ListView implements OnScrollListener{
	private int firstVisibleItemPosition;//屏幕显示在第一个的item的索引
	private int downY;//按下时y轴的偏移量
	private int headerViewHeight;//头布局的高度
	private View headerView;//头布局的对象
	
	private final int DOWN_PULL_REFRESH=0;//下拉刷新状态
	private final int RELEASE_REFRESH=1;//松开刷新状态
	private final int REFRESHING=2;//正在刷新中状态
	private int currentState=DOWN_PULL_REFRESH;//头布局的状态:默认为下拉刷新状态
	
	private Animation upAnimation;//向上旋转的动画
	private Animation downAnimation;//向下旋转的动画
	
	private ImageView ivArrow;//头布局的箭头
	private ProgressBar mProgressBar;//头布局的进度条
	private TextView tvState;//头布局的状态
	private TextView tvLastUpateTime;//头布局的最后更新时间
	
	private OnRefreshListener mOnRefreshListener;
	private boolean isScrollToBottom;//是否滑动到底部
	private View footerView;//脚布局的对象
	private int footerViewHeight;//脚布局的高度
	private boolean isLoadingMore=false;//是否正在加载更多中
	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initHeaderView();
		initFooterView();
		setOnScrollListener(this);
	}
	/**初始化脚布局*/
	private void initFooterView()
	{
		footerView=View.inflate(getContext(), R.layout.listview_footer, null);
		footerView.measure(0, 0);
		footerViewHeight=footerView.getMeasuredHeight();
		footerView.setPadding(0, -footerViewHeight, 0, 0);
		addFooterView(footerView);
	}
	/**初始化头布局*/
	private void initHeaderView()
	{
		headerView=View.inflate(getContext(), R.layout.listview_header, null);
		ivArrow=(ImageView) headerView.findViewById(R.id.iv_listview_header_arrow);
		mProgressBar=(ProgressBar) headerView.findViewById(R.id.pb_listview_header);
		tvState=(TextView) headerView.findViewById(R.id.tv_listview_header_state);
		tvLastUpateTime=(TextView) headerView.findViewById(R.id.tv_listview_header_last_update_time);
		
		//设置最后刷新时间
		tvLastUpateTime.setText("最后刷新时间:"+getLastUpdateTime());
		
		headerView.measure(0, 0);//系统会帮我们测量出headerView的高度
		headerViewHeight=headerView.getMeasuredHeight();
		headerView.setPadding(0, -headerViewHeight, 0, 0);
		addHeaderView(headerView);//向ListView的顶部添加一个view对象
		initAnimation();
	}
	/**获得系统的最新时间*/
	private String getLastUpdateTime()
	{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(System.currentTimeMillis());
	}
	private void initAnimation()
	{
		upAnimation=new RotateAnimation(0f, -180f, Animation.RELATIVE_TO_SELF, 0.5f,
		                                Animation.RELATIVE_TO_SELF, 0.5f);
		upAnimation.setDuration(500);
		upAnimation.setFillAfter(true);//动画结束后,停留在结束的位置上
		
		downAnimation=new RotateAnimation(-180f, -360f, Animation.RELATIVE_TO_SELF, 0.5f,
		                                  Animation.RELATIVE_TO_SELF, 0.5f);
		downAnimation.setDuration(500);
		downAnimation.setFillAfter(true);//动画结束后,停留在结束的位置上
	}
	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downY=(int)ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			int moveY=(int)ev.getY();
			//移动中的y-按下的y=间距
			int diff=(moveY-downY)/2;
			//-头布局的高度+间距=paddingTop
			int paddingTop=-headerViewHeight+diff;
			//如果：-头布局的高度>paddingTop的值 执行super.onTouchEvent(ev);
			if(firstVisibleItemPosition==0&&-headerViewHeight<paddingTop)
			{
				if(paddingTop>0&&currentState==DOWN_PULL_REFRESH)//完全显示了
				{
					currentState=RELEASE_REFRESH;
					refreshHeaderView();
				}
				else if(paddingTop<0&&currentState==RELEASE_REFRESH)//没有完全显示
				{
					currentState=DOWN_PULL_REFRESH;
					refreshHeaderView();
				}
				//下拉头布局
				headerView.setPadding(0, paddingTop, 0, 0);
				return true;
			}
		case MotionEvent.ACTION_UP:
			//判断当前的状态是松开刷新还是下拉刷新
			if(currentState==RELEASE_REFRESH)
			{
				//把头布局设置为完全显示状态
				headerView.setPadding(0, 0, 0, 0);
				//进入到正在刷新中状态
				currentState=REFRESHING;
				refreshHeaderView();
				if(mOnRefreshListener!=null)
				{
					mOnRefreshListener.onDownPullRefresh();//调用使用者的监听方法
				}
			}
			else if(currentState==DOWN_PULL_REFRESH)
			{
				//隐藏头布局
				headerView.setPadding(0, -headerViewHeight, 0, 0);
			}
			break;
		default:
			break;
		}
		return super.onTouchEvent(ev);
	}
	private void refreshHeaderView()
	{
		switch (currentState) {
		case DOWN_PULL_REFRESH://下拉刷新状态
			tvState.setText("下拉刷新");
			ivArrow.startAnimation(downAnimation);//执行向下旋转
			break;
		case RELEASE_REFRESH://松开刷新状态
			tvState.setText("松开刷新");
			ivArrow.startAnimation(upAnimation);//执行向上旋转
			break;
		case REFRESHING:
			ivArrow.clearAnimation();
			ivArrow.setVisibility(View.GONE);
			mProgressBar.setVisibility(View.VISIBLE);
			tvState.setText("正在刷新中...");
			break;
		default:
			break;
		}
	}
	/**当滚动状态改变时回调*/
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if(scrollState==SCROLL_STATE_IDLE||scrollState==SCROLL_STATE_FLING)
		{
			//判断当前是否已经到了底部
			if(isScrollToBottom&&!isLoadingMore)
			{
				isLoadingMore=true;
				//当前到底部
				footerView.setPadding(0, 0, 0, 0);
				setSelection(getCount());
				if(mOnRefreshListener!=null)
				{
					mOnRefreshListener.onLoadingMore();
				}
			}
		}
		
	}
	/**当滚动时调用*/
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		firstVisibleItemPosition=firstVisibleItem;
		//firstVisibleItem+visibleItemCount！=getLastVisiblePosition()????
		if(getLastVisiblePosition()==(totalItemCount-1))
		{
			isScrollToBottom=true;
		}
		else
		{
			isScrollToBottom=false;
		}
		
	}
	public void setOnRefreshListener(OnRefreshListener listener)
	{
		mOnRefreshListener=listener;
	}
	public void hideHeaderView()
	{
		headerView.setPadding(0, -headerViewHeight, 0, 0);
		ivArrow.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.GONE);
		tvState.setText("下拉刷新");
		tvLastUpateTime.setText("最后刷新时间:"+getLastUpdateTime());
		currentState=DOWN_PULL_REFRESH;
	}
	public void hideFooterView()
	{
		footerView.setPadding(0, -footerViewHeight, 0, 0);
		isLoadingMore=false;
	}
	public interface OnRefreshListener
	{
		void onDownPullRefresh();
		void onLoadingMore();
	}
}
