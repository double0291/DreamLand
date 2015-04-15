package com.dreamland.URLDrawable.download;

import android.graphics.Bitmap;
import android.media.ExifInterface;

import org.apache.http.Header;
import org.apache.http.client.CookieStore;

import java.net.URL;

public class DownloadParams {
    public URL url;
    public String urlStr;
    public int reqWidth, reqHeight;

    public Header[] headers;
    public CookieStore cookies;

    public boolean isUseExifOrientation;
    // 旋转角度，作为输出用
    public int outOrientation = ExifInterface.ORIENTATION_UNDEFINED;

    public long downloadSize; // 已下载的字节数量

    // 图片的实际宽高。如果不为0，下载成功后，会用这个代替Drawable的宽高。注意单位时px
    public int outWidth, outHeight;
    // 根据屏幕密度自动缩放图片
    public boolean isAutoScaleByDensity;

    public Object tag;

    // 解析处理，可以在下载器的解析接口中调用，供应用提供定制功能
    public DecodeHandler decodeHandler;

    public static interface DecodeHandler {
        Bitmap run(DownloadParams params, Bitmap bitmap);
    }

}
