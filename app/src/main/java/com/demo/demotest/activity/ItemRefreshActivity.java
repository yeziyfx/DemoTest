package com.demo.demotest.activity;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.demo.demotest.R;
import com.demo.demotest.adapter.ItemRefreshAdapter;
import com.demo.demotest.base.BaseActivity;
import com.demo.demotest.domain.MessageContentBean;
import com.demo.demotest.util.LogSuperUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sun on 2017/11/10 8:38
 */
public class ItemRefreshActivity extends BaseActivity implements View.OnClickListener{
    private Handler mHandler=new Handler()
    {
        private int mSendLen;
        @Override
        public void handleMessage(Message msg)
        {
            int index=msg.arg1;
            LogSuperUtil.i("mychar","index="+index);
            MessageContentBean bean=mDataList.get(index);
            bean.sendLen=mSendLen;
            bean.totalLen=100;
            refreshItem(index);
            mSendLen+=10;
            if(mSendLen<100)
            {
                refreshPercent();
            }
            else {
                bean.sendLen=100;
                refreshItem(index);
            }
        }
    };
    private ListView mLv;
    private List<MessageContentBean> mDataList;
    private ItemRefreshAdapter mAdapter;
    private TextView mTvUpload;
    @Override
    protected void init() {
        setContentView(R.layout.activity_item_refresh);
    }
    @Override
    protected void initView() {
        setCommonTitleTitle("某个条目刷新");
        mLv = (ListView) findViewById(R.id.lv_item_refresh);
        mTvUpload = (TextView) findViewById(R.id.tv_upload_item_refresh);
    }
    @Override
    protected void initData() {
        mDataList=new ArrayList<>();
        mAdapter=new ItemRefreshAdapter(m_context,mDataList);
        mLv.setAdapter(mAdapter);
        getData();
    }
    private void getData()
    {
        MessageContentBean bean;
        for (int i=0;i<25;i++)
        {
            bean=new MessageContentBean();
            bean.content="我只是个衬托";
            mDataList.add(bean);
        }
        notifyDataChanged();
    }
    @Override
    protected void initListener() {
        setCommonTitleBackListener();
        mTvUpload.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tv_upload_item_refresh:
                refreshPercent();
                break;
        }
    }
    private void refreshPercent()
    {
        Message message=mHandler.obtainMessage();
        message.arg1=mDataList.size()-1;
        mHandler.sendMessageDelayed(message,2*1000);
    }
    private void notifyDataChanged()
    {
        mAdapter.notifyDataSetChanged();
    }
    private void refreshItem(int position)
    {
//        mAdapter.getView(position,mLv.getChildAt(position),mLv);
        int firstPos=mLv.getFirstVisiblePosition();
        int lastPos=mLv.getLastVisiblePosition();
        LogSuperUtil.i("mychar","firstPos="+firstPos+",lastPos="+lastPos);
        int viewIndex=position;
        if(position>firstPos&&position<=lastPos)
        {
            viewIndex=position-firstPos;
        }
        View itemView=mLv.getChildAt(viewIndex);
        mAdapter.updateItemView(itemView,position);
    }
}
