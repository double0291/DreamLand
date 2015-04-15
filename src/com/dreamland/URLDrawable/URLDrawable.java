package com.dreamland.urldrawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

public class URLDrawable extends Drawable implements Drawable.Callback, Runnable,
        URLState.Callback {
    /**
     * 状态，正在加载
     */
    public static final int LOADING = 0;
    /**
     * 状态，加载成功
     */
    public static final int SUCCESS = 1;
    /**
     * 状态，加载失败
     */
    public static final int FAIL = 2;
    /**
     * 状态，加载取消
     */
    public static final int CANCEL = 3;
    /**
     * 状态，文件下载成功
     */
    public static final int FILE_DOWNLOAD = 4;

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void setAlpha(int i) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }

    @Override
    public void invalidateDrawable(Drawable drawable) {

    }

    @Override
    public void scheduleDrawable(Drawable drawable, Runnable runnable, long l) {

    }

    @Override
    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {

    }

    @Override
    public void run() {

    }

    @Override
    public void onFileDownload(URLState state) {

    }

    @Override
    public void onLoadStart(URLState state) {

    }

    @Override
    public void onLoadSuccess(URLState state) {

    }

    @Override
    public void onLoadFail(URLState state, Throwable e) {

    }

    @Override
    public void onLoadCancel(URLState state) {

    }

    @Override
    public void onUpdateProgress(int progress) {

    }

    @Override
    public void onFileDownloadStart() {

    }

    @Override
    public void onFileDownloadSuccess(long fileSize) {

    }

    @Override
    public void onFileDownloadFail(int errCode) {

    }
}
