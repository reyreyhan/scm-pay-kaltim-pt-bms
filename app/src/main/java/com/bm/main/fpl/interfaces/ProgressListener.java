package com.bm.main.fpl.interfaces;

public interface ProgressListener
{
    void onUpdate(long bytesRead,long totalSize,boolean done);
}
