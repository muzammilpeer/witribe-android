package com.muzammilpeer.smarttvapp.manager;

import com.muzammilpeer.smarttvapp.constant.PreferencesKeys;
import com.muzammilpeer.smarttvapp.service.LiveRecordIntentService;
import com.ranisaurus.newtorklayer.models.Response;
import com.ranisaurus.utilitylayer.logger.Log4a;
import com.ranisaurus.utilitylayer.network.GsonUtil;
import com.ranisaurus.utilitylayer.preferences.PreferencesUtil;

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
        try {
            if (getUserProfile() != null && getUserProfile().getToken().length() > 0)
                return true;
        } catch (Exception e) {
            Log4a.printException(e);
        }
        return false;
    }

    public void logoutUser() {
        PreferencesUtil.clearAllPreferences();
    }

}
