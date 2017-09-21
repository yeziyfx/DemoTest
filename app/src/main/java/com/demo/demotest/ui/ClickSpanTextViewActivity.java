package com.demo.demotest.ui;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.demo.demotest.R;
import com.demo.demotest.adapter.ClickSpanTextViewAdapter;
import com.demo.demotest.base.BaseActivity;
import com.demo.demotest.domain.ReplyMessageEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KEYIKEYI-Android-2 on 2016/8/25.
 */
public class ClickSpanTextViewActivity extends BaseActivity implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {
    private ListView mLv;
    private List<ReplyMessageEntity> mDataList;
    private ClickSpanTextViewAdapter mAdapter;
    @Override
    protected void init() {
        setContentView(R.layout.activity_click_span_textview);
    }
    @Override
    protected void initView() {
        mLv = (ListView) findViewById(R.id.lv_click_span_textview);
        setCommonTitleTitle("可点击部分文字的TextView");
    }
    @Override
    protected void initData() {
        mDataList = new ArrayList<>();
        mAdapter = new ClickSpanTextViewAdapter(m_context,mDataList);
        mLv.setAdapter(mAdapter);
        mDataList.addAll(getData());
        notifyDataChanged();
    }
    @Override
    protected void initListener() {
        mLv.setOnItemClickListener(this);
        mLv.setOnItemLongClickListener(this);
    }
    private List<ReplyMessageEntity> getData()
    {
        List<ReplyMessageEntity> dataList=new ArrayList<>();
        for(int i=0;i<30;i++)
        {
            ReplyMessageEntity replyMessageEntity=new ReplyMessageEntity();
            replyMessageEntity.uid=i+1;
            replyMessageEntity.name="iphone"+(i+1);

            replyMessageEntity.replyUid=i+2;
            replyMessageEntity.replyName="iphone"+(i+2);

            replyMessageEntity.replyMsg="我要买iphone"+(i+3);

            dataList.add(replyMessageEntity);
        }
        return dataList;
    }
    private void notifyDataChanged()
    {
        mAdapter.notifyDataSetChanged();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ReplyMessageEntity entity=mDataList.get(position);
        showToast("回复"+entity.name+"?");
    }
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        ReplyMessageEntity entity=mDataList.get(position);
        showToast("删除"+entity.name+"给"+entity.replyName+"的回复？");
        return true;
    }
}
