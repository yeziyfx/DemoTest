package com.demo.demotest.adapter;

import java.util.List;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.demo.demotest.R;
import com.demo.demotest.base.BaseCommontAdapter;

public class ListViewEditTextAdpater extends BaseCommontAdapter<String> {

	private int index = -1;

	public ListViewEditTextAdpater(Context context, List<String> dataList) {
		super(context, dataList);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getItemView(Context context, final int position, View converntView, ViewGroup parent, List<String> dataList) {
		ViewHolder vh;
		if (converntView == null) {
			vh = new ViewHolder();
			converntView = View.inflate(context, R.layout.item_listview_listview_edittext, null);
			vh.tvOrderNum = (TextView) converntView.findViewById(R.id.tv_order_num_item_edittext);
			vh.etTest = (EditText) converntView.findViewById(R.id.et_item_edittext);
			converntView.setTag(vh);
		} else {
			vh = (ViewHolder) converntView.getTag();
		}
		vh.tvOrderNum.setText("" + (position + 1));
		vh.etTest.setTag(position);
		vh.etTest.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View view, MotionEvent event) {

				// 在TOUCH的UP事件中，要保存当前的行下标，因为弹出软键盘后，整个画面会被重画

				// 在getView方法的最后，要根据index和当前的行下标手动为EditText设置焦点

				if (event.getAction() == MotionEvent.ACTION_UP) {

					index = position;

				}

				return false;

			}


		});
//		vh.etTest.clearFocus();
//
//		if (index != -1 && index == position) {
//
//			// 如果当前的行下标和点击事件中保存的index一致，手动为EditText设置焦点。
//
//			vh.etTest.requestFocus();
//
//		}

		if (index!=-1&&index==1&&(Integer) (vh.etTest.getTag()) == 1) {
			vh.etTest.requestFocus();
		} else {
			vh.etTest.clearFocus();
		}
		return converntView;
	}

	class ViewHolder {

		TextView tvOrderNum;
		EditText etTest;
	}
}
