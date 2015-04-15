package com.dreamland.urldrawable;

import android.graphics.drawable.Drawable;

import java.net.URL;

/**
 * 保存URLDrawable状态
 */
public class URLState extends Drawable.ConstantState {
    URL mUrl;
    String mUrlStr; // 缓存字符串，避免重复toString造成浪费

    /**
     * 成功的图片
     * {@link #mStatus}为成功的话，一定要确保该值不为null，
     * 但是不能用该值是否为null来确定对象是否加载成功
     */
    Drawable.ConstantState mSuccess;

    /**
     * 下载状态
     * 如果状态是成功，那么{@link #mSuccess}一定存在。
     * 其他状态，{@link #mSuccess}也可能存在，比如对象已经异步入了缓存，但是cancel了任务，
     * 或者中做收尾工作时发生了异常
     */
    int mStatus = URLDrawable.LOADING;


    @Override
    public Drawable newDrawable() {
        return null;
    }

    @Override
    public int getChangingConfigurations() {
        return 0;
    }

    /**
     * 回调
     */
    public static interface Callback {
        /**
         * 下载成功，但是没解码
         */
        public void onFileDownload(URLState state);

        /**
         * 加载开始
         */
        public void onLoadStart(URLState state);

        /**
         * 加载成功（已解码）
         */
        public void onLoadSuccess(URLState state);

        /**
         * 加载失败
         */
        public void onLoadFail(URLState state, Throwable e);

        /**
         * 取消加载
         */
        public void onLoadCancel(URLState state);

        /**
         * 加载进度
         */
        public void onUpdateProgress(int progress);

        /**
         * 文件开始下载
         */
        public void onFileDownloadStart();

        /**
         * 文件下载成功
         */
        public void onFileDownloadSuccess(long fileSize);

        /**
         * 文件下载失败
         */
        public void onFileDownloadFail(int errCode);
    }
}
