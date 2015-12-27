package com.ranisaurus.newtorklayer.models;

import com.ranisaurus.utilitylayer.base.BaseModel;

/**
 * Created by muzammilpeer on 12/26/15.
 */
public class SectionSampleModel extends BaseModel {

    private String title;

    public SectionSampleModel(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
