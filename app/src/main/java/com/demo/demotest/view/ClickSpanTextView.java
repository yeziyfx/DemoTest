package com.demo.demotest.view;
import android.content.Context;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.Touch;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by KEYIKEYI-Android-2 on 2016/8/25.
 */
public class ClickSpanTextView extends TextView {
    boolean dontConsumeNonUrlClicks = true;
    boolean linkHit;

    public ClickSpanTextView(Context context) {
        super(context);
    }
    public ClickSpanTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean hasFocus() {
        return false;
    }
    private int mStart = -1;
    private int mEnd = -1;
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        boolean result = super.onTouchEvent(event);
//        int action = event.getAction();
//        // if (action == MotionEvent.ACTION_UP || action ==
//        // MotionEvent.ACTION_DOWN) {
//        int x = (int) event.getX();
//        int y = (int) event.getY();
//        x -= getTotalPaddingLeft();
//        y -= getTotalPaddingTop();
//        x += getScrollX();
//        y += getScrollY();
//        Layout layout = getLayout();
//        int line = layout.getLineForVertical(y);
//        int off = layout.getOffsetForHorizontal(line, x);
//        CharSequence text = getText();
//        if (TextUtils.isEmpty(text) || !(text instanceof Spannable)) {
//            return result;
//        }
//        Spannable buffer = (Spannable) text;
//        ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);
//        int colorRes= getResources().getColor(R.color.gray_e0);
//        if (link.length != 0) {
//            if (action == MotionEvent.ACTION_DOWN) {
//                mStart = buffer.getSpanStart(link[0]);
//                mEnd = buffer.getSpanEnd(link[0]);
//                if (mStart >= 0 && mEnd >= mStart) {
//                    buffer.setSpan(new BackgroundColorSpan(colorRes), mStart, mEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                }
//            } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
//                if (mStart >= 0 && mEnd >= mStart) {
//                    buffer.setSpan(new BackgroundColorSpan(Color.TRANSPARENT), mStart, mEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    mStart = -1;
//                    mEnd = -1;
//                }
//            }
//            return true;
//        } else {
//            if (mStart >= 0 && mEnd >= mStart) {
//                buffer.setSpan(new BackgroundColorSpan(Color.TRANSPARENT), mStart, mEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                mStart = -1;
//                mEnd = -1;
//            }
//            Selection.removeSelection(buffer);
//            return false;
//        }
//    }

    public static class LocalLinkMovementMethod extends LinkMovementMethod {
        static LocalLinkMovementMethod sInstance;


        public static LocalLinkMovementMethod getInstance() {
            if (sInstance == null)
                sInstance = new LocalLinkMovementMethod();

            return sInstance;
        }

        @Override
        public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
            int action = event.getAction();

            if (action == MotionEvent.ACTION_UP ||
                    action == MotionEvent.ACTION_DOWN) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                x -= widget.getTotalPaddingLeft();
                y -= widget.getTotalPaddingTop();

                x += widget.getScrollX();
                y += widget.getScrollY();

                Layout layout = widget.getLayout();
                int line = layout.getLineForVertical(y);
                int off = layout.getOffsetForHorizontal(line, x);

                ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);

                if (link.length != 0) {
                    if (action == MotionEvent.ACTION_UP) {
                        link[0].onClick(widget);
                    } else if (action == MotionEvent.ACTION_DOWN) {
                        Selection.setSelection(buffer, buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]));
                    }

                    if (widget instanceof ClickSpanTextView){
                        ((ClickSpanTextView) widget).linkHit = true;
                    }
                    return true;
                } else {
                    Selection.removeSelection(buffer);
                    Touch.onTouchEvent(widget, buffer, event);
                    return false;
                }
            }
            return Touch.onTouchEvent(widget, buffer, event);
        }
    }
}
