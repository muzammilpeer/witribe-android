package com.muzammilpeer.pakistanitv.activity.tv;

import android.app.Activity;
import android.os.Bundle;

import com.muzammilpeer.pakistanitv.R;

/**
 * Created by muzammilpeer on 1/2/16.
 */
public class TvDashboardActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tv_activity_dashboard);
//        Crashlytics.setUserIdentifier(UserManager.getInstance().getUserProfile().getCustomerID() + "");
//        Crashlytics.setUserEmail(UserManager.getInstance().getUserProfile().getToken() + "");
//        Crashlytics.setUserName(UserManager.getInstance().getUserProfile().getFirstName() + UserManager.getInstance().getUserProfile().getLastName() + "");
    }
}
