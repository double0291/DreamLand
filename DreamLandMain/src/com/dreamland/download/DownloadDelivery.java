package com.dreamland.download;

import android.os.Handler;

import java.util.concurrent.Executor;

/**
 * Download delivery: used to delivery callback to call back in main thread.
 */
public class DownloadDelivery {
    private final Executor mDownloadPoster;

    public DownloadDelivery(final Handler handler) {
        mDownloadPoster = new Executor() {
            @Override
            public void execute(Runnable command) {
                handler.post(command);
            }
        };
    }

    /**
     * Post download start event.
     *
     * @param request    download request
     * @param totalBytes total bytes
     */
    protected void postStart(final DownloadRequest request, final long totalBytes) {
        mDownloadPoster.execute(new Runnable() {
            @Override
            public void run() {
                request.getDownloadListener().onStart(request.getUrl(), totalBytes);
            }
        });
    }

    /**
     * Post download retry event.
     *
     * @param request download request
     */
    protected void postRetry(final DownloadRequest request) {
        mDownloadPoster.execute(new Runnable() {
            @Override
            public void run() {
                request.getDownloadListener().onRetry(request.getUrl());
            }
        });
    }

    /**
     * Post download progress event.
     *
     * @param request      download request
     * @param bytesWritten the bytes have written to file
     * @param totalBytes   the total bytes of current file in downloading
     */
    protected void postProgress(final DownloadRequest request,
                                final long bytesWritten, final long totalBytes) {
        mDownloadPoster.execute(new Runnable() {
            @Override
            public void run() {
                request.getDownloadListener().onProgress(
                        request.getUrl(), bytesWritten, totalBytes);
            }
        });
    }

    /**
     * Post download success event.
     *
     * @param request download request
     */
    protected void postSuccess(final DownloadRequest request) {
        mDownloadPoster.execute(new Runnable() {
            @Override
            public void run() {
                request.getDownloadListener().onSuccess(
                        request.getUrl(), request.getDestinationPath());
            }
        });
    }

    /**
     * Post download failure event.
     *
     * @param request download request
     */
    protected void postFailure(final DownloadRequest request, final int statusCode,
                               final String errMsg) {
        mDownloadPoster.execute(new Runnable() {
            @Override
            public void run() {
                request.getDownloadListener().onFailure(request.getUrl(), statusCode,
                        errMsg);
            }
        });
    }
}
