package com.demo.demotest.activity;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.demo.demotest.R;
import com.demo.demotest.base.BaseActivity;
import com.demo.demotest.base.BaseCommonActivity;
import com.demo.demotest.util.LogSuperUtil;
import com.demo.demotest.view.RoundRectImageView;

import java.io.File;

/**
 * Created by sun on 2017/11/30 18:52
 * 图片宽高自适应
 */
public class ImageViewSizeAutoActivity extends BaseCommonActivity {
    private RoundRectImageView mIv;
    private final static String TAG="imagesize";
    private LinearLayout mLinBg;
    @Override
    protected void init() {
        setContentView(R.layout.activity_imageview_size_auto);
    }
    @Override
    protected void initView() {
        setCommonTitleTitle("图片尺寸自适应");
        mIv= (RoundRectImageView) findViewById(R.id.iv_imageview_size_auto);
        mLinBg = (LinearLayout) findViewById(R.id.lin_bg_iv);
    }
    @Override
    protected void initData() {

    }
    @Override
    protected void initListener() {
        setCommonTitleBackListener();
        mIv.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                LogSuperUtil.i(TAG,"imageview width="+(right-left));
            }
        });
        mLinBg.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                LogSuperUtil.i(TAG,"lin bg width="+(right-left));
            }
        });
    }
    @Override
    protected void loadData() {
        String url="http://epub.cnki.net/fengmian/CJFD/big/"+"LZGX"+".jpg";
        Glide.with(this).load(url).into(mIv);
        //小米5s plus
        //imageview width=1030
        //lin bg width=1080

        //模拟器
        //imageview width=508
        //lin bg width=530
    }
}
