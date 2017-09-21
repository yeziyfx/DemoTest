package com.demo.demotest.adapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.demotest.R;
import com.demo.demotest.base.BaseCommontAdapter;

import java.util.List;

/**
 * Created by KEYIKEYI-Android-2 on 2016/8/26.
 */
public class GridViewMenuAdapter extends BaseCommontAdapter<String> {
    private int mPos;
    public GridViewMenuAdapter(Context context, List<String> dataList) {
        super(context, dataList);
    }
    @Override
    public View getItemView(Context context, int position, View converntView, ViewGroup parent, List<String> dataList) {
        String menu=m_dataList.get(position);
        ViewHolder vh;
        if(converntView==null)
        {
            vh=new ViewHolder();
            converntView=View.inflate(m_context, R.layout.item_gridview_gridview_menu,null);
            vh.tv= (TextView) converntView.findViewById(R.id.tv_item_gridview_menu);
            vh.iv=(ImageView)converntView.findViewById(R.id.iv_item_gridview_menu);
            vh.lineRight=converntView.findViewById(R.id.line_right_item_gridview_menu);
            converntView.setTag(vh);
        }
        else {
            vh= (ViewHolder) converntView.getTag();
        }
        vh.tv.setText(menu);
        //控制选择图片的显示和隐藏
        if(mPos==position)
        {
            vh.iv.setVisibility(View.VISIBLE);
        }
        else {
            vh.iv.setVisibility(View.INVISIBLE);
        }
        //控制线的显示和隐藏
        if((position+1)%4==0) //4是因为每行有4列
        {
            vh.lineRight.setVisibility(View.VISIBLE);
        }
        else {
            vh.lineRight.setVisibility(View.GONE);
        }
        return converntView;
    }
    class ViewHolder
    {
        public TextView tv;
        public ImageView iv;
        public View lineRight;
    }
    public void setSelected(int pos)
    {
        this.mPos=pos;
    }
}
