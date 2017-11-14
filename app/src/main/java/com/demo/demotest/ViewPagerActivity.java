package com.demo.demotest;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.demo.demotest.R;
import com.demo.demotest.adapter.ViewPagerAdapter;
import com.demo.demotest.base.BaseFragmentActivity;
import com.demo.demotest.fragment.Fragment1;
import com.demo.demotest.fragment.Fragment2;
import com.demo.demotest.fragment.Fragment3;
import com.demo.demotest.view.LazyViewPager;

/**
 * 
 * 项目名称:DemoBaseTest<br/>
 * 类名称:ViewPagerActivity<br/>
 * 描述:ViewPager示例
 *
 * @author:yfx
 * @Date:2015-7-15下午11:35:26
 */
public class ViewPagerActivity extends BaseFragmentActivity {

	private LazyViewPager m_viewPager;
	private List<Fragment> m_fragmentList;
	private ViewPagerAdapter m_adapter;
	@Override
	protected void init() {
		setContentView(R.layout.activity_view_pager);
	}

	@Override
	protected void initView() {
		m_viewPager = (LazyViewPager) findViewById(R.id.viewpager);
		setTitleBarTitle("viewpager+fragment");
	}

	@Override
	protected void initData() {
		m_fragmentList = new ArrayList<Fragment>();
		Fragment1 fragment1=new Fragment1();
		Fragment2 fragment2=new Fragment2();
		Fragment3 fragment3=new Fragment3();
		m_fragmentList.add(fragment1);
		m_fragmentList.add(fragment2);
		m_fragmentList.add(fragment3);
		m_adapter = new ViewPagerAdapter(getSupportFragmentManager(),m_fragmentList);
		m_viewPager.setAdapter(m_adapter);
	}

	@Override
	protected void initListener() {
		setTitleBarBackListener();
		m_viewPager.setOnPageChangeListener(new ViewPagerChangeListener());

	}
	class ViewPagerChangeListener implements LazyViewPager.OnPageChangeListener
	{
		/**
		 * state:网上通常说法:1表示正在滑动，2表示滑动完毕 ，0表示什么都没做，就是停在那
		 * 我的认为:1是按下时，0是松开，2则是新的标签页是否滑动了
		 * (例如：当前页是第一页，如果你向右滑不会打印2，如果向左滑直到看到了第二页，那么就会打印出2了);
		 * 个人认为一般情况下，是不会重写这个方法的
		 */
		@Override
		public void onPageScrollStateChanged(int state) {
			// TODO Auto-generated method stub
			
		}
		/**
		 * page 看名称就看得出，当前页;positionOffset:位置偏移量，范围[0,1];
		 * positionOffsetPixels:位置像素,范围[0,屏幕宽度];
		 * 个人认为一般情况下是不会重写这个方法的
		 */
		@Override
		public void onPageScrolled(int page, float positionOffset, int positionOffsetPixels) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int page) {
			//更新图标
//			Toast.makeText(m_context, "page"+page, 0).show();
			showToast("第"+(page+1)+"页");
		}
		
	}
}
