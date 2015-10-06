package com.ranisaurus.utilitylayer.string;

/**
 * Created by muzammilpeer on 9/5/15.
 */
public class StringUtil {

    public static String getFirstCharacterCapitalized(String content) {
        if (content != null && content.length() > 0) {
            return content.substring(0, 1).toUpperCase();
        }
        return "";
    }

    public static String getCleanTagline(String tagline) {
        return tagline.replaceAll("\"", "");
    }


}
