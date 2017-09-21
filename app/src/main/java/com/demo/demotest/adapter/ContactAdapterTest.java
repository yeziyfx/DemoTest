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
public class ContactAdapterTest extends BaseAdapter {
	private Context m_context;
	private List<Contact> data;
	private int listviewItem;
	private File cache;
	LayoutInflater layoutInflater;
	private AsyncImageLoader asyncImageLoader;
	private boolean flag = true;

	public ContactAdapterTest(Context context, List<Contact> data,
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
		final Contact contact = data.get(arg0);
		dataWrapper.textView.setText(contact.name);
		dataWrapper.imageView.setTag(contact.imageUrl);
		if (arg0 != 0) {
			LogUtil.i("item", "item-"+arg0+"contact="+contact.toString());
			new AsyncImageTask(dataWrapper.imageView,new CallBack() {

				@Override
				public void loadFinished(ImageView iv,Bitmap result) {
					if (result != null && iv!= null) {
							iv.setImageBitmap(result);
					}
				}
			}).execute(contact.imageUrl);
		} else if (flag) {
			flag = false;
			new AsyncImageTask(dataWrapper.imageView,new CallBack() {

				@Override
				public void loadFinished(ImageView imageView,Bitmap result) {
					if (result != null && imageView != null) {
							imageView.setImageBitmap(result);
					}
				}
			}).execute(contact.imageUrl);
		}
		return arg1;
	}

	class AsyncImageTask extends AsyncTask<String, Void, Bitmap> {
		private String imageUrl;
		private CallBack callBack;
		private ImageView imageView;
		public AsyncImageTask(ImageView imageView,CallBack callBack) {
			this.imageView=imageView;
			this.callBack = callBack;
		}

		@Override
		protected Bitmap doInBackground(String... params) {// 子线程中运行
			Bitmap bitmap = null;
			imageUrl = params[0];
			LogUtil.i("url", "url="+imageUrl);
			try {
				bitmap = asyncImageLoader.getImageBitmap(imageView,imageUrl,
						cache);
			} catch (Exception e) {
				LogUtil.i("exception",
						"AsyncImageTask-doInBackground-出错啦" + e.toString());
			}
			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap result)// 主线程中运行
		{
			callBack.loadFinished(imageView,result);
		}
	}

	interface CallBack {
		void loadFinished(ImageView imageView, Bitmap bitmap);
	}

	class DataWrapper {
		ImageView imageView;
		TextView textView;
	}

}
