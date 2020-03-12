package com.bm.main.fpl.interfaces;

/**
 * Created by sarifhidayat on 2019-08-27.
 **/
public interface RequestProgress {
  void onRequestProgress(long bytesWritten, long contentLength);
}
