package com.demo.demotest;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.demo.demotest.adapter.GridViewMenuAdapter;
import com.demo.demotest.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KEYIKEYI-Android-2 on 2016/8/26.
 */
public class GridViewMenuActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    private GridView mGv;
    private List<String> mDataList;
    private GridViewMenuAdapter mAdapter;
    @Override
    protected void init() {
        setContentView(R.layout.activity_gridview_menu);
    }
    @Override
    protected void initView() {
        setCommonTitleTitle("GridView菜单");
        mGv = (GridView) findViewById(R.id.gv_gridview_menu);
    }
    @Override
    protected void initData() {
        mDataList = new ArrayList<>();
        mAdapter = new GridViewMenuAdapter(m_context,mDataList);
        mGv.setAdapter(mAdapter);
        mDataList.addAll(getData());
        notifyDataChanged();

        handleClick(0);
    }
    @Override
    protected void initListener() {
        mGv.setOnItemClickListener(this);
        setCommonTitleBackListener();
    }
    private List<String> getData()
    {
        List<String> dataList=new ArrayList<>();
        dataList.add("成长类");
        dataList.add("出版社");
        dataList.add("大奖类");
        dataList.add("感情类");
        dataList.add("情绪类");
        dataList.add("特型类");
        dataList.add("学习类");
        dataList.add("其他");
        return dataList;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        handleClick(position);
    }
    private void handleClick(int position)
    {
        mAdapter.setSelected(position);
        notifyDataChanged();
    }
    private void notifyDataChanged()
    {
        mAdapter.notifyDataSetChanged();
    }

}
