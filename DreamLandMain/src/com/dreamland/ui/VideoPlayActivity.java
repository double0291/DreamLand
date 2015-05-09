package com.dreamland.ui;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.dreamland.R;
import com.dreamland.base.BaseActivity;
import com.dreamland.util.Toaster;

public class VideoPlayActivity extends BaseActivity {
	VideoView mVideoView;
	String mPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!LibsChecker.checkVitamioLibs(this))
			return;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_video_play);

		initData();
		initView();
	}
	
	private void initData(){
		mPath = getIntent().getStringExtra("path");
	}

    private void initView() {
        try {
            mVideoView = (VideoView) findViewById(R.id.videoView);
            mVideoView.setVideoPath(mPath);
            mVideoView.setMediaController(new MediaController(this));
            mVideoView.requestFocus();

            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    // optional need Vitamio 4.0
                    mediaPlayer.setPlaybackSpeed(1.0f);
                }
            });
        } catch (Exception e) {
            Toaster.show(this, R.string.vitamio_init_error, Toast.LENGTH_LONG);
        }
    }

}
