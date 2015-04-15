package com.dreamland.URLDrawable;

import android.graphics.drawable.Drawable;

public class URLDrawableOptions {
    // 请求解码的尺寸
    public int mRequestWidth, mRequestHeight;
    // 加载中和失败情况的占位图
    public Drawable mLoadingDrawable, mFailDrawable;
    // 是否播放GIF
    public boolean mIsPlayGif = false;
    // 图片的圆角半径
    public float mGifRoundCorner = 0;
    // 是否使用自动缩放的参数
    public boolean mUseAutoScaleParams = true;
    // 是否使用ExifOrientation来旋转图片
    public boolean mUseExifOrientation = true;
    // 是否使用内存缓存
    public boolean mUseMemoryCache = true;

    private URLDrawableOptions mNext;
    // 标记是否被回收，避免一次申请，多次使用
    private boolean mRecycled = false;

    private static final Object sPoolSync = new Object();
    private static URLDrawableOptions sPool;
    private static int sPoolSize = 0;
    private static final int MAX_POOL_SIZE = 30;

    private URLDrawableOptions() {
    }

    public static URLDrawableOptions obtain() {
        synchronized (sPoolSync) {
            if (sPool != null) {
                URLDrawableOptions options = sPool;
                sPool = options.mNext;
                options.mRecycled = false;
                options.mNext = null;
                sPoolSize--;

                return options;
            }

            return new URLDrawableOptions();
        }
    }

    public void recycle() {
        if (mRecycled)
            return;

        clearForRecycle();
        synchronized (sPoolSync) {
            if (sPoolSize < MAX_POOL_SIZE) {
                mNext = sPool;
                sPool = this;
                sPoolSize++;
            }
        }
    }

    private void clearForRecycle() {
        mRequestHeight = 0;
        mRequestWidth = 0;
        mLoadingDrawable = null;
        mFailDrawable = null;
        mIsPlayGif = false;
        mGifRoundCorner = 0;
        mUseAutoScaleParams = true;
        mUseExifOrientation = true;
        mUseMemoryCache = true;
        mRecycled = true;
    }
}
