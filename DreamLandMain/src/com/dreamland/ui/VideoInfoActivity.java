package com.dreamland.ui;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dreamland.R;
import com.dreamland.base.BaseActivity;
import com.dreamland.util.Constants;
import com.dreamland.util.Logger;

import org.json.JSONObject;

public class VideoInfoActivity extends BaseActivity implements View.OnClickListener,
        Response.Listener<JSONObject>, Response.ErrorListener {
    ImageView mImageView;
    Button mPlayBtn;
    TextView mBriefTextView, mScoreTextView;

    Long mId;
    String mVideoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_video_info);

        initView();
        initData();
    }

    private void initView() {
        mImageView = (ImageView) findViewById(R.id.image);
        mPlayBtn = (Button) findViewById(R.id.play);
        mPlayBtn.setOnClickListener(this);
        mBriefTextView = (TextView) findViewById(R.id.brief);
        mScoreTextView = (TextView) findViewById(R.id.score);
        /*
        *DownloadRequest request = new DownloadRequest()
                            .setDownloadListener(this)
                            .setRetryTime(3)
                            .setAllowedNetworkTypes(this, DownloadRequest.NETWORK_WIFI)
                            .setUrl(mUrl);
                    app.mDownloadManager.add(request);
        */
    }

    private void initData() {
        mId = getIntent().getLongExtra("id", -1L);
        // 请求视频详情
        JsonObjectRequest request = new JsonObjectRequest(Constants.HttpCmd.GET_VIDEO_INFO,
                Constants.URL_GET_VIDEO_INFO + "?id=" + mId, this, this);
        app.mRequestQueue.add(request);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                Bundle extras = new Bundle();
                extras.putString("path", mVideoUrl);
                startActivity(VideoPlayActivity.class, extras);
                break;
        }
    }

    @Override
    public void onErrorResponse(Constants.HttpCmd cmd, VolleyError error) {
        Logger.d(error.toString(), false);
    }

    @Override
    public void onResponse(Constants.HttpCmd cmd, JSONObject response) {
        Logger.d("cmd: " + cmd + ", response: " + response.toString(), false);

        double score = response.optDouble("rate");
        mScoreTextView.setText(score + "");
        String brief = response.optString("detail");
        mBriefTextView.setText(brief);
        String picUrl = response.optString("pic");
        app.loadImage(mImageView, picUrl);
        mVideoUrl = response.optString("fileLocation");
    }

}
