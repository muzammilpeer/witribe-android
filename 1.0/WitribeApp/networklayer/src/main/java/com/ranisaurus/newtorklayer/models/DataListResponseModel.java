package com.ranisaurus.newtorklayer.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by muzammilpeer on 9/27/15.
 */
public class DataListResponseModel implements Parcelable {
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<DataListResponseModel> CREATOR = new Parcelable.Creator<DataListResponseModel>() {
        @Override
        public DataListResponseModel createFromParcel(Parcel in) {
            return new DataListResponseModel(in);
        }

        @Override
        public DataListResponseModel[] newArray(int size) {
            return new DataListResponseModel[size];
        }
    };
    private ArrayList<Data> data;

    public DataListResponseModel() {
    }

    protected DataListResponseModel(Parcel in) {
        if (in.readByte() == 0x01) {
            data = new ArrayList<Data>();
            in.readList(data, Data.class.getClassLoader());
        } else {
            data = null;
        }
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
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
        if (data == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(data);
        }
    }
}