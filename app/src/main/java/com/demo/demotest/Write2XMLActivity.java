package com.demo.demotest;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.demo.demotest.R;
import com.demo.demotest.base.BaseActivity;
import com.demo.demotest.util.HttpUtils;
import com.demo.demotest.util.LogUtil;
import com.demo.demotest.util.ResPathCenter;

/**
 * 
 * 项目名称: DemoBaseTest<br/>
 * 类名称: Write2XMLActivity<br/>
 * 描述: 
 *
 * @author:yefx
 * @Date:2015-6-5下午5:49:46
 */
public class Write2XMLActivity extends BaseActivity implements OnClickListener{

	private TextView m_tvResult;
	private TextView m_tvPath;
	private Button m_btnCreateXML;
	
	@Override
	protected void init() {
		setContentView(R.layout.activity_write2xml);
	}

	@Override
	protected void initView() {
		m_tvResult = (TextView) findViewById(R.id.tv_write2xml);
		m_tvPath= (TextView) findViewById(R.id.tv_path_create_xml);
		m_btnCreateXML = (Button) findViewById(R.id.btn_create_xml);
		setCommonTitleTitle("生成XML");
	}

	@Override
	protected void initData() {
		
	}

	@Override
	protected void initListener() {
		setCommonTitleBackListener();
		m_btnCreateXML.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btn_create_xml:
			createXML("list.xml");
			break;

		default:
			break;
		}
	}
	private void createXML(String fileName)
	{
		ResPathCenter center=ResPathCenter.getInstance();
		String path=center.getSDCardFilePath(fileName);
		m_tvPath.setText(path);
		try {
			XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
			XmlSerializer serializer=factory.newSerializer();
			OutputStream out=new FileOutputStream(path);
			serializer.setOutput(out, "utf-8");
			
			serializer.startDocument("utf-8", true);
			serializer.startTag(null,"contacts");
			String[] imageNames=getImageNames();
			for(int i=0;i<imageNames.length;i++)
			{
				serializer.startTag(null,"contact");
				serializer.attribute(null,"id", (i+1)+"");
				
				serializer.startTag(null, "name");
				serializer.text("图片"+(i+1));
				serializer.endTag(null, "name");
				
				serializer.startTag(null, "img");
				serializer.attribute(null, "src", HttpUtils.getServerImageDir()+imageNames[i]);
				serializer.endTag(null, "img");
				
				serializer.endTag(null, "contact");
			}
			serializer.endTag(null, "contacts");
			serializer.endDocument();
			out.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	private String[] getImageNames()
	{
		String[] imageNames=null;
		try {
			imageNames=getResources().getAssets().list("images");
			m_tvResult.setText(imageNames.length);
		} catch (Exception e) {
			LogUtil.i("exception", "getImageNames-出错啦");
			throw new RuntimeException(e);
		}
		return imageNames;
	}
	
}
