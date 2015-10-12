package com.ranisaurus.newtorklayer.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by muzammilpeer on 9/27/15.
 */
public class DataBooleanResponseModel implements Parcelable {
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<DataBooleanResponseModel> CREATOR = new Parcelable.Creator<DataBooleanResponseModel>() {
        @Override
        public DataBooleanResponseModel createFromParcel(Parcel in) {
            return new DataBooleanResponseModel(in);
        }

        @Override
        public DataBooleanResponseModel[] newArray(int size) {
            return new DataBooleanResponseModel[size];
        }
    };
    private String data;

    protected DataBooleanResponseModel(Parcel in) {
        data = in.readString();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ClassPojo [data = " + data + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(data);
    }
}