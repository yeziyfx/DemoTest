package com.demo.demotest.bannerViewPager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.demo.demotest.R;

/**
 * ViewPagerIndicator helps the user recognize the position of current page.
 * 指示器帮助用户分辨出当前页所在的位置
 *
 * @author Chen Yu
 * @version 1.0.0
 * @date 2016-10-03
 */
public class LoopIndicator extends IndicatorView {
    private static final int DRFAULT_ITEMCOUNT = 5;
    private static final int DEFAULT_RADIUS = 16;
    private static final int DEFAULT_PADDING = 15;

    private Context mContext;
//    private Paint mPaint;
    private View mMoveView;
    private int mCurrentPosition = 0;
    private float mPositionOffset;
    private int mItemCount = DRFAULT_ITEMCOUNT;
    private int mPadding = DEFAULT_PADDING;
    private int mRadius = DEFAULT_RADIUS;
    //the distance from the left side of the previous item to the left side of the next item.
    private int mDistanceBtwItem = mRadius * 2 + mPadding;

    private int mNormalResId=R.drawable.ic_point_normal;
    private int mSelectResId=R.drawable.ic_point_selected;
    private Bitmap mBitmapNormal,mBitmapSelect;
    public LoopIndicator(Context context) {
        this(context, null);
    }

    public LoopIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoopIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        setOrientation(LinearLayout.HORIZONTAL);
        setWillNotDraw(false);
//        mPaint = new Paint();
//        mPaint.setAntiAlias(true);
//        mPaint.setColor(mDefaultColor);
        mBitmapNormal= BitmapFactory.decodeResource(getResources(),mNormalResId);
        mBitmapSelect=BitmapFactory.decodeResource(getResources(),mSelectResId);
        mMoveView = new MoveView(mContext);
        addView(mMoveView);
    }

    public void setItemCount(int count){
        this.mItemCount = count;
        requestLayout();
    }

    public void setRadius(int radius){
        this.mRadius = radius;
        this.mDistanceBtwItem = mRadius * 2 + mPadding;
        requestLayout();
    }

    public void setPadding(int padding){
        this.mPadding = padding;
        this.mDistanceBtwItem = mRadius * 2 + mPadding;
        requestLayout();
    }
    public void setIndicatorNormalResId(int drawableResId)
    {
        mNormalResId=drawableResId;
        mBitmapNormal= BitmapFactory.decodeResource(getResources(),mNormalResId);
    }
    public void setIndicatorSelectResId(int drawableResId)
    {
        mSelectResId=drawableResId;
        mBitmapSelect=BitmapFactory.decodeResource(getResources(),mSelectResId);
    }
    public void setPositionAndOffset(int position,float offset){
        this.mCurrentPosition = position;
        this.mPositionOffset  =offset;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mPadding + (mRadius*2 + mPadding) * mItemCount,2*mRadius + 2*mPadding);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mMoveView.layout(
                (int) (mPadding + mDistanceBtwItem * (mCurrentPosition + mPositionOffset) ),
                mPadding,
                (int) (mDistanceBtwItem * ( 1 + mCurrentPosition + mPositionOffset) ),
                mPadding+mRadius*2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i = 0;i < mItemCount;i++){
            canvas.drawBitmap(mBitmapNormal,mPadding*(i+1)+i*2*mRadius,mPadding,null);
        }

    }

    private class MoveView extends View {
        private Paint mPaintView;

        public MoveView(Context context) {
            super(context);
//            mPaintView = new Paint();
//            mPaintView.setAntiAlias(true);
//            mPaintView.setColor(mSelectColor);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            setMeasuredDimension(mRadius*2,mRadius*2);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawBitmap(mBitmapSelect,0,0,null);
        }
    }
}
