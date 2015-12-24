package com.ranisaurus.utilitylayer.base;

import android.os.Parcel;
import android.os.Parcelable;

import com.ranisaurus.utilitylayer.logger.Log4a;
import com.ranisaurus.utilitylayer.reflection.ReflectionUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by muzammilpeer on 11/9/15.
 */
public class BaseModel implements Parcelable {

    public static final Parcelable.Creator<BaseModel> CREATOR = new Parcelable.Creator<BaseModel>() {
        @Override
        public BaseModel createFromParcel(Parcel in) {
            return new BaseModel(in);
        }

        @Override
        public BaseModel[] newArray(int size) {
            return new BaseModel[size];
        }
    };

    // constructor
    public BaseModel() {

    }

    protected BaseModel(Parcel in) {
        for (Method method : ReflectionUtil.findGettersSetters(getClass(), false)) {
            try {

                String returnValue = (String) method.invoke(this,
                        in.readString());
                if (returnValue != null) {
                    Log4a.e(method.getName(), returnValue);
                }
            } catch (IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                Log4a.e("error occured", e.getMessage());
            }

        }
        Log4a.e("Parcel Bean In completed", "End");
    }

    @Override
    public String toString() {
        return ReflectionUtil.getToStringDescription(getClass(), this);
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // dest.writeString(statusCode);
        for (Method method : ReflectionUtil.findGettersSetters(getClass(), true)) {
            try {
                if (method.invoke(this, null) instanceof String) {
                    String returnValue = (String) method.invoke(this, null);
                    if (returnValue != null) {
                        Log4a.e(method.getName(), returnValue);
                        dest.writeString(returnValue);
                    }
                } else if (method.invoke(this, null) instanceof ArrayList) {
                    ArrayList returnValue = (ArrayList) method.invoke(this, null);
                    if (returnValue != null) {
                        dest.writeArray(returnValue.toArray());
                    }
                }
            } catch (IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                // TODO Auto-generated catch block
                Log4a.e("error occured", e.getMessage());
            }

        }
        Log4a.e("writeToParcel is completed", "End");
    }


}