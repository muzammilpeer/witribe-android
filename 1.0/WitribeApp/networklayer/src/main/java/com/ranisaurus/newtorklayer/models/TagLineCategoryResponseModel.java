package com.ranisaurus.newtorklayer.models;

import java.util.ArrayList;

/**
 * Created by muzammilpeer on 8/31/15.
 */
public class TagLineCategoryResponseModel extends BaseModel {
    private ArrayList<Taglines> taglines;

    private String code;

    private String msg;

    public ArrayList<Taglines> getTaglines() {
        return taglines;
    }

    public void setTaglines(ArrayList<Taglines> taglines) {
        this.taglines = taglines;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ClassPojo [taglines = " + taglines + ", code = " + code + ", msg = " + msg + "]";
    }
}
