package com.muzammilpeer.ffmpeglayer.manager;

import android.content.Context;

import com.muzammilpeer.ffmpeglayer.helpers.FFMpegFileUtils;
import com.muzammilpeer.ffmpeglayer.imanager.IBufferStream;

/**
 * Created by muzammilpeer on 11/2/15.
 */
public class FFmpegManager {

    private Context mContext;
    String ffmpegFullCommandFormat = "%s %s";

    String recordingParameterFormat = "-re -i %s -c copy -bsf:a aac_adtstoasc %s";
    private static FFmpegManager ourInstance = new FFmpegManager();

    public static FFmpegManager getInstance(Context context) {
        ourInstance.mContext = context;
        return ourInstance;
    }

    public static FFmpegManager getInstance() {
        return ourInstance;
    }


    private FFmpegManager() {
        //set logging by default enable
        ShellManager.getInstance().setLoggingEnable(true);
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public void startLiveStreamRecording(String streamURL,String mp4FilePath,IBufferStream callback)
    {
        String params = String.format(recordingParameterFormat,streamURL,mp4FilePath);
        String fullCommand = String.format(ffmpegFullCommandFormat,FFMpegFileUtils.getFFmpeg(mContext),params);
        ShellManager.getInstance().executeCallback(fullCommand,callback);
    }

    public void stopLiveStreamRecording()
    {
        ShellManager.getInstance().writeToShell("q");
    }

    public boolean isLiveStreamRecording()
    {
        return ShellManager.getInstance().isTaskRunning();
    }
}
