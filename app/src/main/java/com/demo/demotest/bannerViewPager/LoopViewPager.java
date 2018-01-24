package com.demo.demotest.bannerViewPager;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.demo.demotest.util.Constants;
import com.demo.demotest.util.LogSuperUtil;

/**
 * A BannerViewPager owns its indicators and it can roll automatically.
 * BannerViewPager支持指示器以及自动轮播。
 *左右均循环
 * @author Chen Yu
 * @version 1.1.0
 * @date 2017-01-20
 */
public class LoopViewPager extends FrameLayout implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private IndicatorView mIndicatorView;
    private BannerPagerAdapter mAdapter;
    private Context mContext;
    private int mCurrentPosition;

    //viewpager's rolling state
    private int mViewPagerScrollState;
    //by default,auto-rolling is on.
    private boolean isAutoRolling = true;
    //the interval between rollings
    private int mAutoRollingTime = 4000;
    private int mReleasingTime = 0;
    private boolean isFirstVisible;
    private static final int MESSAGE_AUTO_ROLLING = 0X1001;
    private static final int MESSAGE_AUTO_ROLLING_CANCEL = 0X1002;

    public LoopViewPager(Context context) {
        this(context, null);
    }

    public LoopViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoopViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initViews();
    }

    private void initViews() {
        //initialize the viewpager
        mViewPager = new ViewPager(mContext);
        ViewPager.LayoutParams lp = new ViewPager.LayoutParams();
        lp.width = ViewPager.LayoutParams.MATCH_PARENT;
        lp.height = ViewPager.LayoutParams.MATCH_PARENT;
        mViewPager.setLayoutParams(lp);
        //add the viewpager and the indicator to the container.
        addView(mViewPager);
        //initialize the indicator
        isFirstVisible = true;
        mViewPager.setPageTransformer(false,null);
    }

    public void setAutoRolling(boolean isAutoRolling){
        this.isAutoRolling = isAutoRolling;
    }

    public void setAutoRollingTime(int time){
        this.mAutoRollingTime = time;
    }
    private int mIndictorCount;
    public void setAdapter(BannerPagerAdapter adapter){
        mIndictorCount=adapter.getCount()-2;
        if(mIndicatorView!=null)
        {
            mIndicatorView.setItemCount(mIndictorCount);
        }
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(this);

        mAdapter = adapter;
        mAdapter.registerSubscriber(new DataSetSubscriber() {
            @Override
            public void update(int count) {
                mIndictorCount=count;
                if(mIndicatorView!=null)
                {
                    mIndicatorView.setItemCount(mIndictorCount);
                }
                mViewPager.setCurrentItem(1);
//                mIndicator.setPositionAndOffset(0,0);
            }
        });
        //start the auto-rolling task if needed
        if(isAutoRolling){
            postDelayed(mAutoRollingTask,mAutoRollingTime);
        }

    }
    /**
     *
     * @param indicatorView 根布局是线性布局
     * @param bottomMargin
     * @param gravity
     */
    public void setIndicator(IndicatorView indicatorView,int bottomMargin,int gravity)
    {
        this.mIndicatorView=indicatorView;
        LayoutParams indicatorlp = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        gravity=gravity<0?Gravity.BOTTOM | Gravity.CENTER:gravity;
        indicatorlp.gravity = gravity;
        indicatorlp.bottomMargin = bottomMargin;//20
        mIndicatorView.setLayoutParams(indicatorlp);
        addView(mIndicatorView);
    }
    /**
     * This runnable decides the viewpager should roll to next page or wait.
     */
    private Runnable mAutoRollingTask = new Runnable() {
        @Override
        public void run() {
            int now = (int) System.currentTimeMillis();
            int timediff = mAutoRollingTime;
            if(mReleasingTime != 0){
                timediff = now - mReleasingTime;
            }

            if(mViewPagerScrollState == ViewPager.SCROLL_STATE_IDLE){
                //if user's finger just left the screen,we should wait for a while.
                if(timediff >= mAutoRollingTime * 0.8){
                    //0,1,2,0
                    if(mCurrentPosition == mAdapter.getCount() - 1){
                        mViewPager.setCurrentItem(0,true);
                    }else {
                        mViewPager.setCurrentItem(mCurrentPosition + 1,true);
                    }
                    postDelayed(mAutoRollingTask,mAutoRollingTime);
                }else {
                    postDelayed(mAutoRollingTask,mAutoRollingTime);
                }
            }else if(mViewPagerScrollState == ViewPager.SCROLL_STATE_DRAGGING){
                postDelayed(mAutoRollingTask,mAutoRollingTime);
            }

        }
    };
    @Override
    public void onPageScrollStateChanged(int state) {
        if(state == ViewPager.SCROLL_STATE_DRAGGING){//拖拽滑动状态
            mViewPagerScrollState = ViewPager.SCROLL_STATE_DRAGGING;
        }else if(state == ViewPager.SCROLL_STATE_IDLE){//空闲状态
            mReleasingTime = (int) System.currentTimeMillis();
            mViewPagerScrollState = ViewPager.SCROLL_STATE_IDLE;
            //控制页面
            //2,    0,1,2,    0
            if(mCurrentPosition==mIndictorCount+1)//右循环
            {
                mViewPager.setCurrentItem(1,false);
            }
            else if(mCurrentPosition==0)//左循环
            {
                mViewPager.setCurrentItem(mIndictorCount,false);
            }
        }

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        LogSuperUtil.i(Constants.LogTag.theme_recommand,"position="+position
                +",offset="+positionOffset+",positionOffsetPixels="+positionOffsetPixels);
        int indicatorIndex=position-1;//除去左右边界时的逻辑
        //控制指示器
        //右循环
        //2,    0,1,2,    0
        if(position==mIndictorCount)
        {
            if(positionOffset>0.6)//优化UI效果，过半就换点
            {
                indicatorIndex=0;
            }
            positionOffset=0;
        }
        if(position==mIndictorCount+1)//修复点闪了一下（因为最后一个图片翻页时，翻页完毕走了一下position=3,positionOffset=0.0）
        {
            indicatorIndex=0;
        }
        //左循环
        if(position==0)//只要第二个页面一往左滑，position立刻就变成0了，这么神奇？
        {
            indicatorIndex=0;//需要手动设置
            if(positionOffset<0.4)//优化UI效果，过半换点
            {
                indicatorIndex=mIndictorCount-1;
            }
            positionOffset=0;
        }
        setIndicator(indicatorIndex,positionOffset);
    }

    @Override
    public void onPageSelected(int position) {
        mCurrentPosition = position;
    }
    /**
     * If the current page is being dragged by the user,this method will be invoke.
     * And then it will call {@link ViewPagerIndicator#setPositionAndOffset}.
     * @param indicatorIndex Position index of the first page currently being displayed.
     * @param offset Value from [0, 1) indicating the offset from the page at position.
     */
    private void setIndicator(int indicatorIndex,float offset){
        LogSuperUtil.i(Constants.LogTag.theme_recommand,"indicatorIndex="+indicatorIndex+",offset="+offset);
        mIndicatorView.setPositionAndOffset(indicatorIndex,offset);
    }
    /**
     * 每当当前窗口被隐藏、至于后台时，Runnable就会停止并被取消，防止内存泄漏。
     * @param visibility
     */
    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (!isAutoRolling) return;
        if (visibility != VISIBLE){
            Log.d("cylog","remove callbacks");
            removeCallbacks(mAutoRollingTask);
        }else{
            if (isFirstVisible){
                isFirstVisible = false;
            }else {
                Log.d("cylog","post runnable");
                postDelayed(mAutoRollingTask,mAutoRollingTime);
            }
        }


    }

    /**
     * Save the state of this BannerViewPager.The current position will be saved.
     * @return Parcelable
     */
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        SavedState ss = new SavedState(parcelable);
        ss.currentPosition = mCurrentPosition;
        return ss;
    }

    /**
     * Restore the BannerViewPager from the previous state.
     * @param state
     */
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        mViewPager.setCurrentItem(ss.currentPosition);
    }

    static class SavedState extends BaseSavedState{

        int currentPosition;

        public SavedState(Parcel source) {
            super(source);
            currentPosition = source.readInt();
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(currentPosition);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>(){

            @Override
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
