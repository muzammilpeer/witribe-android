package com.ranisaurus.newtorklayer.models;

/**
 * Created by muzammilpeer on 8/31/15.
 */
public class TagLineCreateResponseModel extends BaseModel {
    private String code;

    private String msg;

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
        return "ClassPojo [code = " + code + ", msg = " + msg + "]";
    }
}