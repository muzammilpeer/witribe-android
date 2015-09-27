package com.ranisaurus.newtorklayer.models;

/**
 * Created by muzammilpeer on 8/31/15.
 */
public class TagLineCategoryRequestModel extends BaseModel {


    private String categoryid;
    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }
}
