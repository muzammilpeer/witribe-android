package com.ranisaurus.newtorklayer.models;

/**
 * Created by muzammilpeer on 8/31/15.
 */
public class TagLineCreateRequestModel extends BaseModel {
    private String action;

    private String tagline;

    private String howto;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getHowto() {
        return howto;
    }

    public void setHowto(String howto) {
        this.howto = howto;
    }
}
