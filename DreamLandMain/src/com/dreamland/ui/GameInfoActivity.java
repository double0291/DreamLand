package com.dreamland.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dreamland.R;
import com.dreamland.base.BaseActivity;
import com.dreamland.download.DownloadListener;
import com.dreamland.download.DownloadRequest;
import com.dreamland.util.Constants;
import com.dreamland.util.Logger;
import com.dreamland.util.Toaster;

import org.json.JSONObject;

import java.io.File;

public class GameInfoActivity extends BaseActivity implements View.OnClickListener,
        Response.Listener<JSONObject>, Response.ErrorListener, DownloadListener {
    private static enum DownloadStatus {
        PAUSE,
        DOWNLOADING,
        DOWNLOADED;
    }

    ImageView mIconView;
    Button mDownloadBtn;
    ProgressBar mProgressBar;
    TextView mBriefTextView, mScoreTextView;

    Long mId;
    String mDownloadUrl;
    String mFilePath;
    DownloadStatus mDownloadStatus = DownloadStatus.PAUSE;
    int mProgress; // 防止过度刷新progress

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_game_info);

        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelDownload();
    }

    private void initView() {
        mIconView = (ImageView) findViewById(R.id.icon);
        mDownloadBtn = (Button) findViewById(R.id.download);
        mDownloadBtn.setOnClickListener(this);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mBriefTextView = (TextView) findViewById(R.id.brief);
        mScoreTextView = (TextView) findViewById(R.id.score);
    }

    private void initData() {
        mId = getIntent().getLongExtra("id", -1L);
        // 请求视频详情
        JsonObjectRequest request = new JsonObjectRequest(Constants.HttpCmd.GET_GAME_INFO,
                Constants.URL_GET_GAME_INFO + "?id=" + mId, this, this);
        app.mRequestQueue.add(request);
    }

    /**
     * 开始下载游戏
     */
    private void startDownload() {
        if (TextUtils.isEmpty(mDownloadUrl)) {
            Toaster.show(this, R.string.get_game_download_url_error, Toast.LENGTH_SHORT);
            return;
        }

        DownloadRequest request = new DownloadRequest().setDownloadListener(this).setUrl(mDownloadUrl);
        app.mDownloadManager.add(request);
        // UI
        mDownloadBtn.setBackgroundDrawable(null);
        mDownloadBtn.setText(R.string.cancel);
        // status
        mDownloadStatus = DownloadStatus.DOWNLOADING;
    }

    /**
     * 暂停下载
     */
    private void cancelDownload() {
        app.mDownloadManager.cancel(mDownloadUrl);
        // UI
        mDownloadBtn.setBackgroundResource(R.drawable.common_btn_green);
        mDownloadBtn.setText(R.string.download);
        // status
        mDownloadStatus = DownloadStatus.PAUSE;
    }

    /**
     * 安装apk
     */
    private void installApk() {
        if (TextUtils.isEmpty(mFilePath)) {
            Toaster.show(this, R.string.get_apk_error, Toast.LENGTH_SHORT);
        }
        Uri uri = Uri.fromFile(new File(mFilePath));
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.download:
                switch (mDownloadStatus) {
                    case PAUSE:
                        startDownload();
                        break;
                    case DOWNLOADING:
                        cancelDownload();
                        break;
                    case DOWNLOADED:
                        installApk();
                        break;
                }
                break;
        }
    }

    @Override
    public void onErrorResponse(Constants.HttpCmd cmd, VolleyError error) {
        Logger.e(error.toString(), false);
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

    @Override
    public void onStart(String downloadUrl, long totalBytes) {

    }

    @Override
    public void onRetry(String downloadUrl) {

    }

    @Override
    public void onProgress(String downloadUrl, long bytesWritten, long totalBytes) {
        int progress = (int) (100 * bytesWritten / totalBytes);
        // progress飙的太慢，防止UI过度
        if (mProgress != progress) {
            Logger.d("download progress: " + progress, false);
            mProgressBar.setProgress(progress);
        }
        mProgress = progress;
    }

    @Override
    public void onSuccess(String downloadUrl, String filePath) {
        Logger.d("download success", false);
        // UI
        mDownloadBtn.setBackgroundResource(R.drawable.common_btn_green);
        mDownloadBtn.setText(R.string.install);
        // status
        mDownloadStatus = DownloadStatus.DOWNLOADED;

        mFilePath = filePath;
    }

    @Override
    public void onFailure(String downloadUrl, int statusCode, String errMsg) {

    }
}
