package com.ranisaurus.utilitylayer.base;

import com.ranisaurus.utilitylayer.reflection.ReflectionUtil;

import java.io.Serializable;

/**
 * Created by muzammilpeer on 11/9/15.
 */
public class BaseModel implements Serializable {
    // constructor
    public BaseModel() {

    }

    @Override
    public String toString() {
        return ReflectionUtil.getToStringDescription(getClass(), this);
    }

//
//    public static final Parcelable.Creator<BaseModel> CREATOR = new Parcelable.Creator<BaseModel>() {
//        @Override
//        public BaseModel createFromParcel(Parcel in) {
//            return new BaseModel(in);
//        }
//
//        @Override
//        public BaseModel[] newArray(int size) {
//            return new BaseModel[size];
//        }
//    };


//    protected BaseModel(Parcel in) {
//        for (Method method : ReflectionUtil.findGettersSetters(getClass(), false)) {
//            try {
//
//                String returnValue = (String) method.invoke(this,
//                        in.readString());
//                if (returnValue != null) {
//                    Log4a.e(method.getName(), returnValue);
//                }
//            } catch (IllegalAccessException | IllegalArgumentException
//                    | InvocationTargetException e) {
//                Log4a.e("error occured", e.getMessage());
//            }
//
//        }
//        Log4a.e("Parcel Bean In completed", "End");
//    }


//    public int describeContents() {
//        return 0;
//    }

//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        // dest.writeString(statusCode);
//        for (Method method : ReflectionUtil.findGettersSetters(getClass(), true)) {
//            try {
//                if (method.invoke(this, null) instanceof String) {
//                    String returnValue = (String) method.invoke(this, null);
//                    if (returnValue != null) {
//                        Log4a.e(method.getName(), returnValue);
//                        dest.writeString(returnValue);
//                    }
//                } else if (method.invoke(this, null) instanceof ArrayList) {
//                    ArrayList returnValue = (ArrayList) method.invoke(this, null);
//                    if (returnValue != null) {
//                        dest.writeArray(returnValue.toArray());
//                    }
//                }
//            } catch (IllegalAccessException | IllegalArgumentException
//                    | InvocationTargetException e) {
//                // TODO Auto-generated catch block
//                Log4a.e("error occured", e.getMessage());
//            }
//
//        }
//        Log4a.e("writeToParcel is completed", "End");
//    }


}