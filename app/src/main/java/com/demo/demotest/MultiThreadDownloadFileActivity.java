package com.demo.demotest;

import java.io.File;
import java.net.URL;
import java.net.URLConnection;

import com.demo.demotest.R;
import com.demo.demotest.util.FileDownloadThread;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
/**
 *  Copyright (C) 2010 ideasandroid
 *  演示android多线程下载
 *  欢迎访问http://www.ideasandroid.com
 *  让程序开发不再那么神秘
 */
public class MultiThreadDownloadFileActivity extends Activity {
 
    private EditText downloadUrl;
    private EditText downloadFileName;
    private EditText downloadThreadNum;
    private Button downloadBt;
    private ProgressBar downloadProgressBar;
    private TextView progressMessage;
    private int downloadedSize = 0;
    private int fileSize = 0;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_thread_download_filel);
 
        downloadUrl = (EditText) findViewById(R.id.et_downloadUrl);
        downloadFileName = (EditText) findViewById(R.id.et_downloadFileName);
        downloadThreadNum = (EditText) findViewById(R.id.et_downloadThreadNum);
        progressMessage = (TextView) findViewById(R.id.tv_progressMessage);
        downloadBt = (Button) findViewById(R.id.btn_download_multi_thread);
        downloadProgressBar = (ProgressBar) findViewById(R.id.pb_downloadProgressBar);
        downloadProgressBar.setVisibility(View.VISIBLE);
        downloadProgressBar.setMax(100);
        downloadProgressBar.setProgress(0);
        initData();
        downloadBt.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                download();
            }
        });
    }
    private void initData()
    {
    	downloadUrl.setText(R.string.url_download_default_parent);
    	downloadFileName.setText("test.apk");
    	downloadThreadNum.setText("5");
    }
    private void download() {
        // 获取SD卡目录
        String dowloadDir = Environment.getExternalStorageDirectory()
                + "/1download_apk/";
        File file = new File(dowloadDir);
        //创建下载目录
        if (!file.exists()) {
            file.mkdirs();
        }
 
        //读取下载线程数，如果为空，则单线程下载
        int downloadTN = Integer.valueOf("".equals(downloadThreadNum.getText()
                .toString()) ? "1" : downloadThreadNum.getText().toString());
        //如果下载文件名为空则获取Url尾为文件名
        int fileNameStart = downloadUrl.getText().toString().lastIndexOf("/");
        String fileName = "".equals(downloadFileName.getText().toString()) ? downloadUrl
                .getText().toString().substring(fileNameStart)
                : downloadFileName.getText().toString();
        //开始下载前把下载按钮设置为不可用
        downloadBt.setClickable(false);
        //进度条设为0
        downloadProgressBar.setProgress(0);
        //启动文件下载线程
        new downloadTask(downloadUrl.getText().toString(), Integer
                .valueOf(downloadTN), dowloadDir + fileName).start();
    }
 
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //当收到更新视图消息时，计算已完成下载百分比，同时更新进度条信息
            int progress = (Double.valueOf((downloadedSize * 1.0 / fileSize * 100))).intValue();
            if (progress == 100) {
                downloadBt.setClickable(true);
                progressMessage.setText("下载完成！");
            } else {
                progressMessage.setText("当前进度:" + progress + "%");
            }
            downloadProgressBar.setProgress(progress);
        }
 
    };
 
    /**
     * @author ideasandroid
     * 主下载线程
     */
    public class downloadTask extends Thread {
        private int blockSize, downloadSizeMore;
        private int threadNum = 5;
        String urlStr, threadNo, fileName;
 
        public downloadTask(String urlStr, int threadNum, String fileName) {
            this.urlStr = urlStr;
            this.threadNum = threadNum;
            this.fileName = fileName;
        }
 
        @Override
        public void run() {
            FileDownloadThread[] fds = new FileDownloadThread[threadNum];
            try {
                URL url = new URL(urlStr);
                URLConnection conn = url.openConnection();
                //获取下载文件的总大小
                fileSize = conn.getContentLength();
                //计算每个线程要下载的数据量
                blockSize = fileSize / threadNum;
                // 解决整除后百分比计算误差
                downloadSizeMore = (fileSize % threadNum);
                File file = new File(fileName);
                for (int i = 0; i < threadNum; i++) {
                    //启动线程，分别下载自己需要下载的部分
                    FileDownloadThread fdt = new FileDownloadThread(url, file,
                            i * blockSize, (i + 1) * blockSize - 1);
                    fdt.setName("Thread" + i);
                    fdt.start();
                    fds[i] = fdt;
                }
                boolean finished = false;
                while (!finished) {
                    // 先把整除的余数搞定
                    downloadedSize = downloadSizeMore;
                    finished = true;
                    for (int i = 0; i < fds.length; i++) {
                        downloadedSize += fds[i].getDownloadSize();
                        if (!fds[i].isFinished()) {
                            finished = false;
                        }
                    }
                    //通知handler去更新视图组件
                    handler.sendEmptyMessage(0);
                    //休息1秒后再读取下载进度
                    sleep(1000);
                }
            } catch (Exception e) {
 
            }
 
        }
    }
}
