package com.demo.demotest;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.demo.demotest.R;
import com.demo.demotest.adapter.ContactAdapterTest;
import com.demo.demotest.base.BaseActivity;
import com.demo.demotest.service.ContactService;
import com.demo.demotest.util.ToastUtil;

public class ListViewAsyncActivity extends BaseActivity implements OnClickListener{
	Context m_context;
	ListView listview;
	File cache;
	private TextView m_tvCount;
	private Button m_btnDeleteImages;
	Handler handler=new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			 List dataList=(List)msg.obj;
			 if(dataList==null)
			 {
				 return;
			 }
			 m_tvCount.setText("共"+dataList.size()+"条信息");
			 listview.setAdapter(new ContactAdapterTest(m_context, dataList, R.layout.item_listview_contact, cache));
		}
	};
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.btn_delete_images:
			deleteImages();
			break;

		default:
			break;
		}
	}
	private void deleteImages() {
		if(cache.exists())
		{
			for(File file:cache.listFiles())
			{
				file.delete();
			}
		}
		ToastUtil.show(m_context, "删除成功");
	}
	@Override
	protected void init() {
		m_context=this;
		setContentView(R.layout.activity_listview_async);
		setCommonTitleTitle("异步加载图片");
	}
	@Override
	protected void initView() {
		listview=(ListView) findViewById(R.id.lv_listview_async);
		m_tvCount = (TextView) findViewById(R.id.tv_count);
		m_btnDeleteImages = (Button) findViewById(R.id.btn_delete_images);
	}
	@Override
	protected void initData() {
		cache=new File(Environment.getExternalStorageDirectory(),"asyncCache");
		if(!cache.exists())
		{
			cache.mkdirs();
		}
		new Thread()
		{
			@Override
			public void run()
			{
				try {
					List data=ContactService.getContactsTest();
					handler.sendMessage(handler.obtainMessage(22, data));
				} catch (Exception e) {
					 throw new RuntimeException(e);
				}
			}
		}.start();
		
	}
	@Override
	protected void initListener() {
		m_btnDeleteImages.setOnClickListener(this);
		setCommonTitleBackListener();
	}
}
