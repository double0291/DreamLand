package com.dreamland.URLDrawable.download;

public interface DownloadListener {
    /**
     * 进度
     */
    public void publishProgress(int progress);

    /**
     * 是否被取消
     */
    public boolean isCancel();

    /**
     * 取消下载
     */
    public void cancel();

    /**
     * 下载开始
     */
    public void onDownloadStart();

    /**
     * 下载成功
     */
    public void onDownloadSuccess();

    /**
     * 下载失败
     */
    public void onDownloadFail();


    public class Adapter implements DownloadListener {
        @Override
        public void publishProgress(int progress) {

        }

        @Override
        public boolean isCancel() {
            return false;
        }

        @Override
        public void cancel() {

        }

        @Override
        public void onDownloadStart() {

        }

        @Override
        public void onDownloadSuccess() {

        }

        @Override
        public void onDownloadFail() {

        }
    }
}
