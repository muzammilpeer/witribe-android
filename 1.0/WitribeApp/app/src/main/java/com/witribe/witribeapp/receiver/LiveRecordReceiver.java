package com.witribe.witribeapp.receiver;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.witribe.witribeapp.manager.UserManager;
import com.witribe.witribeapp.notifications.LiveRecordNotification;
import com.witribe.witribeapp.service.LiveRecordIntentService;

/**
 * Created by muzammilpeer on 11/21/15.
 */
public class LiveRecordReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentData = new Intent(context, LiveRecordIntentService.class);
        intentData.setComponent(new ComponentName(context, LiveRecordIntentService.class));


        if (intent != null && intent.getExtras() != null) {
            String action = (String) intent.getExtras().get(LiveRecordNotification.INTENT_NOTIFICATION_KEY);
            if (action.equals(LiveRecordNotification.INTENT_NOTIFICATION_START_RECORDING)) {
                intentData.putExtra(LiveRecordNotification.INTENT_NOTIFICATION_START_RECORDING, UserManager.getInstance().getVideoStreamingUrl());
                intentData.setAction(LiveRecordNotification.INTENT_NOTIFICATION_START_RECORDING);
                context.startService(intentData);
            } else if (action.equals(LiveRecordNotification.INTENT_NOTIFICATION_STOP_RECORDING)) {
                intentData.putExtra(LiveRecordNotification.INTENT_NOTIFICATION_STOP_RECORDING, "true");
                intentData.setAction(LiveRecordNotification.INTENT_NOTIFICATION_STOP_RECORDING);
                context.startService(intentData);
                context.sendBroadcast(intentData);
            } else if (action.equals(LiveRecordNotification.INTENT_NOTIFICATION_OPEN_APP)) {
                //Your code
            }
        }
    }

}