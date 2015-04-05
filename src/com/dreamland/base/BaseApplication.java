package com.dreamland.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class BaseApplication extends Application {
    private static String PREFERENCE_NAME = "info";

    private SharedPreferences mSP;
    private boolean mIsLogin = false;
    private String mUin;

    // Volley网络请求
    public RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        mSP = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);

        mIsLogin = mSP.getBoolean("isLogin", false);
        if (mIsLogin) {
            mUin = mSP.getString("uin", "");
        }

        mRequestQueue = Volley.newRequestQueue(this);
    }

    public String getUin() {
        return mUin;
    }
}
