package com.demo.demotest;

import org.json.JSONArray;
import org.json.JSONObject;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.demo.demotest.R;
import com.demo.demotest.base.BaseActivity;
import com.demo.demotest.util.LogUtil;
import com.demo.demotest.util.ToastUtil;

/**
 * 
 * 项目名称: DemoBaseTest<br/>
 * 类名称: JsonTestActivity<br/>
 * 描述: 
 *
 * @author:yefx
 * @Date:2015-11-20下午2:05:32
 */
public class JsonTestActivity extends BaseActivity{
	private Button m_btn;
	private TextView m_tv;
	@Override
	protected void init() {
		setContentView(R.layout.activity_json_test);
	}

	@Override
	protected void initView() {
		m_btn=(Button) findViewById(R.id.btn_jsonarray_test);
		m_tv=(TextView) findViewById(R.id.tv_jsonarray_test);

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initListener() {
		m_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				test();
				
			}
		});

	}
	private void test()
	{
		m_tv.setText("测试中...");
		String json="{\"datetime\":\"2015-12-01 17:51:12\",\"userinfo\":\"{\\\"IconPath\\\":\\\"http://123.57.218.50:9001/6,02d32bf86a47\\\",\\\"Nikename\\\":\\\"我叫叶飞翔\\\",\\\"Option\\\":\\\"{\\\\\\\"Sessionid\\\\\\\":\\\\\\\"6a6079cdc7c748d7402d8d32dc949a39\\\\\\\",\\\\\\\"headImgUrl\\\\\\\":null}\\\",\\\"Tel\\\":\\\"13666666666\\\",\\\"Uid\\\":\\\"10560\\\",\\\"Username\\\":\\\"yfx2\\\",\\\"Type\\\":1}\",\"direction\":-1.0,\"lat\":39.950329173827605,\"lon\":116.2299873133849,\"speed\":0.0,\"state\":1}";
//		String json="{\"IconPath\":\"http://123.57.218.50:9001/2,02144db99006\",\"Nikename\":叶飞翔,\"Option\":{\"Sessionid\":\"b8bd7698259d43b5c64babfbcbfb86e8\",\"headImgUrl\":null},\"Tel\":\"18210039043\",\"Username\":\"yfx\",\"Uid\":\"10150\",\"Type\":1}";
//		String json="{\"IconPath\":\"http://123.57.218.50:9001/2,02144db99006\",\"Nikename\":\"叶飞翔\",\"Option\":\"test\",\"Tel\":\"18210039043\",\"Username\":\"yfx\",\"Uid\":\"10150\",\"Type\":1}";
//		String json="{\"Option\":\"{\"Sessionid\":\"b8bd7698259d43b5c64babfbcbfb86e8\",\"headImgUrl\":\"http\"}}";
//		String json="{\"option\":{\"sessionid\":\"b8bd7698259d43b5c64babfbcbfb86e8\",\"headImgUrl\":\"http\"}}";
		String result="";
		try {
			JSONObject jsonObj=new JSONObject(json);
			result=jsonObj.getString("userinfo");
		} catch (Exception e) {
			m_tv.setText("测试出异常");
			throw new RuntimeException(e);
		}
		m_tv.setText("测试完成"+result);
	}
}
