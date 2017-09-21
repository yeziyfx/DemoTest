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
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.demo.demotest.HTMLParseActivity.Loadhtml;
import com.demo.demotest.adapter.ExpandableListViewAdapter;
import com.demo.demotest.adapter.HTMLParseAdapter;
import com.demo.demotest.base.BaseActivity;
import com.demo.demotest.domain.FileEntity;
import com.demo.demotest.domain.ImgEntity;
import com.demo.demotest.util.ExceptionUtil;
import com.demo.demotest.util.LogSuperUtil;
import com.demo.demotest.util.ResPathCenter;

/**
 * 
 * 项目名称: DemoBaseTest<br/>
 * 类名称: ExpandableListViewActivity<br/>
 * 描述: 可扩展ListView
 * 
 * @author:yefx
 * @Date:2016-2-18下午3:01:03
 */
public class ExpandableListViewActivity extends BaseActivity implements OnClickListener {

	private Button m_btnParse;
	private ExpandableListView m_exLv;
	private List<ImgEntity> m_dataList;
	private ExpandableListViewAdapter m_adapter;
	private String m_htmlFilePath;
	private String m_htmlFileDir;

	@Override
	protected void init() {
		setContentView(R.layout.activity_expandable_listview);
	}

	@Override
	protected void initView() {
		setCommonTitleTitle("可扩展列表的ListView");
		m_exLv = (ExpandableListView) findViewById(R.id.exlistview_expandable_listview);
		m_btnParse = (Button) findViewById(R.id.btn_expandable_listview);
	}

	@Override
	protected void initData() {
		m_dataList = new ArrayList<ImgEntity>();
		m_adapter = new ExpandableListViewAdapter(m_context, m_dataList);
		m_exLv.setAdapter(m_adapter);

	}

	@Override
	protected void initListener() {
		setCommonTitleBackListener();
		m_btnParse.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_expandable_listview:
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

	private void notifyDataChanged() {
		m_adapter.notifyDataSetChanged();
	}

	// 异步获取信息
	class Loadhtml extends AsyncTask<Void, Void, Void> {

		ProgressDialog bar;
		Document doc;

		@Override
		protected Void doInBackground(Void... params) {
			
			// 需要解析的HTML字符串
			String html = "<p>An <a href='http:// example.com/'><b>example</b></a> link.</p>";
			// 保存到Document对象中
			Document doc = Jsoup.parse(html);
			// 得到第一个a标签的超链接
			Element link = doc.select("a").first();
			// 取出HTML字符串中的文本内容
			// 这里test的值为 An example link
			String text = doc.body().text();
			// 获取属性为href的字符串
			// 这里linkHref的值为"http:// example.com/"
			String linkHref = link.attr("href");
			// 获取a标签内部的纯文本
			// linkText为 "example"
			String linkText = link.text();
			// 获取整个a标签里面的字符串
			// 这里linkOuterH的值为<a href="http:// example.com"><b>example</b></a>
			String linkOuterH = link.outerHtml();
			// 获取a标签内部（不包含a标签）的全部字符串
			// 这里linkInnerH的值为<b>example</b>
			String linkInnerH = link.html();
			
			
			try {
				// doc = Jsoup.connect(NetAddress).timeout(5000).post();
				File in = new File(m_htmlFilePath);
				doc = Jsoup.parse(in, "UTF-8");
				Document content = Jsoup.parse(doc.toString());
				Elements allDivs=content.getAllElements();
				Elements fileDivs=content.getElementsByTag("File");
				Elements imgDivs = content.getElementsByTag("ImgTag");
//				 Document divcontions = Jsoup.parse(divs.toString());
//				 Elements es=divcontions.getElementsByTag("Img");
//				 Elements element = divcontions.getAllElements();
//				LogSuperUtil.i("element", "allDivs divs="+allDivs.toString());
				LogSuperUtil.i("element", "fileDivs divs="+fileDivs.toString());
				LogSuperUtil.i("element", "Img divs="+imgDivs.toString());
				ImgEntity entity;
				FileEntity fileEntity;
				List<FileEntity> fileList;
				m_dataList.clear();
				for (Element e : imgDivs) {
					entity = new ImgEntity();
					entity.productid = e.attr("productid");
					entity.imageName = e.attr("imageName");
//					String contentText=e.html();
//					Elements childDivs =Jsoup.parse(contentText).getAllElements();
					Elements childDivs =e.getElementsByTag("File");

					fileList = new ArrayList<FileEntity>();
					for (Element childDiv : childDivs) {
						fileEntity = new FileEntity();
						fileEntity.id = childDiv.attr("id");
						fileEntity.fileName = childDiv.attr("fileName");
						fileList.add(fileEntity);
					}
					entity.fileList = fileList;
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