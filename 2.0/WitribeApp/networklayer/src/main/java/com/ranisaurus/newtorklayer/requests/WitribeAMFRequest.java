package com.ranisaurus.newtorklayer.requests;

import com.google.gson.JsonObject;
import com.ranisaurus.newtorklayer.enums.NetworkRequestEnum;
import com.ranisaurus.newtorklayer.manager.NetworkConfig;
import com.ranisaurus.newtorklayer.models.WitribeAMFRequestModel;
import com.ranisaurus.utilitylayer.network.GsonUtil;

/**
 * Created by muzammilpeer on 9/27/15.
 */
public class WitribeAMFRequest extends BaseNetworkRequest {

    //copy constructor
    public WitribeAMFRequest(String[] params, NetworkRequestEnum requestEnum) {
        networkRequestEnum = requestEnum;
        WitribeAMFRequestModel model = new WitribeAMFRequestModel();
        model.setServiceName(networkRequestEnum.getServiceName());
        model.setMethodName(networkRequestEnum.getMethodName());
        model.setParameters(params);
        this.dataModel = model;
    }

    public String getURL() throws Exception {
        return NetworkConfig.getBaseURL();
    }

    @Override
    public Boolean havePostData() {
        return true;
    }

    @Override
    public JsonObject postData() throws Exception {
        return GsonUtil.getJsonObjectFromObject(this.getDataModel());
    }
}
