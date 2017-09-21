package com.demo.demotest;

import java.io.File;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.demo.demotest.base.BaseActivity;
import com.demo.demotest.util.SDCardUtil;

/**
 * 
 * 项目名称: DemoBaseTest<br/>
 * 类名称: VideoPlayerActivity<br/>
 * 描述: 播放视频
 *
 * @author:yefx
 * @Date:2016-3-14上午10:46:33
 */
public class VideoPlayerActivity extends BaseActivity implements OnClickListener{

	private Button m_btnSystem;
	private Button m_btnAndroid;
	private Button m_btnMediaPlayer;

	@Override
	protected void init() {
		setContentView(R.layout.activity_video_player);
	}

	@Override
	protected void initView() {
		m_btnSystem = (Button) findViewById(R.id.btn_play_by_system);
		m_btnAndroid = (Button) findViewById(R.id.btn_play_by_android);
		m_btnMediaPlayer = (Button) findViewById(R.id.btn_play_by_mediaplayer);
	}

	@Override
	protected void initData() {
		setCommonTitleTitle("播放视频");
	}

	@Override
	protected void initListener() {
		setCommonTitleBackListener();
		m_btnSystem.setOnClickListener(this);
		m_btnAndroid.setOnClickListener(this);
		m_btnMediaPlayer.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_play_by_system:
			playBySystem();
			break;
		case R.id.btn_play_by_android:
			playByAndroid();
			break;
		case R.id.btn_play_by_mediaplayer:
			playByMediaPlayer();
			break;

		default:
			showToast("无效操作");
			break;
		}
		
	}
//	源文件路径=/mnt/sdcard/DCIM/Camera/VID_20160307_172820.3gp,网络缩略图路径=http://123.57.64.222:6002/upload/photo/20160307052802.jpeg,
//		网络文件路径=http://123.57.64.222:6002/upload/video/20160307052818.mp4
	/**
	 * 
	 * 功能: 使用手机上的播放器播放(支持网络和本地播放)
	 * @author: yefx
	 * @date:2016-3-14上午11:23:51
	 */
	private void playBySystem()
	{
//		Uri uri = Uri.parse(SDCardUtil.getStorageRootFilePath("/test_video.flv"));     
		Uri uri = Uri.parse("http://123.57.64.222:6002/upload/video/20160307052818.mp4");     
		//调用系统自带的播放器    
		    Intent intent = new Intent(Intent.ACTION_VIEW);    
		    intent.setDataAndType(uri, "video/mp4");    
		    startActivity(intent);
	}
	/**
	 * 
	 * 功能: 使用Android控件进行播放
	 * @author: yefx
	 * @date:2016-3-14下午3:38:07
	 */
	private void playByAndroid()
	{
		Uri uri = Uri.parse("http://123.57.64.222:6002/upload/video/20160307052818.mp4");    
		VideoView videoView = (VideoView)this.findViewById(R.id.videoview_video_player);
		videoView.setVisibility(View.VISIBLE);
		//添加控制条
		videoView.setMediaController(new MediaController(this));    
		videoView.setVideoURI(uri);    
		videoView.start();    
		videoView.requestFocus();
	}
	/**
	 * 
	 * 功能:使用MediaPlayer进行播放 
	 * @author: yefx
	 * @date:2016-3-14下午3:38:58
	 */
	private void playByMediaPlayer()
	{
		toActivity(SurfaceViewActivity.class);
	}
}
