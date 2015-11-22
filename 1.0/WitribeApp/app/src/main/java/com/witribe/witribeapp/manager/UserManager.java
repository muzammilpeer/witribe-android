package com.witribe.witribeapp.manager;

import com.ranisaurus.newtorklayer.models.Response;
import com.ranisaurus.utilitylayer.network.GsonUtil;
import com.ranisaurus.utilitylayer.preferences.PreferencesUtil;
import com.witribe.witribeapp.constant.PreferencesKeys;
import com.witribe.witribeapp.service.LiveRecordIntentService;

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

    public String getVideoStreamingUrl() {
        return PreferencesUtil.getPreferences(LiveRecordIntentService.LIVE_VIDEO_URL_KEY, "") + "";
    }

    public void setVideoStreamingUrl(String url) {
        if (url != null) {
            PreferencesUtil.savePreferences(LiveRecordIntentService.LIVE_VIDEO_URL_KEY, url);
        }
    }

    public String getThumbnailImageUrl() {
        return PreferencesUtil.getPreferences(LiveRecordIntentService.LIVE_IMAGE_URL_KEY, "") + "";
    }

    public void setThumbnailImageUrl(String url) {
        if (url != null) {
            PreferencesUtil.savePreferences(LiveRecordIntentService.LIVE_IMAGE_URL_KEY, url);
        }
    }

    public String getStreamingTitle() {
        return PreferencesUtil.getPreferences(LiveRecordIntentService.LIVE_TITLE_URL_KEY, "") + "";
    }

    public void setStreamingTitle(String title) {
        if (title != null) {
            PreferencesUtil.savePreferences(LiveRecordIntentService.LIVE_TITLE_URL_KEY, title + "");
        }
    }


    public Response getUserProfile() {
        return (Response) GsonUtil.getObjectFromString(PreferencesUtil.getPreferences(PreferencesKeys.user_profile, ""), Response.class);
    }

    public void saveUserProfile(Response model) {
        PreferencesUtil.savePreferences(PreferencesKeys.user_profile, GsonUtil.getStringFromObject(model));
    }

    public boolean isUserLoggedIn() {
        return getUserProfile() != null ? true : false;
    }

    public void logoutUser() {
        PreferencesUtil.clearAllPreferences();
    }

}
