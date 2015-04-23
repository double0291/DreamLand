package com.dreamland.download;

/**
 * Interface definition for a callback to be invoked when downloading.
 */
public interface DownloadListener {
    /**
     * Invoked when downloading is started.
     */
    void onStart(String downloadUrl, long totalBytes);

    /**
     * Invoked when download retrying.
     */
    void onRetry(String downloadUrl);

    /**
     * Invoked when downloading is in progress.
     */
    void onProgress(String downloadUrl, long bytesWritten, long totalBytes);

    /**
     * Invoked when downloading successfully.
     */
    void onSuccess(String downloadUrl, String filePath);

    /**
     * Invoked when downloading failed.
     */
    void onFailure(String downloadUrl, int statusCode, String errMsg);
}
