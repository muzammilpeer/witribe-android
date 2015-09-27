package com.ranisaurus.newtorklayer.models;

/**
 * Created by muzammilpeer on 9/27/15.
 */
public class DataListResponseModel {
    private Data[] data;

    public Data[] getData() {
        return data;
    }

    public void setData(Data[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ClassPojo [data = " + data + "]";
    }
}
