package com.witribe.witribeapp.manager;

import com.ranisaurus.newtorklayer.models.Response;
import com.ranisaurus.utilitylayer.network.GsonUtil;
import com.ranisaurus.utilitylayer.preferences.PreferencesUtil;
import com.witribe.witribeapp.constant.PreferencesKeys;

/**
 * Created by muzammilpeer on 10/25/15.
 */
public class UserManager {

    private UserManager() {

    }

    private static UserManager instance = new UserManager();

    public static UserManager getInstance() {
        return instance;
    }

    public Response getUserProfile() {
        return (Response) GsonUtil.getObjectFromString(PreferencesUtil.getPreferences(PreferencesKeys.user_profile, ""), Response.class);
    }

    public void saveUserProfile(Response model) {
        PreferencesUtil.savePreferences(PreferencesKeys.user_profile, GsonUtil.getStringFromObject(model));
    }

    public boolean isUserLoggedIn()
    {
        return getUserProfile() != null ? true : false ;
    }

    public void logoutUser()
    {
       PreferencesUtil.clearAllPreferences();
    }

}
