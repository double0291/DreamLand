package com.dreamland.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.dreamland.R;
import com.dreamland.base.BaseActivity;
import com.dreamland.database.Entity;
import com.dreamland.database.entity.VideoBrief;
import com.dreamland.util.Constants;
import com.dreamland.util.DisplayUtil;
import com.dreamland.util.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListActivity extends BaseActivity implements View.OnClickListener,
        Response.Listener<JSONArray>, Response.ErrorListener {
    private static final int TOP_MARGIN = 50, LEFT_MARGIN = 30;

    private LinearLayout mContainer;
    int mScreenHeight;
    int mCardHeight, mCardWidth;

    Constants.HOME_CARD mCard;
    ArrayList<Entity> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_list);

        initView();
        initData();
    }

    private void initView() {
        mContainer = (LinearLayout) findViewById(R.id.container);
        mScreenHeight = DisplayUtil.getScreenHeightInPx(this);
        mCardHeight = mScreenHeight - 2 * DisplayUtil.dp2px(TOP_MARGIN, getResources());
        mCardWidth = 3 * mCardHeight / 5;
    }

    private void initData() {
        mCard = (Constants.HOME_CARD) getIntent().getSerializableExtra("card");
        // 请求数据
        switch (mCard) {
            case VIDEO: {
                JsonArrayRequest request = new JsonArrayRequest(Constants.HttpCmd.GET_VIDEO_LIST,
                        Constants.URL_GET_VIDEO_LIST + "?page=0&num=10", this, this);
                app.mRequestQueue.add(request);
                break;
            }
            case GAME: {
                JsonArrayRequest request = new JsonArrayRequest(Constants.HttpCmd.GET_GAME_LIST,
                        Constants.URL_GET_GAME_LIST + "?page=0&num=10", this, this);
                app.mRequestQueue.add(request);
                break;
            }
        }
    }

    private void updateView() {
        LayoutInflater inflater = LayoutInflater.from(this);

        int size = mData.size();
        for (int i = 0; i < size; i++) {
            VideoBrief vb = (VideoBrief) mData.get(i);
            RelativeLayout cardView = (RelativeLayout) inflater.inflate(R.layout.card_in_list, null);
            // 设置宽高
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mCardWidth, mCardHeight);
            // 与左边的间隔
            layoutParams.leftMargin = DisplayUtil.dp2px(LEFT_MARGIN, getResources());
            // 最后一个与右边有间隔
            if (i == size - 1) {
                layoutParams.rightMargin = DisplayUtil.dp2px(LEFT_MARGIN, getResources());
            }

            cardView.setLayoutParams(layoutParams);
            // 加tag，用于点击事件
            cardView.setTag(vb.getId());
            cardView.setOnClickListener(ListActivity.this);

            ImageView imageView = (ImageView) cardView.findViewById(R.id.image);
            app.loadImage(imageView, vb.picUrl);
            TextView text = (TextView) cardView.findViewById(R.id.text);
            text.setText(vb.name);

            mContainer.addView(cardView);
        }
    }

    @Override
    public void onClick(View view) {
        startActivity(VideoInfoActivity.class);
    }

    @Override
    public void onErrorResponse(Constants.HttpCmd cmd, VolleyError error) {
        Logger.d(error.toString(), false);
    }

    @Override
    public void onResponse(Constants.HttpCmd cmd, JSONArray response) {
        Logger.d("cmd: " + cmd + ", response: " + response.toString(), false);
        switch (cmd) {
            case GET_VIDEO_LIST: {
                int length = response.length();
                mData = new ArrayList<Entity>(length);

                for (int i = 0; i < length; i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        VideoBrief vb = new VideoBrief();
                        vb.id = obj.optInt("id");
                        vb.name = obj.optString("name");
                        vb.picUrl = obj.optString("pic");
                        vb.score = obj.optDouble("rate");
                        mData.add(vb);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                updateView();
                break;
            }
            case GET_GAME_LIST: {
                break;
            }
        }
    }
}
