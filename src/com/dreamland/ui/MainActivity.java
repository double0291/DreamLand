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
import com.dreamland.util.Constants;
import com.dreamland.util.DisplayUtil;
import com.dreamland.util.Logger;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    LinearLayout mContainer;
    int mScreenWidth, mScreenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        /*
        按比例动态添加card
        2--5--2--5--2--5--2
        */
        mScreenWidth = DisplayUtil.getScreenWidthInPx(this);
        mContainer = (LinearLayout) findViewById(R.id.container);
        int cardWidth = mScreenWidth * 5 / 23;
        int leftWidth = mScreenWidth * 2 / 23;

        mScreenHeight = DisplayUtil.getScreenHeightInPx(this);
        int cardHeight = 5 * cardWidth / 3;
        int topMargin = (mScreenHeight - cardHeight) / 2;

        LayoutInflater inflater = LayoutInflater.from(this);

        for (Constants.HOME_CARD card : Constants.HOME_CARD.values()) {
            RelativeLayout cardView = (RelativeLayout) inflater.inflate(R.layout.card, null);
            // 设置宽高
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(cardWidth, cardHeight);
            // 与左边的间隔
            layoutParams.leftMargin = leftWidth;
            // 与顶部的间隔
            layoutParams.topMargin = topMargin;

            cardView.setLayoutParams(layoutParams);

            TextView text = (TextView) cardView.findViewById(R.id.text);
            // 加tag，用于点击事件
            text.setTag(card);
            text.setOnClickListener(MainActivity.this);
            //添加文字
            switch (card) {
                case VIDEO:
                    text.setText(getString(R.string.video));
                    break;
                case GAME:
                    text.setText(getString(R.string.game));
                    break;
                case MINE:
                    text.setText(getString(R.string.mine));
                    break;
            }
            mContainer.addView(cardView);
        }
    }

    public void onClick(View v) {
        switch ((Constants.HOME_CARD) v.getTag()) {
            case VIDEO:
                Logger.d("video click", false);
                break;
            case GAME:
                Logger.d("game click", false);
                break;
            case MINE:
                Logger.d("mine click", false);
                break;

        }
    }

}
