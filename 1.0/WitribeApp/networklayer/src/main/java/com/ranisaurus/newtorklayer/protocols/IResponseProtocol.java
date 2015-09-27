package com.ranisaurus.newtorklayer.protocols;


import com.ranisaurus.newtorklayer.enums.NetworkRequestEnum;

/**
 * Created by MuzammilPeer on 3/13/2015.
 */
public interface IResponseProtocol {
    void responseWithError(Exception error, NetworkRequestEnum requestType);

    void successWithData(Object data, NetworkRequestEnum requestType);
}
