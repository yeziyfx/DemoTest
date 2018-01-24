package com.demo.demotest.activity;

import android.view.Gravity;
import android.view.View;

import com.demo.demotest.R;
import com.demo.demotest.adapter.HomeThemePagerAdapter;
import com.demo.demotest.bannerViewPager.BannerViewPager;
import com.demo.demotest.bannerViewPager.LoopIndicator;
import com.demo.demotest.bannerViewPager.LoopViewPager;
import com.demo.demotest.bannerViewPager.OnPageClickListener;
import com.demo.demotest.base.BaseCommonActivity;
import com.demo.demotest.domain.ThemeRecommendBean;
import com.demo.demotest.util.Constants;
import com.demo.demotest.util.LogSuperUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sun on 2018/1/23 14:53
 */
public class BannerViewPagerActivity extends BaseCommonActivity {
    private LoopViewPager mViewPager;
    private List<ThemeRecommendBean> themeNoLoginlist;
    private List<View> mViewPagerItemViewList;
    private List<String> mUrlList;
    private HomeThemePagerAdapter mAdapter;
    @Override
    protected void init() {
        setContentView(R.layout.activity_banner_viewpager);
    }
    @Override
    protected void initView() {
        setCommonTitleTitle("可循环滚动的轮播图");
        mViewPager= (LoopViewPager) findViewById(R.id.viewpager_home);
        LoopIndicator loopIndicator=new LoopIndicator(m_context);
        loopIndicator.setIndicatorNormalResId(R.drawable.ic_point_normal);
        loopIndicator.setIndicatorSelectResId(R.drawable.ic_point_selected);
        mViewPager.setIndicator(loopIndicator,20, Gravity.BOTTOM|Gravity.CENTER);
        mViewPager.setAutoRolling(false);
    }
    @Override
    protected void initData() {
        themeNoLoginlist=new ArrayList<>();
        mViewPagerItemViewList=new ArrayList<>();
        mAdapter = new HomeThemePagerAdapter(m_context, mViewPagerItemViewList, themeNoLoginlist, new OnPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
                showToast("position="+position);
            }
        });
        mUrlList=new ArrayList<>();
        mViewPager.setAdapter(mAdapter);
    }
    @Override
    protected void initListener() {
        setCommonTitleBackListener();
    }
    @Override
    protected void loadData() {
        mUrlList.add("http://img.ivsky.com/img/bizhi/slides/201711/16/thor_ragnark-010.jpg");
        mUrlList.add("http://img.ivsky.com/img/bizhi/slides/201712/19/trickybiddy-017.jpg");
        mUrlList.add("http://img.ivsky.com/img/bizhi/slides/201711/23/coco-004.jpg");
        mUrlList.add(mUrlList.get(0));
        //0,1,2,0
        mUrlList.add(0,mUrlList.get(2));//左右都可以循环
        //2,0,1,2,0
        for (int i=0;i<mUrlList.size();i++)
        {
            ThemeRecommendBean bean=new ThemeRecommendBean();
            bean.bimageid=mUrlList.get(i);
            themeNoLoginlist.add(bean);
            View itemView=View.inflate(m_context,R.layout.item_viewpager_theme_home,null);
            mViewPagerItemViewList.add(itemView);
        }
        mAdapter.notifyDataSetChanged();
    }
}
