package com.ranisaurus.newtorklayer.models;

import java.util.ArrayList;

/**
 * Created by muzammilpeer on 8/31/15.
 */
public class CategoriesResponseModel extends BaseModel {
    private ArrayList<Categories> categories;

    private String code;

    private String msg;

    public ArrayList<Categories> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Categories> categories) {
        this.categories = categories;
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
        return "ClassPojo [categories = " + categories + ", code = " + code + ", msg = " + msg + "]";
    }
}
