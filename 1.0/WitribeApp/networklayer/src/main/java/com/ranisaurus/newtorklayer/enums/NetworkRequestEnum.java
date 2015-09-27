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
    //http://www.sdtps.gov.ae/SDTPS.WebAPI/
    //http://appserver1.systemsltd.com/SDTPS.WebAPI/
    BASE_SERVER_URL(0, "http://ranisaurus.com/webservice/", "Production server url request"),
    CATEGORIES_LIST(2, "webservice.php", "Get Categories List"),
    TAG_LINE_LIST(3, "webservice.php", "Get Tag Line detail"),
    CREATE_TAG_LINE(4, "webservice.php?action=addtagline&tagline=test&howto=test", "Create tagline"),
    LOGIN_REQUEST(5, "http://afmsrv:81/skytelecom/mapi.php", "Login request"),;// semicolon needed when fields / methods follow

    public static boolean isProduction = false;
    /**
     * A mapping between the integer code and its corresponding Status to
     * facilitate lookup by code.
     */
    private static Map<Integer, NetworkRequestEnum> codeToStatusMapping;
    private int code;
    private String relativeUrl;
    private String description;

    NetworkRequestEnum(int code, String relativeUrl, String description) {
        this.code = code;
        this.relativeUrl = relativeUrl;
        this.description = description;
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

    public String getRelativeUrl() {
//        if (getCode() == 0) {
//            return isProduction ? BASE_SERVER_URL.relativeUrl : BASE_SERVER_URL.relativeUrl;
//        }
        return relativeUrl;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("NetworkRequestEnum");
        sb.append("{code=").append(code);
        sb.append(", relativeUrl='").append(relativeUrl).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
