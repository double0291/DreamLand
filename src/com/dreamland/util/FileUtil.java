package com.dreamland.util;

import android.content.Context;
import android.os.Environment;

import com.dreamland.base.BaseApplication;

public class FileUtil {
	/**
	 * 获取文件缓存的根目录
	 * 
	 * @return
	 */
	public static String getDiskCacheRoot() {
		Context context = BaseApplication.mApp;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable()) {
			return context.getExternalCacheDir().getPath();
		} else {
			return context.getCacheDir().getPath();
		}
	}
}
