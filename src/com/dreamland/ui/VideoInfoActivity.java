package com.dreamland.ui;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.dreamland.R;
import com.dreamland.base.BaseActivity;

public class VideoInfoActivity extends BaseActivity implements View.OnClickListener {
    Button mPlayBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_video_info);

        initView();
    }

    private void initView() {
        mPlayBtn = (Button) findViewById(R.id.play);
        mPlayBtn.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                break;
        }
    }

}
