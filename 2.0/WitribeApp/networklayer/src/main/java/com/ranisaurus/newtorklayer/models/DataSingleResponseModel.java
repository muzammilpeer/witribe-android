package com.ranisaurus.newtorklayer.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by muzammilpeer on 9/27/15.
 */
public class DataSingleResponseModel implements Parcelable {
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<DataSingleResponseModel> CREATOR = new Parcelable.Creator<DataSingleResponseModel>() {
        @Override
        public DataSingleResponseModel createFromParcel(Parcel in) {
            return new DataSingleResponseModel(in);
        }

        @Override
        public DataSingleResponseModel[] newArray(int size) {
            return new DataSingleResponseModel[size];
        }
    };
    private Data data;

    protected DataSingleResponseModel(Parcel in) {
        data = (Data) in.readValue(Data.class.getClassLoader());
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ClassPojo [data = " + data.toString() + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(data);
    }
}