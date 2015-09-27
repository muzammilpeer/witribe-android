package com.ranisaurus.databaselayer.model;

import com.orm.SugarRecord;
import com.ranisaurus.utilitylayer.string.StringUtil;

/**
 * Created by muzammilpeer on 8/31/15.
 */
public class DBTaglines extends SugarRecord {

    private String categoryID;

    private String taglineId;

    private String howTo;

    private String tagline;

    public DBTaglines() {
    }

//    public DBTaglines(Taglines model, String dbCategoryID) {
//        this.taglineId = model.getId();
//        this.howTo = model.getHowTo();
//        this.tagline = model.getTagline();
//        this.categoryID = dbCategoryID;
//    }

    public String getTaglineId() {
        return taglineId;
    }

    public void setTaglineId(String taglineId) {
        this.taglineId = taglineId;
    }

    public String getHowTo() {
        return howTo;
    }

    public void setHowTo(String howTo) {
        this.howTo = howTo;
    }

    public String getTagline() {
        return StringUtil.getCleanTagline(tagline);
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getTaglineIconName() {
        return StringUtil.getFirstCharacterCapitalized(getTagline());
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }
}
