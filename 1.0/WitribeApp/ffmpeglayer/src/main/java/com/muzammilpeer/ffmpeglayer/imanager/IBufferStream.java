package com.muzammilpeer.ffmpeglayer.imanager;

/**
 * Created by muzammilpeer on 11/2/15.
 */
public interface IBufferStream {
    void onStreamInit(String progress);
    void onStreamWorking(String progress);
    void onStreamCompleted(String progress);
    void onStreamError(String error);
}
