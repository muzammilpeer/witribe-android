package com.ranisaurus.newtorklayer.manager;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.ranisaurus.newtorklayer.protocols.IResponseProtocol;
import com.ranisaurus.newtorklayer.requests.BaseNetworkRequest;

/**
 * Created by MuzammilPeer on 1/18/2015.
 */
public class NetworkManager {


    private static NetworkManager ourInstance = new NetworkManager();

    //    protected NetworkManager() {
//        // Exists only to thwart instantiation.
//    }

    //    @SuppressWarnings("static-access")
//    public static NetworkManager getInstance() {
//        return (NetworkManager) SingletonRegistry.REGISTRY
//                .getInstance(NetworkManager.class.getCanonicalName());
//    }
    private NetworkConfig mConfig = new NetworkConfig();

    // singleton
    //singleton reference
    private NetworkManager() {

    }

    public static NetworkManager getInstance() {
        return ourInstance;
    }

    public static NetworkConfig getConfiguration() {
        return ourInstance.mConfig;
    }

    public static void setConfiguration(NetworkConfig config) {
        ourInstance.mConfig = config;
    }

    // methods

    public void executeRequest(final BaseNetworkRequest request,
                               final IResponseProtocol delegate)
            throws Exception {

        if (null == request) {
            this.respondto(delegate, null, request);
        }

        // if (!internet.isAvailable()) {
        // this.respondto(delegate, new
        // Exception("Internet Not Available"),requestType);
        // return;
        // }

        if (request.haveGetData()) {
            Ion.with(mConfig.getContext())
                    .load(request.getURL() + request.getData())
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            if (result != null) {
                                respondto(delegate, result, request);
                            } else {
                                respondto(delegate, e, request);
                            }
                        }
                    });

        } else if (request.havePostData()) {
            Ion.with(mConfig.getContext())
                    .load(request.getURL())
                    .setJsonObjectBody(request.postData())
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            if (result != null) {
                                respondto(delegate, result, request);
                            } else {
                                respondto(delegate, e, request);
                            }
                        }
                    });
        } else if (request.haveImageData()) {
            Ion.with(mConfig.getContext())
                    .load(request.getURL())
                    .setMultipartFile("files", request.getImageData())
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            if (result != null) {
                                respondto(delegate, result, request);
                            } else {
                                respondto(delegate, e, request);
                            }
                        }
                    });
        } else if (request.haveMultiPartData()) {


            Ion.with(mConfig.getContext())
                    .load(request.getURL())
                    .setMultipartParameters(request.getMultiPartData())
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            if (result != null) {
                                respondto(delegate, result, request);
                            } else {
                                respondto(delegate, e, request);
                            }
                        }
                    });

        } else {
            this.respondto(delegate, new Exception("No Request type defined"),
                    request);
        }

    }


    //private methods

    private void respondto(IResponseProtocol delegate, Exception error,
                           BaseNetworkRequest request) {
        if (null != delegate) {
            delegate.responseWithError(error, request);
        }
    }

    private void respondto(IResponseProtocol delegate, Object data, BaseNetworkRequest request) {
        if (null != delegate) {
            delegate.successWithData(data, request);
        }
    }

}
