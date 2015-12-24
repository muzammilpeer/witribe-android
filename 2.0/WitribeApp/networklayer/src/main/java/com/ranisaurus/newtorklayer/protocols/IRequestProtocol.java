package com.ranisaurus.newtorklayer.protocols;

/**
 * Created by MuzammilPeer on 3/13/2015.
 */

import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

public interface IRequestProtocol {

    String getURL() throws Exception;

    Boolean haveGetData();

    String getData() throws Exception;

    Boolean havePostData();

    JsonObject postData() throws Exception;

    Boolean haveImageData();

    Object getImageData() throws Exception;

    //multipart form support for parameters and files
    boolean haveMultiPartData();

    Map<String, List<String>> getMultiPartData() throws Exception;

}
