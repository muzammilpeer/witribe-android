package com.ranisaurus.newtorklayer.enums;

/**
 * Created by MuzammilPeer on 3/13/2015.
 * Reference from : http://javahowto.blogspot.com/2008/04/java-enum-examples.html
 */

import android.annotation.SuppressLint;

import java.util.HashMap;
import java.util.Map;

@SuppressLint("UseSparseArrays")
public enum NetworkRequestEnum {

    LOGIN_WITRIBE_USER(0, "NewWiTribeService", "loginWiTribeUser"),
    GET_CHANNEL_CATEGORIES(1, "NewWiTribeService", "getChannelCategories"),
    GET_CHANNELS(2, "NewWiTribeService", "getAllChannels"),
    GET_VODS_CATEGORIES(3, "NewWiTribeService", "getVodsCategories"),
    SIGNUP_WITRIBE_USER(4, "NewWiTribeService", "signupWiTribeUser"),
    GET_FAVOURITE_LISTING(5, "NewWiTribeService", "getFavouritelisting"),
    GET_INNER_MENU(6, "TeachService", "getInnerMenu"),
    GET_VODS_SUB_CATEGORIES(7, "NewWiTribeService", "getVODsSubCategories"),
    GET_ALL_VIDEOS_IN_SPECIFIC_CATEGORY(8, "NewWiTribeService", "getAllVideosInSpecificCategory"),
    GET_EDUCATION_MENU_FROM_DB(9, "TeachService", "getEducationMenuFromDB"),
    GET_RELEVANT_VIDEO_BY_ID(10, "TeachService", "getRelevantVideosByID"),
    SEND_PASSWORD_TO_USER(11, "NewWiTribeService", "sendPasswordToUser"),
    GET_ALL_VIDEOS_OF_SELECTED_DRAMA(12, "NewWiTribeService", "getAllVideosOfSelectedDrama"),
    GET_RELEVANT_VIDEOS_BY_ID(13, "TeachService", "getRelevantVideosByID"),
    GET_URL(14, "NewWiTribeService", "getURL"),
    UPDATE_VIEWS(15, "NewWiTribeService", "updateViews"),
    SEARCH_VODS_BY_PHRASE(16, "NewWiTribeService", "searchVODsByPhrase"),
    SEARCH_RESULTS_WITH_WORDS(17, "TeachService", "searchResultsWithWords"),
    ADD_FAVOURITE_LISTING(18, "NewWiTribeService", "addFavouritelisting"),
    DELETE_FAVOURITE_LISTING(19, "NewWiTribeService", "deleteFavouritelisting"),
    GET_COUNTRY_CODE(20, "NewWiTribeService", "getCountryCode"),
    GET_CHANNEL_SCHEDULE(21, "http://timesofindia.indiatimes.com/", "tvschedulejson.cms"),
     //3 parameters 1 userid, 1 2



    ;// semicolon needed when fields / methods follow

    public static boolean isProduction = false;
    /**
     * A mapping between the integer code and its corresponding Status to
     * facilitate lookup by code.
     */
    private static Map<Integer, NetworkRequestEnum> codeToStatusMapping;
    private int code;
    private String serviceName;
    private String methodName;


    NetworkRequestEnum(int code, String sName, String mName) {
        this.code = code;
        this.serviceName = sName;
        this.methodName = mName;
    }

    public static NetworkRequestEnum getStatus(int i) {
        if (codeToStatusMapping == null) {
            initMapping();
        }
        return codeToStatusMapping.get(i);
    }

    @SuppressLint("UseSparseArrays")
    private static void initMapping() {
        codeToStatusMapping = new HashMap<Integer, NetworkRequestEnum>();
        for (NetworkRequestEnum s : values()) {
            codeToStatusMapping.put(s.code, s);
        }
    }

    public int getCode() {
        return code;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("NetworkRequestEnum");
        sb.append("{code=").append(code);
        sb.append(", serviceName='").append(serviceName).append('\'');
        sb.append(", methodName='").append(methodName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
