package com.demo.demotest.view.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

/**
 * Created by sun on 2017/6/26 14:37
 * http://blog.csdn.net/mr_dsw/article/details/48755993
 */
public class MonthDateView extends View {
    private static final int num_columns=7;
    private static final int num_rows=6;
    private Paint mPaint;
    private int mDayColor= Color.parseColor("#000000");
    private int mSelectDayColor= Color.parseColor("#ffffff");
    private int mSelectBGColor= Color.parseColor("#1FC2F3");
    private int mCurrentColor= Color.parseColor("#ff0000");
    private int mCurrYear,mCurrMonth,mCurrDay;
    private int mSelYear,mSelMonth,mSelDay;
    private int mColumnSize,mRowSize;
    private DisplayMetrics mDisplayMetrics;
    private int mDaySize=18;
    private TextView tv_date,tv_week;
    private int weekRow;
    private int[][] daysString;
    private int mCircleRadius=35;//6
    private DateClick dateClick;
    private int mCircleColor= Color.parseColor("#ff0000");
    private List<Integer> daysHasThingList;
    public MonthDateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDisplayMetrics=getResources().getDisplayMetrics();
        Calendar calendar= Calendar.getInstance();
        mPaint=new Paint();
        mCurrYear=calendar.get(Calendar.YEAR);
        mCurrMonth=calendar.get(Calendar.MONTH);
        mCurrDay=calendar.get(Calendar.DATE);
        setSelectYearMonth(mCurrYear,mCurrMonth,mCurrDay);
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
            heightSize=mDisplayMetrics.densityDpi*200;
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
        initSize();
        daysString=new int[6][7];
        mPaint.setTextSize(mDaySize*mDisplayMetrics.scaledDensity);
        String dayString;
        int mMonthDays= DateUtils.getMonthDays(mSelYear,mSelMonth);//某个月有多少天
        int weekNumber=DateUtils.getFirstDayWeek(mSelYear,mSelMonth);//某个月1号是周几
        for(int day=0;day<mMonthDays;day++)
        {
            dayString=(day+1)+"";
            int column=(day+weekNumber-1)%7;
            int row=(day+weekNumber-1)/7;
            daysString[row][column]=day+1;
            int startX=(int)(mColumnSize*column+(mColumnSize-mPaint.measureText(dayString))/2);
            int startY=(int)(mRowSize*row+mRowSize/2-(mPaint.ascent()+mPaint.descent())/2);
            if(dayString.equals(mSelDay+""))
            {
                //绘制背景色矩形
                int startRecX=mColumnSize*column;
                int startRecY=mRowSize*row;
                int endRecX=startRecX+mColumnSize;
                int endRecY=startRecY+mRowSize;
                mPaint.setColor(mSelectBGColor);
                canvas.drawRect(startRecX,startRecY,endRecX,endRecY,mPaint);
                //记录第几行，即第几周
                weekRow=row+1;
            }
            //绘制事务圆形标志
            drawCircle(row,column,day+1,canvas);
            if(dayString.equals(mSelDay+""))
            {
                mPaint.setColor(mSelectDayColor);
            }
            else if(daysString.equals(mCurrDay+"")&&mCurrDay!=mSelDay&&mCurrMonth==mSelMonth)
            {
                mPaint.setColor(mCurrentColor);
            }
            else {
                mPaint.setColor(mDayColor);
            }
            canvas.drawText(dayString,startX,startY,mPaint);
            if(tv_date!=null)
            {
                tv_date.setText(mSelYear+"年"+(mSelMonth+1)+"月");
            }
            if(tv_week!=null)
            {
                tv_week.setText("第"+weekRow+"周");
            }
        }
    }
    private void drawCircle(int row,int column,int day,Canvas canvas)
    {
        if(daysHasThingList!=null&&daysHasThingList.size()>0)
        {
            if(!daysHasThingList.contains(day))
            {
                return;
            }
            mPaint.setColor(mCircleColor);
            float circleX=(float)(mColumnSize*column+mColumnSize*0.5);//0.8
            float circleY=(float)(mRowSize*row+mRowSize*0.5);//0.2
            canvas.drawCircle(circleX,circleY,mCircleRadius,mPaint);
        }
    }
    @Override
    public boolean performClick()
    {
        return super.performClick();
    }
    private int downX=0,downY=0;
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int eventCode=event.getAction();
        switch (eventCode)
        {
            case MotionEvent.ACTION_DOWN:
                downX=(int)event.getX();
                downY=(int)event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                int upX=(int)event.getX();
                int upY=(int)event.getY();
                if(Math.abs(upX-downX)<10&& Math.abs(upY-downY)<10)//点击事件
                {
                    performClick();
                    doClickAction((upX+downX)/2,(upY+downY)/2);
                }
                break;
        }
        return true;
    }
    /**
     * 初始化列宽行高
     */
    private void initSize()
    {
        mColumnSize=getWidth()/num_columns;
        mRowSize=getHeight()/num_rows;
    }
    /**
     * 设置年月
     * @param year
     * @param month
     * @param day
     */
    private void setSelectYearMonth(int year,int month,int day)
    {
        mSelYear=year;
        mSelMonth=month;
        mSelDay=day;
    }
    /**
     * 执行点击事件
     * @param x
     * @param y
     */
    private void doClickAction(int x,int y)
    {
        int row=y/mRowSize;
        int column=x/mColumnSize;
        setSelectYearMonth(mSelYear,mSelMonth,daysString[row][column]);
        invalidate();
        //执行activity发送过来的点击处理事件
        if(dateClick!=null)
        {
            dateClick.onClickOnDate();
        }
    }
    /**
     * 左点击，日历向后翻页
     */
    public void onLeftClick()
    {
        int year=mSelYear;
        int month=mSelMonth;
        int day=mSelDay;
        if(month==0)//如果是1月份，则变成12月份
        {
            year=mSelYear-1;
            month=11;
        }
        else if(DateUtils.getMonthDays(year,month)==day)
        {
            //如果当前日期为该月最后一点，当向前推的时候，就需要改变选中的日期
            month=month-1;
            day= DateUtils.getMonthDays(year,month);
        }
        else {
            month=month-1;
        }
        setSelectYearMonth(year,month,day);
        invalidate();
    }
    /**
     * 右点击，日历向前翻页
     */
    public void onRightClick()
    {
        int year=mSelYear;
        int month=mSelMonth;
        int day=mSelDay;
        if(month==11)//如果是12月份，则变成1月份
        {
            year=mSelYear+1;
            month=0;
        }
        else if(DateUtils.getMonthDays(year,month)==day)
        {
            //如果当前日期为该月最后一点，当向前推的时候，就需要改变选中的日期
            month=month+1;
            day=DateUtils.getMonthDays(year,month);
        }
        else {
            month=month+1;
        }
        setSelectYearMonth(year,month,day);
        invalidate();
    }
    public int getSelYear()
    {
        return mSelYear;
    }
    public int getSelMonth()
    {
        return mSelMonth;
    }
    public int getSelDay()
    {
        return mSelDay;
    }
    /**
     * 普通日期的字体颜色，默认黑色
     * @param dayColor
     */
    public void setDayColor(int dayColor)
    {
        this.mDayColor=dayColor;
    }
    /**
     * 选择日期的颜色，默认为白色
     * @param selectDayColor
     */
    public void setSelectDayColor(int selectDayColor)
    {
        this.mSelectDayColor=selectDayColor;
    }
    /**
     * 选中日期的背景颜色，默认蓝色
     * @param selectBGColor
     */
    public void setSelectBGColor(int selectBGColor)
    {
        this.mSelectBGColor=selectBGColor;
    }
    /**
     * 当前日期不是选中的颜色，默认红色
     * @param currentColor
     */
    public void setCurrentColor(int currentColor)
    {
        this.mCurrentColor=currentColor;
    }
    /**
     * 日期字体的大小，默认18sp
     * @param daySize
     */
    public void setDaySize(int daySize)
    {
        this.mDaySize=daySize;
    }
    public void setTextView(TextView tv_date, TextView tv_week)
    {
        this.tv_date=tv_date;
        this.tv_week=tv_week;
        invalidate();
    }
    /**
     * 设置事务天数
     * @param daysHasThingList
     */
    public void setDaysHasThingList(List<Integer> daysHasThingList)
    {
        this.daysHasThingList=daysHasThingList;
    }
    /**
     * 设置圆圈的半径，默认为6
     * @param circleRadius
     */
    public void setCircleRadius(int circleRadius)
    {
        this.mCircleRadius=circleRadius;
    }
    /**
     * 设置圆圈的颜色
     * @param circleColor
     */
    public void setCircleColor(int circleColor)
    {
        this.mCircleColor=circleColor;
    }
    /**
     * 设置日期的点击回调事件
     */
    public interface DateClick
    {
        void onClickOnDate();
    }
    /**
     * 设置日期点击事件
     * @param dateClick
     */
    public void setDateClick(DateClick dateClick)
    {
        this.dateClick=dateClick;
    }
    /**
     * 跳转至今天
     */
    public void setTodayToView()
    {
        setSelectYearMonth(mCurrYear,mCurrMonth,mCurrDay);
        invalidate();
    }
}
