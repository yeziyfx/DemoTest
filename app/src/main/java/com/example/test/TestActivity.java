package com.example.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.demo.demotest.R;
import com.demo.demotest.base.BaseActivity;
/**
 * 实现对listView的循环滚动 
 * @author gerry
 *
 */
public class TestActivity extends BaseActivity implements OnClickListener{

	private ListView listView;
	private List<String> list;
	//
	private TextView m_tvMsg;
   
	@Override
	protected void init() {
	     setContentView(R.layout.activity_test);
//       test1();
       testToLong();
	}
	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		m_tvMsg=(TextView) findViewById(R.id.tv_msg);
	}
	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		
	}
	 private void test1()
	    {
	    	 listView = (ListView) findViewById(R.id.listView1);
	         list = getList();
	         new Timer().schedule(new TimeTaskScroll(this, listView,list), 20, 20);
	    }
	    /**
	     * 获取数据
	     * @return
	     */
	    public List<String> getList(){
	    	List<String> list =  new ArrayList<String>();
	    	for (int i = 0; i < 100; i++) {
				list.add(String.valueOf(i));
			}
	    	return list;
	    }
	    private void testToLong()
	    {
	    	long l=Long.parseLong(39.353456+"");
	    	m_tvMsg.setText(l+"");
	    }
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
}
