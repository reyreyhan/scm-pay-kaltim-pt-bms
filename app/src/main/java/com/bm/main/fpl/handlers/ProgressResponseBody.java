package com.bm.main.fpl.handlers;

import androidx.annotation.NonNull;

import com.bm.main.fpl.interfaces.ProgressListener;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by Sarif Hidayat on 28/05/2017.
 */

public class ProgressResponseBody extends ResponseBody {

    private final ResponseBody responseBody;
    private final ProgressListener progressListener;
    private BufferedSource bufferedSource;

    public ProgressResponseBody(ResponseBody responseBody, ProgressListener progressListener) {
        this.responseBody = responseBody;
        this.progressListener = progressListener;
    }

    @Override public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override public long contentLength() {
        return responseBody.contentLength();
    }

    @Override public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    @NonNull
    public Source source(@NonNull Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                // read() returns the number of bytes read, or -1 if this source is exhausted.
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
              // progressListener.onUpdate(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
               progressListener.onUpdate(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
             //   progressListener.onUpdate(totalBytesRead,responseBody.contentLength());
                return bytesRead;
            }
        };
    }
//    interface ProgressListener {
//        void onUpdate(long bytesRead, long contentLength, boolean done);
 //   }
}
