package com.ranisaurus.utilitylayer.network;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ranisaurus.utilitylayer.logger.Log4a;
import com.ranisaurus.utilitylayer.reflection.ReflectionUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MuzammilPeer on 3/13/2015.
 */
public class GsonUtil {
    // shared method for json coversion
    public static JsonObject getJsonObjectFromObject(final Object obj) {

        Gson gson = new Gson();
        String modelString = gson.toJson(obj);
        JsonParser parser = new JsonParser();
        JsonObject model = (JsonObject) parser.parse(modelString);

        return model;
    }

    public static <T> Object getObjectFromJsonObject(final Object data, Class<T> classofT) {

        if (data instanceof JsonObject) {
            JsonObject json = (JsonObject) data;
            Gson gson = new Gson();
            T obj = gson.fromJson(json, classofT);
            return obj;
        }
        return null;
    }

    public static String getStringFromObject(final Object data) {

        if (data != null) {
            Gson gson = new Gson();
            String json = gson.toJson(data);
            return json;
        }
        return "";
    }

    public static <T> Object getObjectFromString(final String data, Class<T> classofT) {

        if (data instanceof String) {
            Gson gson = new Gson();
            T obj = gson.fromJson(data, classofT);
            return obj;
        }
        return "";
    }


    public static String getQueryStringURL(Object model) {
        StringBuilder sb = new StringBuilder();
//        sb.append(url);
        if (model != null) {
            Boolean flag = true;
            ArrayList<Method> methods = ReflectionUtil.findGettersSetters(
                    model.getClass(), true);
            if (methods.size() > 0) {
                sb.append("?");
            }

            for (Method method : methods) {
                try {
                    String returnValue = (String) method.invoke(model, null);
                    if (returnValue != null) {
                        Log4a.e(method.getName(), returnValue);
                        if (flag) {
                            sb.append(method.getName().substring(3).toLowerCase() + "=" + returnValue);
                            flag = false;
                        } else {
                            sb.append("&" + method.getName().substring(3).toLowerCase() + "="
                                    + returnValue);
                        }
                    }
                } catch (IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException e) {
                    Log4a.e("error occured", e.getMessage());
                }
            }
            Log4a.e("Parcel Bean In completed", "End");
        }
        Log4a.e("Full URL = ", sb.toString());
        return sb.toString();
    }

    public static Map<String, List<String>> getMultipartParams(Object model) {
        Map<String, List<String>> mMap = new HashMap<>();
        List<String> valueList;
        ArrayList<Method> methods = ReflectionUtil.findGettersSetters(model.getClass(), true);
        for (Method method : methods) {
            try {
                String returnValue = (String) method.invoke(model, null);
                if (returnValue != null) {
                    valueList = new ArrayList<>();
                    valueList.add(returnValue);
                    mMap.put(method.getName().substring(3).toLowerCase(), valueList);
                }
            } catch (IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                Log4a.e("error occured", e.getMessage());
            }
        }
        Log4a.e("getMultipartParams Bean In completed", "End");
        return mMap;
    }
}
