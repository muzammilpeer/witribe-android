package com.muzammilpeer.smarttvapp.controls;

/**
 * Created by muzammilpeer on 11/7/15.
 */
public interface IVideoPlayerControls {

    void onPauseVideo();
    void onPlayVideo();
    void onStopVideo();
    void onStartRecording();
    void onStopRecording();
    void onVideoQualitySelected(boolean HQ);
}
