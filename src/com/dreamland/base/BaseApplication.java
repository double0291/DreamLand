package com.dreamland.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.dreamland.database.EntityManagerFactory;

public class BaseApplication extends Application {
    private static String PREFERENCE_NAME = "info";

    public static BaseApplication mApp;

    private SharedPreferences mSP;
    private boolean mIsLogin = false;
    private String mUin;

    private EntityManagerFactory mEntityManagerFactory;

    // Volley网络请求
    public RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        mApp = this;

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

    public EntityManagerFactory getEntityManagerFactory() {
        if (TextUtils.isEmpty(mUin)) {
            throw new IllegalStateException(
                    "Can not create a entity factory when the uin is null.");
        }
        synchronized (this) {
            if (mEntityManagerFactory == null) {
                mEntityManagerFactory = new EntityManagerFactory(mUin);
            }
        }
        return mEntityManagerFactory;
    }
}
