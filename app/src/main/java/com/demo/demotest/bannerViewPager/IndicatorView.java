package com.demo.demotest.bannerViewPager;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by sun on 2018/1/24 15:53
 * 继承此类的类要实现接口方法，即使接口回调中不写任何代码
 */
public abstract class IndicatorView extends LinearLayout implements IIndicator {
    public IndicatorView(Context context) {
        super(context);
    }
    public IndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public IndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
