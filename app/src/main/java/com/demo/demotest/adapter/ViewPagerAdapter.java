package com.demo.demotest.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * 项目名称:DemoBaseTest<br/>
 * 类名称:ViewPagerAdapter<br/>
 * 描述:ViewPager的适配器
 * 
 * @author:yfx
 * @Date:2015-7-15下午11:58:57
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {


	private List<Fragment> m_list;

	public ViewPagerAdapter(FragmentManager fm, List<Fragment> list) {
		super(fm);
		m_list = list;
	}

	@Override
	public Fragment getItem(int arg0) {
		return m_list.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return m_list.size();
	}
	@Override
	public boolean isViewFromObject(View view, Object object) {
		// TODO Auto-generated method stub
		return super.isViewFromObject(view, object);
	}
	
}
