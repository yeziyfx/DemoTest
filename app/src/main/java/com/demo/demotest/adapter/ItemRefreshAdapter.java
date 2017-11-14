package com.demo.demotest.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.demotest.R;
import com.demo.demotest.base.BaseCommontAdapter;
import com.demo.demotest.domain.MessageContentBean;
import com.demo.demotest.util.LogSuperUtil;

import java.util.List;

/**
 * Created by sun on 2017/11/10 8:41
 */
public class ItemRefreshAdapter extends BaseCommontAdapter<MessageContentBean> {
    public ItemRefreshAdapter(Context context, List<MessageContentBean> dataList) {
        super(context, dataList);
    }
    @Override
    public View getItemView(Context context, int position, View converntView, ViewGroup parent, List<MessageContentBean> dataList) {

        ViewHolder vh;
        if(converntView==null)
        {
            vh=new ViewHolder();
            converntView=View.inflate(m_context, R.layout.item_listview_item_refresh,null);
            vh.tvContent= (TextView) converntView.findViewById(R.id.tv_content_item_item_refresh);
            vh.tvPercent= (TextView) converntView.findViewById(R.id.tv_percent_item_item_refresh);
            converntView.setTag(vh);
        }
        else {
            vh= (ViewHolder) converntView.getTag();
        }
        fillData(vh,position);
        return converntView;
    }
    private static class ViewHolder
    {
        public TextView tvContent;
        public TextView tvPercent;
    }
    public void updateItemView(View itemView,int position)
    {
        if(itemView==null)
        {
            LogSuperUtil.i("mychar","position="+position+",视图为null");
            return;
        }
        TextView tvPercent= (TextView) itemView.findViewById(R.id.tv_percent_item_item_refresh);
        MessageContentBean bean=m_dataList.get(position);
        float percent=0;
        if(bean.totalLen>0)
        {
            percent=(bean.sendLen*1.0f)/(bean.totalLen*1.0f);
        }
        LogSuperUtil.i("mychar","position="+position+",percent="+percent);
        tvPercent.setText(percent*100+"%");
//        ViewHolder vh= (ViewHolder) itemView.getTag();
//        fillData(vh,position);
    }
    private void fillData(ViewHolder vh,int position)
    {
        MessageContentBean bean=m_dataList.get(position);
        vh.tvContent.setText(bean.content+"--"+position+"--");
        float percent=0;
        if(bean.totalLen>0)
        {
            percent=(bean.sendLen*1.0f)/(bean.totalLen*1.0f);
        }
        LogSuperUtil.i("mychar","position="+position+",percent="+percent);
        vh.tvPercent.setText(percent*100+"%");
    }
}
