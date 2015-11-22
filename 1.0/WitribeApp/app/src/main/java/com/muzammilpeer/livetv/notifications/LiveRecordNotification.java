package com.muzammilpeer.livetv.notifications;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.NotificationCompat;

import com.muzammilpeer.livetv.MainActivity;
import com.muzammilpeer.livetv.R;
import com.muzammilpeer.livetv.manager.UserManager;
import com.muzammilpeer.livetv.receiver.LiveRecordReceiver;

/**
 * Created by muzammilpeer on 11/21/15.
 */
@SuppressLint("ParcelCreator")
public class LiveRecordNotification extends Notification {

    public static String INTENT_NOTIFICATION_KEY = "INTENT_NOTIFICATION_KEY";
    public static String INTENT_NOTIFICATION_START_RECORDING = "INTENT_NOTIFICATION_START_RECORDING";
    public static String INTENT_NOTIFICATION_STOP_RECORDING = "INTENT_NOTIFICATION_STOP_RECORDING";
    public static String INTENT_NOTIFICATION_OPEN_APP = "INTENT_NOTIFICATION_OPEN_APP";
    public static int NOTIFICATION_SESSION_ID = 1234567890;

    private int NOTIFICATION_ICON_WIDTH = 45;
    private int NOTIFICATION_ICON_HEIGHT = 45;

    private Context mContext;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder builder;

    private static LiveRecordNotification mInstance;

    public static LiveRecordNotification getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new LiveRecordNotification(ctx);
        }
        return mInstance;
    }

    public LiveRecordNotification(Context ctx) {
        super();
        this.mContext = ctx;
        if (builder == null && mNotificationManager == null) {

            NotificationCompat.MediaStyle mediaStyle = new NotificationCompat.MediaStyle().setShowActionsInCompactView(1);

            String ns = Context.NOTIFICATION_SERVICE;
            mNotificationManager = (NotificationManager) mContext.getSystemService(ns);
            CharSequence tickerText = "RecordingStarted";
            long when = System.currentTimeMillis();
//            NOTIFICATION_SESSION_ID = (int) when;

            Drawable notificationDrawable = mContext.getResources().getDrawable(R.mipmap.ic_launcher);
            Bitmap notificaionBitmap = ((BitmapDrawable) notificationDrawable).getBitmap();


            Intent startIntent = new Intent(mContext, LiveRecordReceiver.class);
            startIntent.putExtra(INTENT_NOTIFICATION_KEY, INTENT_NOTIFICATION_START_RECORDING);
            PendingIntent startPendingIntent = PendingIntent.getBroadcast(mContext, 0, startIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Intent stopIntent = new Intent(mContext, LiveRecordReceiver.class);
            stopIntent.putExtra(INTENT_NOTIFICATION_KEY, INTENT_NOTIFICATION_STOP_RECORDING);
            PendingIntent stopPendingIntent = PendingIntent.getBroadcast(mContext, 1, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            //top listener
            Intent app = new Intent(mContext, MainActivity.class);
            app.putExtra(INTENT_NOTIFICATION_KEY, INTENT_NOTIFICATION_OPEN_APP);
            PendingIntent appPendingIntent = PendingIntent.getActivity(mContext, 2, app, 0);

            builder = new NotificationCompat.Builder(mContext);
            builder.setWhen(when)
                    .setOngoing(true)
                    .setContentText("Recording")
                    .setContentTitle(UserManager.getInstance().getStreamingTitle())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                            // Add media control buttons that invoke intents in your media service
                    .addAction(R.drawable.ic_stop_white_48dp, "Stop", stopPendingIntent) // #0
                    .addAction(R.drawable.ic_fiber_smart_record_white_48dp, "Record", startPendingIntent)  // #1
                    .addAction(R.drawable.ic_web_asset_white_48dp, "App", appPendingIntent)     // #2
                    .setStyle(mediaStyle);


            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(UserManager.getInstance().getThumbnailImageUrl(), options);
            builder.setLargeIcon(bitmap);

            mNotificationManager.notify(NOTIFICATION_SESSION_ID, builder.build());

        }
    }

    public LiveRecordNotification updateNotification(String notificationTitle, Bitmap notificationIcon) {
        if (builder != null) {
            if (notificationTitle != null) {
                builder.setTicker(notificationTitle);
                builder.setContentTitle(notificationTitle);
            }

            if (notificationIcon != null) {
                builder.setSmallIcon(R.mipmap.ic_launcher);
            }

            mNotificationManager.notify(NOTIFICATION_SESSION_ID, builder.build());
        }
        return mInstance;
    }


    public void hideNotification() {
        if (mNotificationManager != null) {
            mNotificationManager.cancel(NOTIFICATION_SESSION_ID);
            builder = null;
            mNotificationManager = null;
            mInstance = null;
        }
    }

    public LiveRecordNotification setOnGoing(boolean isOngoing) {
        if (builder != null) {
            builder.setOngoing(isOngoing);
            if (!isOngoing)
                mNotificationManager.notify(NOTIFICATION_SESSION_ID, builder.build());
        }
        return mInstance;
    }


}