package com.dreamland.URLDrawable.download;

import java.io.File;

/**
 * http://，file://等协议来将图片下载到文件系统中缓存
 */
public interface ProtocolDownloader {
    /**
     * 下载图片到缓存文件中
     */
    File loadImageFile(DownloadParams params, DownloadListener listener) throws Exception;

    /**
     * 将文件解码成图片
     */
    Object decodeFile(File f, DownloadParams params, DownloadListener listener) throws Exception;

    /**
     * 是否存在本地文件
     */
    boolean hasDiskFile(DownloadParams params);


    public class Adapter implements ProtocolDownloader {

        @Override
        public File loadImageFile(DownloadParams params, DownloadListener listener) throws
                Exception {
            return null;
        }

        @Override
        public Object decodeFile(File f, DownloadParams params, DownloadListener listener) throws
                Exception {
            return null;
        }

        @Override
        public boolean hasDiskFile(DownloadParams params) {
            return false;
        }
    }
}
