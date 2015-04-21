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
                request.getDownloadListener().onStart(request.getDownloadId(), totalBytes);
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
                request.getDownloadListener().onRetry(request.getDownloadId());
            }
        });
    }

    /**
     * Post download progress event.
     *
     * @param request      download request
     * @param bytesWritten the bytes have written to file
     * @param totalBytes   the total bytes of currnet file in downloading
     */
    protected void postProgress(final DownloadRequest request,
                                final long bytesWritten, final long totalBytes) {
        mDownloadPoster.execute(new Runnable() {
            @Override
            public void run() {
                request.getDownloadListener().onProgress(
                        request.getDownloadId(), bytesWritten, totalBytes);
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
                        request.getDownloadId(), request.getDestinationPath());
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
                request.getDownloadListener().onFailure(request.getDownloadId(), statusCode,
                        errMsg);
            }
        });
    }
}
