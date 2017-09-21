package com.demo.demotest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.demo.demotest.ExpandableListViewActivity.Loadhtml;
import com.demo.demotest.adapter.HTMLParseAdapter;
import com.demo.demotest.base.BaseActivity;
import com.demo.demotest.domain.HTMLParseEntity;
import com.demo.demotest.domain.ImgEntity;
import com.demo.demotest.util.ExceptionUtil;
import com.demo.demotest.util.LogSuperUtil;
import com.demo.demotest.util.ResPathCenter;
import com.demo.demotest.util.SysConstant;

/**
 * 
 * 项目名称: DemoBaseTest<br/>
 * 类名称: HTMLParseActivity<br/>
 * 描述: HTML解析
 *
 * @author:yefx
 * @Date:2016-2-1下午3:44:55
 */
public class HTMLParseActivity extends BaseActivity implements OnClickListener{

	private Button m_btnParse;
	//http://www.loach.net.cn，http://www.baidu.com
//	private final String NetAddress="http://www.baidu.com";
	
	private ListView m_lv;
	private List<ImgEntity> m_dataList;
	private HTMLParseAdapter m_adapter;
	private String m_htmlFilePath;
	private String m_htmlFileDir;
			
	@Override
	protected void init() {
		setContentView(R.layout.activity_html_parse);

	}

	@Override
	protected void initView() {
		setCommonTitleTitle("HTML解析");
		m_lv = (ListView) findViewById(R.id.listview_html_parse);
		m_btnParse = (Button) findViewById(R.id.btn_parse_html_parse);
//		m_lv.addFooterView(footView, null, true);
//		注意：第三个参数必须为true，否则无效
		//显示头部出现分割线
//		m_lv.setHeaderDividersEnabled(true);
//		   //禁止底部出现分割线 
//		m_lv.setFooterDividersEnabled(false);
	}

	@Override
	protected void initData() {
		m_dataList = new ArrayList<ImgEntity>();
		m_adapter = new HTMLParseAdapter(m_context, m_dataList);
		m_lv.setAdapter(m_adapter);

	}

	@Override
	protected void initListener() {
		setCommonTitleBackListener();
		m_btnParse.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_parse_html_parse:
			parseHTML();
			break;

		default:
			break;
		}
		
	}
	private void parseHTML() {
		m_htmlFileDir = ResPathCenter.getInstance().getHTMLDirPath();
		m_htmlFilePath = m_htmlFileDir + File.separator + "目标.targ";
//		m_htmlFilePath = m_htmlFileDir + File.separator + "html_test.html";
//		m_htmlFilePath = m_htmlFileDir + File.separator + "target.html";
		File htmlFileDir = new File(m_htmlFileDir);
		if (!htmlFileDir.exists()) {
			showToast("资源文件不存在");
			return;
//			htmlFileDir.mkdirs();
//			try {
//				new File(m_htmlFilePath).createNewFile();
//			} catch (Exception e) {
//				ExceptionUtil.handleRuntimeException(e);
//			}
		}
		else
		{
			new Loadhtml().execute();
		}
	}
	private void notifyDataChanged()
	{
		m_adapter.notifyDataSetChanged();
	}
	
	//异步获取信息
    class Loadhtml extends AsyncTask<Void, Void, Void>
    {
        ProgressDialog bar;
        Document doc;
		// <users id="id_users" name="全部用户">
		// <user id="id_user_1" name="小明">
		// </user>
		// <user id="id_user_2" name="大明">
		// </user>
		// <user id="id_user_3" name="中明">
		// </user>
		// </users>
        @Override
        protected Void doInBackground(Void... params ) {
            try {
//                 doc = Jsoup.connect(NetAddress).timeout(5000).post();
            	File in=new File(m_htmlFilePath);
                doc= Jsoup.parse(in, "UTF-8");
                 Document content = Jsoup.parse(doc.toString());
                
                 Elements divs = content.select("Img");
//                 Document divcontions = Jsoup.parse(divs.toString());
//                 Elements element = divcontions.getAllElements();
                 LogSuperUtil.i("element", divs.toString());
                 ImgEntity entity;
                 m_dataList.clear();
                 for(Element e : divs)
                 {
                	 LogSuperUtil.i("element", e.toString());
                	 entity=new ImgEntity();
                     entity.productid=e.attr("productid");
                     entity.imageName=e.attr("imageName");
                     m_dataList.add(entity);
                 }
                
            } catch (IOException e) {
            	ExceptionUtil.handleRuntimeException(e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            bar.dismiss();
           notifyDataChanged();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            m_dataList.clear();
            bar = new ProgressDialog(m_context);
            bar.setMessage("正在加载数据····");
            bar.setIndeterminate(false);
            bar.setCancelable(false);
            bar.show();
        }

        
    }
}
