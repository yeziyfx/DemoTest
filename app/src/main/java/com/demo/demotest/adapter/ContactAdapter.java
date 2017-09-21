package com.demo.demotest.adapter;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract.Contacts.Data;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.demotest.R;
import com.demo.demotest.domain.Contact;
import com.demo.demotest.service.ContactService;
import com.demo.demotest.util.AsyncImageLoader;
import com.demo.demotest.util.LogUtil;
import com.demo.demotest.util.NetImageLoader;

/**
 * 联系人的ListView适配器
 * 
 * @author Administrator
 * 
 */
public class ContactAdapter extends BaseAdapter {
	private Context m_context;
	private List<Contact> data;
	private int listviewItem;
	private File cache;
	LayoutInflater layoutInflater;
	private AsyncImageLoader asyncImageLoader;
	private boolean flag = true;

	public ContactAdapter(Context context, List<Contact> data,
			int listviewItem, File cache) {
		this.m_context = context;
		this.data = data;
		this.listviewItem = listviewItem;
		this.cache = cache;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		asyncImageLoader = new AsyncImageLoader(m_context);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		final DataWrapper dataWrapper;
		if (arg1 == null) {
			arg1 = layoutInflater.inflate(listviewItem, null);
			dataWrapper = new DataWrapper();
			dataWrapper.imageView = (ImageView) arg1
					.findViewById(R.id.iv_item_listview_contact);
			dataWrapper.textView = (TextView) arg1
					.findViewById(R.id.tv_item_listview_contact);
			arg1.setTag(dataWrapper);
		} else {
			dataWrapper = (DataWrapper) arg1.getTag();
		}
		dataWrapper.imageView.setImageResource(R.drawable.default_empty_photo);
		final Contact contact = data.get(arg0);
		dataWrapper.textView.setText(contact.name);
		dataWrapper.imageView.setTag(contact.imageUrl);
		if (arg0 != 0) {
			LogUtil.i("item", "item-"+arg0);
			new NetImageLoader(m_context,cache) {
				
				@Override
				public void loadDown(ImageView imageView, Bitmap bitmap) {
					if(imageView.getTag().equals(contact.imageUrl))
					{
						imageView.setImageBitmap(bitmap);
					}
				}
			}.downLoadImage(dataWrapper.imageView, contact.imageUrl);
		} else if (flag) {
			flag = false;
			LogUtil.i("item", "item-"+arg0);
new NetImageLoader(m_context,cache) {
				
				@Override
				public void loadDown(ImageView imageView, Bitmap bitmap) {
					if(imageView.getTag().equals(contact.imageUrl))
					{
						imageView.setImageBitmap(bitmap);
						bitmap=null;
					}
				}
			}.downLoadImage(dataWrapper.imageView, contact.imageUrl);
		}
		return arg1;
	}
	class DataWrapper {
		ImageView imageView;
		TextView textView;
	}

}
