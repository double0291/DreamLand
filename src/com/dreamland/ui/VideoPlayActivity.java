package com.dreamland.ui;

import android.os.Bundle;
import android.view.Window;

import com.dreamland.R;
import com.dreamland.base.BaseActivity;

public class VideoPlayActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_video_play);

        initView();
    }

    private void initView() {
    }


}
