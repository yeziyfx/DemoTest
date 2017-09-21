package com.demo.demotest.view;

import com.demo.demotest.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.RelativeLayout;


public class MyListView extends ListView implements OnTouchListener,OnGestureListener{
	public interface OnItemDeleteListener
	{
		void onItemDelete(int selectedItem);
	}
	private GestureDetector gestureDetector;
	private View btnDelete;
	private ViewGroup viewGroup;
	private int selectedItem;
	private boolean isDeleteShow;
	private OnItemDeleteListener onItemDeleteListener;
	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		gestureDetector=new GestureDetector(getContext(), this);
		setOnTouchListener(this);
	}
	public void setOnItemDeleteListener(OnItemDeleteListener onItemDeleteListener)
	{
		this.onItemDeleteListener=onItemDeleteListener;
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		selectedItem=pointToPosition((int)event.getX(), (int)event.getY());
		if(isDeleteShow)
		{
			btnHide(btnDelete);
			viewGroup.removeView(btnDelete);
			btnDelete=null;
			isDeleteShow=false;
			return false;
		}
		else
		{
			return gestureDetector.onTouchEvent(event);
		}
	}

	@Override
	public boolean onDown(MotionEvent e) {
		if(!isDeleteShow)
		{
			selectedItem=pointToPosition((int)e.getX(), (int)e.getY());
		}
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		if(!isDeleteShow&&Math.abs(velocityX)>Math.abs(velocityY))
		{
			btnDelete=LayoutInflater.from(getContext()).inflate(R.layout.layout_button, null);
			btnDelete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					viewGroup.removeView(btnDelete);
					btnDelete=null;
					isDeleteShow=false;
					onItemDeleteListener.onItemDelete(selectedItem);
					
				}
			});
			viewGroup=(ViewGroup)getChildAt(selectedItem-getFirstVisiblePosition());
			RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
			btnDelete.setLayoutParams(layoutParams);
			viewGroup.addView(btnDelete);
			btnShow(btnDelete);
			isDeleteShow=true;
		}
		else
		{
			setOnTouchListener(this);
		}
		return false;
	}
	private void btnShow(View v)
	{
		v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.btn_show));
	}
	private void btnHide(View v)
	{
		v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.btn_hide));
	}
}
