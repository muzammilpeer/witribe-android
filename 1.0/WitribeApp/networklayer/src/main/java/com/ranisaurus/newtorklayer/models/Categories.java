package com.ranisaurus.newtorklayer.models;

import com.ranisaurus.utilitylayer.string.StringUtil;

/**
 * Created by muzammilpeer on 8/31/15.
 */
public class Categories {
    private String id;

    private String category;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryIconName() {
        return StringUtil.getFirstCharacterCapitalized(getCategory());
    }

    @Override
    public String toString() {
        return "ClassPojo [id = " + id + ", category = " + category + "]";
    }


}
