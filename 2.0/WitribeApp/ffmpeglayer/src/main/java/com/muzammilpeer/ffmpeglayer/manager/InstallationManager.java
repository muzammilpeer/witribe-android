package com.muzammilpeer.ffmpeglayer.manager;

import android.content.Context;
import android.util.Log;

import com.muzammilpeer.ffmpeglayer.enums.CpuArchEnum;
import com.muzammilpeer.ffmpeglayer.helpers.FFMpegFileUtils;

import java.io.File;

/**
 * Created by muzammilpeer on 11/2/15.
 */
public class InstallationManager {

    private String cpuArchNameFromAssets;
    private Context context;

    private static InstallationManager ourInstance = new InstallationManager();

    public static InstallationManager getInstance() {
        return ourInstance;
    }

    public static InstallationManager getInstance(String cpuArchNameFromAssets, Context context) {
        ourInstance.cpuArchNameFromAssets = cpuArchNameFromAssets;
        ourInstance.context = context;

        return ourInstance;
    }

    private InstallationManager() {

    }

//    public InstallationManager(String cpuArchNameFromAssets, Context context) {
//        this.cpuArchNameFromAssets = cpuArchNameFromAssets;
//        this.context = context;
//    }

    public Boolean installFFMpegInDevice()
    {
        File ffmpegFile = new File(FFMpegFileUtils.getFFmpeg(context));
        if (ffmpegFile.exists() && isDeviceFFmpegVersionOld() && !ffmpegFile.delete()) {
            return false;
        }
        if (!ffmpegFile.exists()) {
            boolean isFileCopied = FFMpegFileUtils.copyBinaryFromAssetsToData(context,
                    cpuArchNameFromAssets + File.separator + FFMpegFileUtils.ffmpegFileName,
                    FFMpegFileUtils.ffmpegFileName);

            // make file executable
            if (isFileCopied) {
                if(!ffmpegFile.canExecute()) {
                    Log.d("canExecute ", "FFmpeg is not executable, trying to make it executable ...");
                    if (ffmpegFile.setExecutable(true)) {
                        return true;
                    }
                } else {
                    Log.d("canExecute","FFmpeg is executable");
                    return true;
                }
            }
        }
        return ffmpegFile.exists() && ffmpegFile.canExecute();

    }

    private boolean isDeviceFFmpegVersionOld() {
        return CpuArchEnum.fromString(FFMpegFileUtils.SHA1(FFMpegFileUtils.getFFmpeg(context))).equals(CpuArchEnum.NONE);
    }


}
