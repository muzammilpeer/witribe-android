package com.ranisaurus.newtorklayer.requests;

import com.ranisaurus.newtorklayer.enums.NetworkRequestEnum;
import com.ranisaurus.newtorklayer.models.TagLineCategoryRequestModel;

/**
 * Created by muzammilpeer on 8/30/15.
 */
public class TagLineListRequest extends BaseNetworkRequest {

    //copy constructor
    public TagLineListRequest(Object model) {
        super(model);
    }

    public String getURL() throws Exception {
        return NetworkRequestEnum.BASE_SERVER_URL.getRelativeUrl()
                + NetworkRequestEnum.TAG_LINE_LIST.getRelativeUrl();
    }

    @Override
    public Boolean haveGetData() {
        return true;
    }

    @Override
    public String getData() throws Exception {
        return "?action=" + ((TagLineCategoryRequestModel) this.getDataModel()).getAction() + "&categoryid=" + ((TagLineCategoryRequestModel) this.getDataModel()).getCategoryid();
//        return GsonUtil.getQueryStringURL(this.getDataModel());
    }

}
