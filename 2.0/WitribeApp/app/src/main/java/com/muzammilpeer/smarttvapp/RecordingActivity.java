package com.muzammilpeer.smarttvapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceView;
import android.widget.Button;

import com.ranisaurus.baselayer.activity.BaseActivity;
import com.ranisaurus.utilitylayer.logger.Log4a;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Date;

/**
 * Created by muzammilpeer on 10/20/15.
 */
abstract public class RecordingActivity  extends BaseActivity {

    private boolean recording=false;
    private Button button;
    private MediaProjectionManager mMediaProjectionManager;
    private static final int REQUEST_CODE_CAPTURE_PERM = 1234;
    private MediaProjection mMediaProjection;
    private static final String VIDEO_MIME_TYPE = "video/avc";

    private static  int VIDEO_WIDTH = 1280;
    private static  int VIDEO_HEIGHT = 720;
    private DisplayMetrics metrics;
    private int screenDensity;
    private int screenWidth;
    private int screenHeight;
    // …
    private boolean mMuxerStarted = false;
    private Surface mInputSurface;
    private MediaMuxer mMuxer;
    private MediaCodec mVideoEncoder;
    private MediaCodec.BufferInfo mVideoBufferInfo;

    private int mTrackIndex = -1;
    private SurfaceView mSurfaceView;

    private FloatingActionButton floatingActionButton;


    private String internalStoragePath = "";
    private String externalStoragePath = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        VIDEO_WIDTH = size.x;
        VIDEO_HEIGHT = size.y;

//        floatingActionButton = (FloatingActionButton)findViewById(R.id.fab_recording);
//
        mMediaProjectionManager = (MediaProjectionManager) getSystemService(
                android.content.Context.MEDIA_PROJECTION_SERVICE);



    }

//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    @Override
//    public void startScreenRecording() {
//        Snackbar.make(mToolbar,"Start Recording",Snackbar.LENGTH_SHORT).show();
//        Intent permissionIntent = mMediaProjectionManager.createScreenCaptureIntent();
//        startActivityForResult(permissionIntent, REQUEST_CODE_CAPTURE_PERM);
//    }
//
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    @Override
//    public void stopScreenRecording() {
//        releaseEncoders();
//        Snackbar.make(mToolbar,"Stop Recording \n File Path :" +internalStoragePath,Snackbar.LENGTH_SHORT).show();
//    }
//
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (REQUEST_CODE_CAPTURE_PERM == requestCode) {
            if (resultCode == RESULT_OK) {
                mMediaProjection = mMediaProjectionManager.getMediaProjection(resultCode, intent);
                startRecording();
            } else {
            }
        }
    }
    private final Handler mDrainHandler = new Handler(Looper.getMainLooper());
    private Runnable mDrainEncoderRunnable = new Runnable() {
        @Override
        public void run() {
            drainEncoder();
        }
    };


// …


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void startRecording() {
        DisplayManager dm = (DisplayManager)getSystemService(Context.DISPLAY_SERVICE);
        Display defaultDisplay = dm.getDisplay(Display.DEFAULT_DISPLAY);
        if (defaultDisplay == null) {
            throw new RuntimeException("No display found.");
        }
        prepareVideoEncoder();
//        prepareAudioEncoder();



        internalStoragePath  = Environment.getExternalStorageDirectory().getAbsolutePath()  +"/"+ new Date().toString() +".mp4";
        Log4a.e("File Path " , internalStoragePath);

        try {
            mMuxer = new MediaMuxer(internalStoragePath, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);

        } catch (IOException ioe) {
            throw new RuntimeException("MediaMuxer creation failed", ioe);
        }

        // Get the display size and density.
        metrics = getResources().getDisplayMetrics();
        screenDensity=metrics.densityDpi;
        screenHeight=metrics.heightPixels;
        screenWidth=metrics.widthPixels;

        // Start the video input.
        mMediaProjection.createVirtualDisplay("Recording Display", screenWidth,
                screenHeight, screenDensity, DisplayManager.VIRTUAL_DISPLAY_FLAG_PRESENTATION, mInputSurface,
                null /* callback */, null /* handler */);

        // Start the encoders
        drainEncoder();
    }

    private boolean copyFile(File src,File dst)throws IOException{
        if(src.getAbsolutePath().toString().equals(dst.getAbsolutePath().toString())){

            return true;

        }else{
            InputStream is=new FileInputStream(src);
            OutputStream os=new FileOutputStream(dst);
            byte[] buff=new byte[1024];
            int len;
            while((len=is.read(buff))>0){
                os.write(buff,0,len);
            }
            is.close();
            os.close();
        }
        return true;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void prepareVideoEncoder() {
        mVideoBufferInfo = new MediaCodec.BufferInfo();
        MediaFormat format = MediaFormat.createVideoFormat(VIDEO_MIME_TYPE, VIDEO_WIDTH, VIDEO_HEIGHT);
        int frameRate = 25; // 30 fps

        // Set some required properties. The media codec may fail if these aren't defined.
        format.setInteger(MediaFormat.KEY_COLOR_FORMAT,
                MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);
        format.setInteger(MediaFormat.KEY_BIT_RATE, 6000000); // 6Mbps
        format.setInteger(MediaFormat.KEY_FRAME_RATE, frameRate);
        format.setInteger(MediaFormat.KEY_CAPTURE_RATE, frameRate);
        format.setInteger(MediaFormat.KEY_REPEAT_PREVIOUS_FRAME_AFTER, 1000000 / frameRate);
        format.setInteger(MediaFormat.KEY_CHANNEL_COUNT, 1);
        format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 1); // 1 seconds between I-frames

        // Create a MediaCodec encoder and configure it. Get a Surface we can use for recording into.
        try {
            mVideoEncoder = MediaCodec.createEncoderByType(VIDEO_MIME_TYPE);
            mVideoEncoder.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
            mInputSurface = mVideoEncoder.createInputSurface();
            mVideoEncoder.start();
        } catch (IOException e) {
            releaseEncoders();
        }
    }



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private boolean drainEncoder() {
        mDrainHandler.removeCallbacks(mDrainEncoderRunnable);
        while (true) {
            int bufferIndex = mVideoEncoder.dequeueOutputBuffer(mVideoBufferInfo, 0);
            if (bufferIndex == MediaCodec.INFO_TRY_AGAIN_LATER) {
                // nothing available yet
                break;
            } else if (bufferIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                // should happen before receiving buffers, and should only happen once
                if (mTrackIndex >= 0) {
                    throw new RuntimeException("format changed twice");
                }
                mTrackIndex = mMuxer.addTrack(mVideoEncoder.getOutputFormat());
//                mTrackIndex = mMuxer.addTrack(mAudioEncoder.getOutputFormat());

                if (!mMuxerStarted && mTrackIndex >= 0) {
                    mMuxer.start();
                    mMuxerStarted = true;
                }
            } else if (bufferIndex < 0) {
                // not sure what's going on, ignore it
            } else {
                ByteBuffer encodedData = mVideoEncoder.getOutputBuffer(bufferIndex);
                if (encodedData == null) {
                    throw new RuntimeException("couldn't fetch buffer at index " + bufferIndex);
                }

                if ((mVideoBufferInfo.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0) {
                    mVideoBufferInfo.size = 0;
                }

                if (mVideoBufferInfo.size != 0) {
                    if (mMuxerStarted) {
                        encodedData.position(mVideoBufferInfo.offset);
                        encodedData.limit(mVideoBufferInfo.offset + mVideoBufferInfo.size);
                        mMuxer.writeSampleData(mTrackIndex, encodedData, mVideoBufferInfo);
                    } else {
                        // muxer not started
                    }
                }

                mVideoEncoder.releaseOutputBuffer(bufferIndex, false);
//                mAudioEncoder.releaseOutputBuffer(bufferIndex,false);

                if ((mVideoBufferInfo.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
                    break;
                }
            }
        }

        mDrainHandler.postDelayed(mDrainEncoderRunnable, 10);
        return false;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void releaseEncoders() {
        mDrainHandler.removeCallbacks(mDrainEncoderRunnable);
        if (mMuxer != null) {
            if (mMuxerStarted) {
                mMuxer.stop();
            }
            mMuxer.release();
            mMuxer = null;
            mMuxerStarted = false;
        }
        if (mVideoEncoder != null) {
            mVideoEncoder.stop();
            mVideoEncoder.release();
            mVideoEncoder = null;
        }



        if (mInputSurface != null) {
            mInputSurface.release();
            mInputSurface = null;
        }
        if (mMediaProjection != null) {
            mMediaProjection.stop();
            mMediaProjection = null;
        }
        mVideoBufferInfo = null;
        mDrainEncoderRunnable = null;
        mTrackIndex = -1;
    }

}
