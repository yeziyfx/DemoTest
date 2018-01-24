package com.demo.demotest.adapter;

/**
 * Created by sun on 2018/1/23 16:34
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.demo.demotest.R;
import com.demo.demotest.bannerViewPager.BannerPagerAdapter;
import com.demo.demotest.bannerViewPager.DataSetSubscriber;
import com.demo.demotest.bannerViewPager.OnPageClickListener;
import com.demo.demotest.domain.ThemeRecommendBean;
import com.demo.demotest.util.Constants;
import com.demo.demotest.util.LogSuperUtil;
import com.demo.demotest.view.RoundRectImageView;

import java.util.ArrayList;
import java.util.List;
public class HomeThemePagerAdapter extends BannerPagerAdapter {

    private List<DataSetSubscriber> mSubscribers = new ArrayList<>();
    private List<View> mDataViews;
    private OnPageClickListener mOnPageClickListener;
    private List<ThemeRecommendBean> mDataList;
    private Context mContext;
    /**
     * 构造函数
     * @param mDataViews view列表
     */
    public HomeThemePagerAdapter(Context context, List<View> mDataViews, List<ThemeRecommendBean> dataList, OnPageClickListener listener) {
        this.mDataViews = mDataViews;
        this.mOnPageClickListener = listener;
        this.mDataList=dataList;
        this.mContext=context;
    }

    public View getView(int location){
        return this.mDataViews.get(location);
    }

    @Override
    public int getCount() {
        return mDataViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        View view = mDataViews.get(position);
        final int i = position;
        if(mOnPageClickListener != null){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnPageClickListener.onPageClick(v,i);
                }
            });
        }
        ThemeRecommendBean bean=mDataList.get(position);
        String url=bean.bimageid;
//        LogSuperUtil.i(Constants.LogTag.theme_recommand,"position="+position+",url="+url);
        RoundRectImageView iv= (RoundRectImageView) view.findViewById(R.id.iv_item_viewpager_theme_home);
        Glide.with(mContext).load(url).into(iv);
//        String title=bean.title;
//        TextView tv= (TextView) view.findViewById(R.id.tv_item_viewpager_theme_home);
//        tv.setText(title==null?"":title);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        notifySubscriber();
    }

    @Override
    public void registerSubscriber(DataSetSubscriber subscriber) {
        mSubscribers.add(subscriber);
    }

    @Override
    public void removeSubscriber(DataSetSubscriber subscriber) {
        mSubscribers.remove(subscriber);
    }

    @Override
    public void notifySubscriber() {
        for(DataSetSubscriber subscriber : mSubscribers){
            subscriber.update(getCount()-2);
        }
    }
}
