package com.ranisaurus.databaselayer.model;

import com.orm.SugarRecord;
import com.ranisaurus.utilitylayer.string.StringUtil;

/**
 * Created by muzammilpeer on 8/31/15.
 */
public class DBCategories extends SugarRecord {
    private String categoryId;

    private String category;

    public DBCategories() {
    }

//    public DBCategories(Categories model) {
//        this.categoryId = model.getId();
//        this.category = model.getCategory();
//    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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

}
