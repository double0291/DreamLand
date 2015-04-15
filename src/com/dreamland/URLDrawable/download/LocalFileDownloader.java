package com.dreamland.urldrawable.download;

import java.io.File;
import java.io.IOException;

public class LocalFileDownloader implements ProtocolDownloader {
    @Override
    public File loadImageFile(DownloadParams params, DownloadListener listener) throws Exception {
        File f = new File(params.url.getFile());
        if (f.exists()) {
            return f;
        } else {
            throw new IOException("File not found, url: " + params.url);
        }
    }

    @Override
    public Object decodeFile(File f, DownloadParams params, DownloadListener listener) throws
            Exception {
        return null;
    }

    @Override
    public boolean hasDiskFile(DownloadParams params) {
        File f = new File(params.url.getFile());
        return f.exists();
    }
}
