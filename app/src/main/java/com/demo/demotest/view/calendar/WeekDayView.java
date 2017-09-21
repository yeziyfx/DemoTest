package com.demo.demotest.view.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by sun on 2017/6/26 10:11
 * http://blog.csdn.net/mr_dsw/article/details/48755993
 */
public class WeekDayView extends View {
    private int mTopLineColor= Color.parseColor("#CCE4F2");
    private int mBottomLineColor= Color.parseColor("#CCE4F2");
    private int mWeekDayColor= Color.parseColor("#1FC2F3");
    private int mWeekendColor= Color.parseColor("#fa4451");
    private int mStrokeWidth=4;
    private int mWeekSize=14;
    private Paint paint;
    private DisplayMetrics mDisplayMetrics;
    private String[] weekString=new String[]{"日","一","二","三","四","五","六"};
    public WeekDayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDisplayMetrics=getResources().getDisplayMetrics();
        paint=new Paint();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec)
    {
        int widthSize= MeasureSpec.getSize(widthMeasureSpec);
        int widthMode= MeasureSpec.getMode(widthMeasureSpec);

        int heightSize= MeasureSpec.getSize(heightMeasureSpec);
        int heightMode= MeasureSpec.getMode(heightMeasureSpec);

        if(heightMode== MeasureSpec.AT_MOST)
        {
            heightSize=mDisplayMetrics.densityDpi*30;
        }
        if(widthMode== MeasureSpec.AT_MOST)
        {
            widthSize=mDisplayMetrics.densityDpi*300;
        }
        setMeasuredDimension(widthSize,heightSize);
    }
    @Override
    protected void onDraw(Canvas canvas)
    {
        int width=getWidth();
        int height=getHeight();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(mTopLineColor);
        paint.setStrokeWidth(mStrokeWidth);
        canvas.drawLine(0,0,width,0,paint);

        paint.setColor(mBottomLineColor);
        canvas.drawLine(0,height,width,height,paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(mWeekSize*mDisplayMetrics.scaledDensity);
        int columnWidth=width/weekString.length;
        for(int i=0;i<weekString.length;i++)
        {
            String text=weekString[i];
            int fontWidth=(int)paint.measureText(text);
            int startX=columnWidth*i+(columnWidth-fontWidth)/2;
            int startY=(int)(height/2-(paint.ascent()+paint.descent())/2);
            if(text.indexOf("日")>-1||text.indexOf("六")>-1)
            {
                paint.setColor(mWeekendColor);
            }
            else {
                paint.setColor(mWeekDayColor);
            }
            canvas.drawText(text,startX,startY,paint);
        }
    }
    public void setTopLineColor(int topLineColor)
    {
        this.mTopLineColor=mTopLineColor;
    }
    public void setBottomLineColor(int bottomLineColor)
    {
        this.mBottomLineColor=bottomLineColor;
    }
    public void setWeekDayColor(int weekdayColor)
    {
        this.mWeekDayColor=weekdayColor;
    }
    public void setWeekendColor(int weekendColor)
    {
        this.mWeekendColor=weekendColor;
    }
    public void setStrokeWidth(int strokeWidth)
    {
        this.mStrokeWidth=strokeWidth;
    }
    public void setWeekSize(int weekSize)
    {
        this.mWeekSize=weekSize;
    }
    public void setWeekString(String[] weekString)
    {
        this.weekString=weekString;
    }
}
