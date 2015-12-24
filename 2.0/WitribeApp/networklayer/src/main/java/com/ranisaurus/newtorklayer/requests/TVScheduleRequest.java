package com.ranisaurus.newtorklayer.requests;

import com.ranisaurus.newtorklayer.enums.NetworkRequestEnum;
import com.ranisaurus.newtorklayer.models.ChannelScheduleRequestModel;
import com.ranisaurus.utilitylayer.network.GsonUtil;

/**
 * Created by muzammilpeer on 11/4/15.
 */
public class TVScheduleRequest extends BaseNetworkRequest {

    //copy constructor
    public TVScheduleRequest(ChannelScheduleRequestModel model, NetworkRequestEnum requestEnum) {
        networkRequestEnum = requestEnum;
        this.dataModel = model;
    }

    public String getURL() throws Exception {
        return networkRequestEnum.getServiceName() + networkRequestEnum.getMethodName();
    }

    @Override
    public Boolean haveGetData() {
        return true;
    }

    @Override
    public String getData() throws Exception {
        return GsonUtil.getQueryStringURL(this.dataModel);
    }
}
