package com.dreamland.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dreamland.R;
import com.dreamland.base.BaseActivity;
import com.dreamland.util.Constants;
import com.dreamland.util.DisplayUtil;
import com.dreamland.util.Logger;
import com.dreamland.util.SharedPreferencer;

import org.json.JSONObject;

public class MainActivity extends BaseActivity implements View.OnClickListener,
        Response.Listener<JSONObject>, Response.ErrorListener {
    private static final String VIDEO_PIC_TAG = "videoPic";
    private static final String GAME_PIC_TAG = "gamePic";

    LinearLayout mContainer;
    ImageView mVideoImageView, mGameImageView;
    int mScreenWidth, mScreenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();
        sendRequest();
    }

    private void initView() {
        /*
        按比例动态添加card
        2--5--2--5--2--5--2
        */
        mScreenWidth = DisplayUtil.getScreenWidthInPx(this);
        mContainer = (LinearLayout) findViewById(R.id.container);
        int cardWidth = mScreenWidth * 5 / 23;
        int cardHeight = 5 * cardWidth / 3;
        int leftWidth = mScreenWidth * 2 / 23;

        LayoutInflater inflater = LayoutInflater.from(this);

        for (Constants.HOME_CARD card : Constants.HOME_CARD.values()) {
            RelativeLayout cardView = (RelativeLayout) inflater.inflate(R.layout.card_in_main,
                    null);
            // 设置宽高
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(cardWidth,
                    cardHeight);
            // 与左边的间隔
            layoutParams.leftMargin = leftWidth;

            cardView.setLayoutParams(layoutParams);
            // 加tag，用于点击事件
            cardView.setTag(card);
            cardView.setOnClickListener(MainActivity.this);

            ImageView imageView = (ImageView) cardView.findViewById(R.id.image);
            TextView text = (TextView) cardView.findViewById(R.id.text);
            switch (card) {
                case VIDEO: {
                    text.setText(getString(R.string.video));
                    // 先加载缓存图片
                    String videoPic = SharedPreferencer.get(this, VIDEO_PIC_TAG, "");
                    if (!TextUtils.isEmpty(videoPic)) {
                        app.loadImage(imageView, videoPic);
                    }
                    mVideoImageView = imageView;
                    break;
                }
                case GAME: {
                    text.setText(getString(R.string.game));
                    // 先加载缓存图片
                    String gamePic = SharedPreferencer.get(this, VIDEO_PIC_TAG, "");
                    if (!TextUtils.isEmpty(gamePic)) {
                        app.loadImage(imageView, gamePic);
                    }
                    mGameImageView = imageView;
                    break;
                }
                case MINE: {
                    text.setText(getString(R.string.mine));
                    imageView.setBackgroundResource(R.drawable.mine);
                    break;
                }
            }
            mContainer.addView(cardView);
        }
    }

    private void sendRequest() {
        // 请求首页的图片链接
        JsonObjectRequest request = new JsonObjectRequest(Constants.HttpCmd.HOME_PAGE, Constants.URL_HOMEPAGE,
                this, this);
        app.mRequestQueue.add(request);
    }

    @Override
    public void onClick(View v) {
        switch ((Constants.HOME_CARD) v.getTag()) {
            case VIDEO: {
                Bundle extras = new Bundle();
                extras.putSerializable("card", Constants.HOME_CARD.VIDEO);
                startActivity(ListActivity.class, extras);
                break;
            }
            case GAME: {
                Bundle extras = new Bundle();
                extras.putSerializable("card", Constants.HOME_CARD.GAME);
                startActivity(ListActivity.class, extras);
                break;
            }
            case MINE: {
                break;
            }

        }
    }

    @Override
    public void onErrorResponse(Constants.HttpCmd cmd, VolleyError error) {
        Logger.d(error.toString(), false);
    }

    @Override
    public void onResponse(Constants.HttpCmd cmd, JSONObject response) {
        Logger.d("cmd: " + cmd + ", response: " + response.toString(), false);
        switch (cmd) {
            case HOME_PAGE:
                // 加载card的图片
                String videoPic = response.optString(VIDEO_PIC_TAG);
                app.loadImage(mVideoImageView, videoPic);
                String gamePic = response.optString(GAME_PIC_TAG);
                app.loadImage(mGameImageView, gamePic);
                // 保存起来，下次进入的时候先读缓存
                SharedPreferencer.save(this, VIDEO_PIC_TAG, videoPic);
                SharedPreferencer.save(this, GAME_PIC_TAG, gamePic);
                break;
        }
    }

}
