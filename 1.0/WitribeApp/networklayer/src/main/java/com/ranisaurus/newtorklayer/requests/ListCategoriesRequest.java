package com.ranisaurus.newtorklayer.requests;

import com.ranisaurus.newtorklayer.enums.NetworkRequestEnum;
import com.ranisaurus.newtorklayer.models.CategoriesRequestModel;

/**
 * Created by muzammilpeer on 8/30/15.
 */
public class ListCategoriesRequest extends BaseNetworkRequest {

    //copy constructor
    public ListCategoriesRequest(Object model) {
        super(model);
    }

    public String getURL() throws Exception {
        return NetworkRequestEnum.BASE_SERVER_URL.getRelativeUrl()
                + NetworkRequestEnum.CATEGORIES_LIST.getRelativeUrl();
    }

    @Override
    public Boolean haveGetData() {
        return true;
    }

    @Override
    public String getData() throws Exception {
        return "?action=" + ((CategoriesRequestModel) this.getDataModel()).getAction();
//        return GsonUtil.getQueryStringURL(this.getDataModel());
    }
//    public Boolean havePostData() {
//        return true;
//    }
//
//    public JsonObject postData() throws Exception {
//        return GsonUtil.getJsonObjectFromObject(this.getDataModel());
//    }
}
