package com.demo.demotest.view;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by KEYIKEYI-Android-2 on 2016/8/26.
 */
public class LinkTouchMovementMethod extends LinkMovementMethod {
    private TouchableSpan mPressedSpan;

    @Override
    public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mPressedSpan = getPressedSpan(textView, spannable, event);
            if (mPressedSpan != null) {
                mPressedSpan.setPressed(true);
                Selection.setSelection(spannable, spannable.getSpanStart(mPressedSpan), spannable.getSpanEnd(mPressedSpan));
            }
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            TouchableSpan touchedSpan = getPressedSpan(textView, spannable, event);
            if (mPressedSpan != null && touchedSpan != mPressedSpan) {
                mPressedSpan.setPressed(false);
                mPressedSpan = null;
                Selection.removeSelection(spannable);
            }
        } else {
            if (mPressedSpan != null) {
                mPressedSpan.setPressed(false);
                super.onTouchEvent(textView, spannable, event);
            }
            mPressedSpan = null;
            Selection.removeSelection(spannable);
        }
        return true;
    }

    private TouchableSpan getPressedSpan(TextView textView, Spannable spannable, MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();

        x -= textView.getTotalPaddingLeft();
        y -= textView.getTotalPaddingTop();

        x += textView.getScrollX();
        y += textView.getScrollY();

        Layout layout = textView.getLayout();
        int line = layout.getLineForVertical(y);
        int off = layout.getOffsetForHorizontal(line, x);

        TouchableSpan[] link = spannable.getSpans(off, off, TouchableSpan.class);
        TouchableSpan touchedSpan = null;
        if (link.length > 0) {
            touchedSpan = link[0];
        }
        return touchedSpan;
    }

    private abstract class TouchableSpan extends ClickableSpan {
        private boolean mIsPressed;
        private int mPressedBackgroundColor;
        private int mNormalTextColor;
        private int mPressedTextColor;

        public TouchableSpan(int normalTextColor, int pressedTextColor, int pressedBackgroundColor) {
            mNormalTextColor = normalTextColor;
            mPressedTextColor = pressedTextColor;
            mPressedBackgroundColor = pressedBackgroundColor;
        }

        public void setPressed(boolean isSelected) {
            mIsPressed = isSelected;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(mIsPressed ? mPressedTextColor : mNormalTextColor);
            ds.bgColor = mIsPressed ? mPressedBackgroundColor : 0xffeeeeee;
            ds.setUnderlineText(false);
        }
    }
}
