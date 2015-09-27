package com.ranisaurus.newtorklayer.protocols;


import com.ranisaurus.newtorklayer.requests.BaseNetworkRequest;

/**
 * Created by MuzammilPeer on 3/13/2015.
 */
public interface IResponseProtocol {
    void responseWithError(Exception error, BaseNetworkRequest request);

    void successWithData(Object data, BaseNetworkRequest request);
}
