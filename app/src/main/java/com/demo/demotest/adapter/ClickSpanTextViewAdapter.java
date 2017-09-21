package com.demo.demotest.adapter;
import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.demotest.R;
import com.demo.demotest.base.BaseCommontAdapter;
import com.demo.demotest.domain.ReplyMessageEntity;
import com.demo.demotest.util.ToastUtil;
import com.demo.demotest.view.ClickSpanTextView;
import com.demo.demotest.view.LinkTouchMovementMethod;

import java.util.List;

/**
 * Created by KEYIKEYI-Android-2 on 2016/8/25.
 */
public class ClickSpanTextViewAdapter extends BaseCommontAdapter<ReplyMessageEntity> {
    public ClickSpanTextViewAdapter(Context context, List dataList) {
        super(context, dataList);
    }
    @Override
    public View getItemView(Context context, int position, View converntView, ViewGroup parent, List dataList) {
        ReplyMessageEntity entity=m_dataList.get(position);
        ViewHolder vh;
        if(converntView==null)
        {
            converntView=View.inflate(m_context, R.layout.item_listview_click_span_textview,null);
            vh=new ViewHolder();
            vh.tvComment= (ClickSpanTextView) converntView.findViewById(R.id.tv_content_item_click_span);
            converntView.setTag(vh);
        }
        else {
            vh= (ViewHolder) converntView.getTag();
        }
        vh.tvComment.setText(getClickableText(entity));
        vh.tvComment.setMovementMethod(LinkTouchMovementMethod.getInstance());
        return converntView;
    }
    class ViewHolder
    {
        public ClickSpanTextView tvComment;
    }
    private SpannableStringBuilder getClickableText(ReplyMessageEntity replyMessageEntity)
    {
        String link="回复";
        String name=replyMessageEntity.name;
        String replyName=replyMessageEntity.replyName;
        String replyMsg=replyMessageEntity.replyMsg;
        String content=name+link+replyName+":"+replyMsg;
        SpannableStringBuilder ssb=new SpannableStringBuilder(content);
        int color=m_context.getResources().getColor(R.color.color_theme_light);
        int startIndex1=0,endIndex1=name.length();
        int startIndex2=name.length()+link.length();
        int endIndex2=name.length()+link.length()+replyName.length();
        ssb.setSpan(new ForegroundColorSpan(color),startIndex1,endIndex1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(new ForegroundColorSpan(color),startIndex2,endIndex2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ssb.setSpan(new MyClickSpan(replyMessageEntity.uid),startIndex1,endIndex1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(new MyClickSpan(replyMessageEntity.replyUid),startIndex2,endIndex2,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }
    class MyClickSpan extends ClickableSpan
    {
        private int uid;
        public MyClickSpan(int uid)
        {
            this.uid=uid;
        }
        @Override
        public void onClick(View widget) {
            ToastUtil.show(m_context,"uid="+uid);
//            Spannable spannable = (Spannable) ((TextView) widget).getText();
//                        Selection.removeSelection(spannable);
            if(widget instanceof TextView)
            {
                ((TextView)widget).setHighlightColor(m_context.getResources().getColor(R.color.gray_d0));
            }
        }
        @Override
        public void updateDrawState(TextPaint ds) {
//            super.updateDrawState(ds);
            ds.setUnderlineText(false);
        }
    }
}
