package com.dreamland.urldrawable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.support.v4.util.LruCache;

import com.dreamland.urldrawable.download.LocalFileDownloader;
import com.dreamland.urldrawable.download.ProtocolDownloader;

import java.util.HashMap;
import java.util.Hashtable;

public abstract class URLDrawableParams {
    // 默认的density
    int mDeviceDensity = DisplayMetrics.DENSITY_DEFAULT;
    // 内存缓存大小，默认5M
    int mMemoryCacheSize = 5 * 1024 * 1024;
    // 内存缓存，如果不为null，就使用此缓存
    public LruCache<String, Object> mMemoryCache = null;
    // 是否采用渐现方式现实图片
    public boolean mFadeInImage = true;
    // 是否播放gif动画，设置为false则只显示第一帧
    public boolean mUseGifAnimation = true;
    // 解码bitmap使用的config
    public Bitmap.Config mConfig = Bitmap.Config.ARGB_8888;
    // 图片是否根据设备的density进行缩放，默认为true
    public boolean mAutoScaleByDensity = true;

    private Hashtable<String, ProtocolDownloader> mDownloaderMap = new Hashtable<String,
            ProtocolDownloader>();
    // 业务自定义的磁盘文件路径，会在磁盘缓存前查找
    HashMap<String, String> mLocalFileMap = new HashMap<String, String>();

    // 超此值2倍的图片将倍缩小
    public int mReqWidth, mReqHeight;

    /**
     * 加载默认loadingDrawable
     */
    protected abstract Drawable getDefaultLoadingDrawable();

    /**
     * 加载默认failDrawable
     */
    protected abstract Drawable getDefaultFailDrawable();

    /**
     * 获取downloader
     */
    protected abstract ProtocolDownloader doGetDownloader(String protocol);

    ProtocolDownloader getDownloader(String protocol) {
        ProtocolDownloader downloader = mDownloaderMap.get(protocol);
        if (downloader == null) {
            downloader = doGetDownloader(protocol);
            if (downloader == null) {
                if ("file".equalsIgnoreCase(protocol)) {
                    downloader = new LocalFileDownloader();
                }
            }
            if (downloader != null) {
                mDownloaderMap.put(protocol, downloader);
            }
        }
        return downloader;
    }

    /**
     * 获取本地文件
     */
    protected abstract String doGetLocalFilePath(String protocol);

    String getLocalFilePath(String protocol) {
        String path = mLocalFileMap.get(protocol);
        if (path == null) {
            path = doGetLocalFilePath(protocol);
            if (path != null) {
                mLocalFileMap.put(protocol, path);
            }
        }
        return path;
    }

    public URLDrawableParams(Context context) {
        mDeviceDensity = context.getResources().getDisplayMetrics().densityDpi;
        mReqWidth = context.getResources().getDisplayMetrics().widthPixels;
        mReqHeight = context.getResources().getDisplayMetrics().heightPixels;
    }
}
