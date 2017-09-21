package com.demo.demotest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sun on 2017/8/30 14:20
 */
public class ViewPagerTitle extends HorizontalScrollView{
    public ViewPagerTitle(Context context) {
        super(context);
    }
    public ViewPagerTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public ViewPagerTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private int defaultTextSize;
    private int allTextViewLength;
    private int getTextViewMargins(String[] titleAry)
    {
        int defaultMargins=30;
        float countLength=0;
        TextView textView=new TextView(getContext());
        textView.setTextSize(defaultTextSize);
        TextPaint paint=textView.getPaint();
        for(int i=0;i<titleAry.length;i++)
        {
            countLength=countLength+defaultMargins
                    +paint.measureText(titleAry[i])+defaultMargins;
        }
        int screenWidth=getScreenWidth(getContext());
        if(countLength<=screenWidth)//TextView总长度小于屏幕宽度
        {
            allTextViewLength=screenWidth;
            return (screenWidth/titleAry.length-(int)paint.measureText(titleAry[0]))/2;
        }
        else {
            allTextViewLength=(int)(countLength);
            return defaultMargins;
        }
    }
    private int getScreenWidth(Context context)
    {
        return 0;
    }
    private int selectedTextSize;
    private String[] titles;
    private DynamicLine dynamicLine;
    private int getFixLeftDis()
    {
        TextView textView=new TextView(getContext());
        textView.setTextSize(defaultTextSize);
        textView.setText(titles[0]);
        float defaultTextSize=getTextViewLength(textView);
        textView.setTextSize(selectedTextSize);
        float selectTextSize=getTextViewLength(textView);
        return (int)(selectTextSize-defaultTextSize)/2;
    }
    private int getTextViewLength(TextView textView)
    {
        return 0;
    }
    private LinearLayout.LayoutParams contentParams;
    private int margin;
    private LinearLayout.LayoutParams textViewParams;
    private OnClickListener onClickListener;
    private List<TextView> textViews;
    private void createTextViews(String[] titles)
    {
        LinearLayout contentL1=new LinearLayout(getContext());
        contentL1.setBackgroundColor(Color.parseColor("#fffacd"));
        contentL1.setLayoutParams(contentParams);
        contentL1.setOrientation(LinearLayout.VERTICAL);
        addView(contentL1);

        LinearLayout textViewL1=new LinearLayout(getContext());
        textViewL1.setLayoutParams(contentParams);
        textViewL1.setOrientation(LinearLayout.HORIZONTAL);
        margin=getTextViewMargins(titles);
        textViewParams.setMargins(margin,0,margin,0);

        for(int i=0;i<titles.length;i++)
        {
            TextView textView=new TextView(getContext());
            textView.setText(titles[i]);
            textView.setTextColor(Color.GRAY);
            textView.setTextSize(defaultTextSize);
            textView.setLayoutParams(textViewParams);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setOnClickListener(onClickListener);
            textView.setTag(i);
            textViews.add(textView);
            textViewL1.addView(textView);
        }
        contentL1.addView(textViewL1);
        contentL1.addView(dynamicLine);
    }
    private List<TextView> getTextView()
    {
        return textViews;
    }
    public class DynamicLine extends View {
        private float startX,stopX;//的起始X，终止X坐标
        private Paint paint;
        private RectF rectF=new RectF(startX,0,stopX,0);
        public DynamicLine(Context context) {
            super(context,null);
        }
        public DynamicLine(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs,0);
        }
        public DynamicLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }
        private void init()
        {
            paint=new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(5);
            paint.setShader(new LinearGradient(0,100,getScreenWidth(getContext()),100,
                    Color.parseColor("#ffc125"),Color.parseColor("#ff4500"), Shader.TileMode.MIRROR));
        }
        @Override
        protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec)
        {
            //自定义DynamicLine的高度
            heightMeasureSpec=MeasureSpec.makeMeasureSpec(20,MeasureSpec.getMode(heightMeasureSpec));
            super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        }
        @Override
        protected void onDraw(Canvas canvas)
        {
            rectF.set(startX,0,stopX,10);
            canvas.drawRoundRect(rectF,5,5,paint);//圆角矩形的圆角曲率
        }
        public void updateView(float startX,float stopX)
        {
            this.startX=startX;
            this.stopX=stopX;
            invalidate();
        }
    }
    public class MyPagerTitleChangeListener implements ViewPager.OnPageChangeListener {
        private ViewPagerTitle viewPagerTitle;
        private ViewPager pager;
        private DynamicLine dynamicLine;
        private List<TextView> textViews;
        private int pagerCount;
        private int screenWidth;
        private int lineWidth;
        private int everyLength;
        private int dis;
        private int fixLeftDis;
        private int lastPosition;
        private final static int SCROLL_STATE_SETTING=1;
        private int[] location=new int[2];
        public MyPagerTitleChangeListener(Context context, ViewPager viewPager,
                                          DynamicLine dynamicLine,ViewPagerTitle viewPagerTitle,
                                          int allLength,int margin,int fixLeftDis)
        {
            this.viewPagerTitle=viewPagerTitle;
            this.pager=viewPager;
            this.dynamicLine=dynamicLine;
            textViews=viewPagerTitle.getTextView();
            pagerCount=textViews.size();
            screenWidth=getScreenWidth(context);
            lineWidth=(int)getTextViewLength(textViews.get(0));
            everyLength=allLength/pagerCount;
            dis=margin;
            this.fixLeftDis=fixLeftDis;
        }
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if(lastPosition>position)
            {
                //当页面向右滚动时，dynamicLine的右边的stopX位置不变，startX在变化
                dynamicLine.updateView((position+positionOffset)*everyLength
                +dis+fixLeftDis,(lastPosition+1)*everyLength-dis);
            }
            else {//页面向左滚动
                //当页面向左滚动时，dynamicLine的左边的startX位置不变，stopX在变化
                if(positionOffset>0.5f)
                {
                    positionOffset=0.5f;
                }
                dynamicLine.updateView(lastPosition*everyLength+dis+fixLeftDis,
                        (position+positionOffset*2)*everyLength+dis+lineWidth);
            }
        }
        @Override
        public void onPageSelected(int position) {
        }
        @Override
        public void onPageScrollStateChanged(int state) {
            boolean scrollRight;//页面向右
            if(state==SCROLL_STATE_SETTING)
            {
                scrollRight=lastPosition<pager.getCurrentItem();
                /*
                下面几行代码，解决页面滑动到的TAB页时对应的TextView对应，TextView处于
                屏幕外面，这个时候就需要将HorizontalScrollView滑动到屏幕中间。
                 */
                if(lastPosition+1<textViews.size()&&lastPosition-1>=0)
                {
                    int targetPosition=scrollRight?lastPosition+1:lastPosition-1;
                    textViews.get(targetPosition).getLocationOnScreen(location);
                    if(location[0]>screenWidth)
                    {
                        viewPagerTitle.smoothScrollBy(screenWidth/2,0);
                    }
                    else if(location[0]<0){
                        viewPagerTitle.smoothScrollBy(-screenWidth/2,0);
                    }
                }
            }
        }
    }
}
