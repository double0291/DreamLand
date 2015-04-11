package com.dreamland.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dreamland.R;
import com.dreamland.base.BaseActivity;
import com.dreamland.database.entity.VideoBrief;
import com.dreamland.util.DisplayUtil;

import java.util.ArrayList;

public class ListActivity extends BaseActivity implements View.OnClickListener {
    private static final int TOP_MARGIN = 50, LEFT_MARGIN = 30;

    private LinearLayout mContainer;
    int mScreenHeight;
    int mCardHeight, mCardWidth;
    ArrayList<VideoBrief> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_list);

        initView();
        initData();
        updateView();
    }

    private void initView() {
        mContainer = (LinearLayout) findViewById(R.id.container);
        mScreenHeight = DisplayUtil.getScreenHeightInPx(this);
        mCardHeight = mScreenHeight - 2 * DisplayUtil.dp2px(TOP_MARGIN, getResources());
        mCardWidth = 1 * mCardHeight / 2;
    }

    private void initData() {
        mData = new ArrayList<VideoBrief>(10);
        for (int i = 1; i <= 10; i++) {
            VideoBrief vb = new VideoBrief();
            vb.name = "video " + i;
            mData.add(vb);
        }
    }

    private void updateView() {
        LayoutInflater inflater = LayoutInflater.from(this);

        for (VideoBrief vb : mData) {
            RelativeLayout cardView = (RelativeLayout) inflater.inflate(R.layout.card_in_main,
                    null);
            // 设置宽高
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mCardWidth,
                    mCardHeight);
            // 与左边的间隔
            layoutParams.leftMargin = DisplayUtil.dp2px(LEFT_MARGIN, getResources());
            // 与顶部的间隔
            layoutParams.topMargin = DisplayUtil.dp2px(TOP_MARGIN, getResources());

            cardView.setLayoutParams(layoutParams);

            TextView text = (TextView) cardView.findViewById(R.id.text);
            // 加tag，用于点击事件
            text.setTag(vb.getId());
            text.setOnClickListener(ListActivity.this);
            // 添加文字
            text.setText(vb.name);

            mContainer.addView(cardView);
        }
    }

    @Override
    public void onClick(View view) {

    }
}
