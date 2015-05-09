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

public class GameInfoActivity extends BaseActivity implements View.OnClickListener,
        Response.Listener<JSONObject>, Response.ErrorListener {
    ImageView mIconView;
    Button mDownloadBtn;
    TextView mBriefTextView, mScoreTextView;

    Long mId;
    String mDownloadUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_game_info);

        initView();
        initData();
    }

    private void initView() {
        mIconView = (ImageView) findViewById(R.id.icon);
        mDownloadBtn = (Button) findViewById(R.id.download);
        mDownloadBtn.setOnClickListener(this);
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
        JsonObjectRequest request = new JsonObjectRequest(Constants.HttpCmd.GET_GAME_INFO,
                Constants.URL_GET_GAME_INFO + "?id=" + mId, this, this);
        app.mRequestQueue.add(request);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.download:
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

        double score = response.optDouble("score", 0.0);
        mScoreTextView.setText(score + "");
        String brief = response.optString("detail");
        mBriefTextView.setText(brief);
        String picUrl = response.optString("pic");
        app.loadImage(mIconView, picUrl);
        mDownloadUrl = response.optString("fileLocation");
    }

}
