package com.witribe.witribeapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ranisaurus.baselayer.fragment.BaseFragment;
import com.ranisaurus.newtorklayer.enums.NetworkRequestEnum;
import com.ranisaurus.newtorklayer.manager.NetworkManager;
import com.ranisaurus.newtorklayer.models.DataBooleanResponseModel;
import com.ranisaurus.newtorklayer.models.DataListResponseModel;
import com.ranisaurus.newtorklayer.models.DataSingleResponseModel;
import com.ranisaurus.newtorklayer.requests.BaseNetworkRequest;
import com.ranisaurus.newtorklayer.requests.WitribeAMFRequest;
import com.ranisaurus.utilitylayer.logger.Log4a;
import com.ranisaurus.utilitylayer.network.GsonUtil;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends BaseFragment {

    private Uri imageUri;
    private String selectedImagePath = "";


    public MainActivityFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, R.layout.fragment_main);

        return mView;
    }

    @Override
    public void initViews() {
        super.initViews();

        getBaseActivity().hideBackButton();
        getBaseActivity().restoreToolBarColorWithStatusBar();
    }

    @Override
    public void initObjects() {
        super.initObjects();

    }

    @Override
    public void initListenerOrAdapter() {
        super.initListenerOrAdapter();


        //open camera
        captureCameraPicture();

    }

    @Override
    public void initNetworkCalls() {
        super.initNetworkCalls();


//        String[] params = new String[2];
//        params[0] = "muzammilpeer98744";
//        params[1] = "pa5is8an";
//
//        WitribeAMFRequest request = new WitribeAMFRequest(params,NetworkRequestEnum.LOGIN_WITRIBE_USER);
//        try {
//            NetworkManager.getInstance().executeRequest(request,this);
//
//        }catch (Exception e)
//        {
//            Log4a.printException(e);
//        }


//        WitribeAMFRequest request = new WitribeAMFRequest(null, NetworkRequestEnum.GET_CHANNEL_CATEGORIES);
//        try {
//            NetworkManager.getInstance().executeRequest(request,this);
//
//        }catch (Exception e)
//        {
//            Log4a.printException(e);
//        }

        String[] params = new String[3];
        params[0] = "39056";
        params[1] = "13";
        params[2] = "1";

        //let's suppose we have image testing thing
        try {


            Bitmap image = getCaptureCameraPictureBitmap(4);
            String filePath = getCaptureCameraPictureFilePath();
        } catch (Exception e) {
            Log4a.printException(e);
        }

        WitribeAMFRequest request = new WitribeAMFRequest(params, NetworkRequestEnum.ADD_FAVOURITE_LISTING);
        try {
            NetworkManager.getInstance().executeRequest(request, this);

        } catch (Exception e) {
            Log4a.printException(e);
        }

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (imageUri == null) {
            outState.putString("file-uri", "");
        } else {
            outState.putString("file-uri", imageUri.toString());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getBaseActivity().RESULT_OK) {
            if (requestCode == 1) {
                String fileName = "";
                try {
                    selectedImagePath = imageUri.getPath().toString();
                    fileName = selectedImagePath.substring(
                            selectedImagePath.lastIndexOf('/') + 1,
                            selectedImagePath.length());

                    Log4a.d("Capture Image Name = ", fileName);

                } catch (Exception e) {
                    Log4a.printException(e);
                }
            }
        } else if (resultCode == getBaseActivity().RESULT_CANCELED) {
            Intent returnIntent = new Intent();
            getBaseActivity().setResult(getBaseActivity().RESULT_CANCELED, returnIntent);
            getBaseActivity().finish();
        }
    }

    @Override
    public void responseWithError(Exception error, BaseNetworkRequest request) {
        super.responseWithError(error, request);
        try {
            if (mView != null) {
                switch (request.getNetworkRequestEnum()) {
                    case LOGIN_WITRIBE_USER: {
                        Log4a.e("Error ", "some error in network");
                    }
                }
            }
        } catch (Exception e) {
            Log4a.printException(e);
        }
    }


    @Override
    public void successWithData(Object data, BaseNetworkRequest request) {
        super.successWithData(data, request);
        try {
            if (mView != null) {
                switch (request.getNetworkRequestEnum()) {

                    case LOGIN_WITRIBE_USER: {
                        DataSingleResponseModel model = (DataSingleResponseModel) GsonUtil.getObjectFromJsonObject(data, DataSingleResponseModel.class);
                        Log4a.e("Response ", model.toString() + "");
                    }
                    break;
                    case GET_CHANNEL_CATEGORIES: {
                        DataListResponseModel model = (DataListResponseModel) GsonUtil.getObjectFromJsonObject(data, DataListResponseModel.class);
                        Log4a.e("Response ", model.toString() + "");
                    }
                    break;
                    case ADD_FAVOURITE_LISTING: {
                        DataBooleanResponseModel model = (DataBooleanResponseModel) GsonUtil.getObjectFromJsonObject(data, DataBooleanResponseModel.class);
                        Log4a.e("Response ", model.toString() + "");
                    }
                    break;

                }
            }
        } catch (Exception e) {
            Log4a.printException(e);
        }
    }


}
