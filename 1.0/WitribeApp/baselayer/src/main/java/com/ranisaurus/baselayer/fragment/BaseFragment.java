package com.ranisaurus.baselayer.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import com.ranisaurus.baselayer.activity.BaseActivity;
import com.ranisaurus.baselayer.view.CircularLoader;
import com.ranisaurus.newtorklayer.manager.NetworkConfig;
import com.ranisaurus.newtorklayer.manager.NetworkManager;
import com.ranisaurus.newtorklayer.protocols.IResponseProtocol;
import com.ranisaurus.newtorklayer.requests.BaseNetworkRequest;
import com.ranisaurus.utilitylayer.file.FileUtil;
import com.ranisaurus.utilitylayer.logger.Log4a;
import com.ranisaurus.utilitylayer.view.ImageUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class BaseFragment extends Fragment implements IResponseProtocol {
    protected static int CAPTURE_IMAGE_REQUEST_CODE = 101;
    protected View mView; //main layout inflated by fragment
    protected Context mContext;
    protected AtomicBoolean isFragmentLoaded = new AtomicBoolean(false);
    private List localDataSource = new ArrayList();
    private Uri captureImageURI;
    //        outState.putString("file-uri", "");
    private String captureImageURIKey = "captureImageURI";


    public BaseFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getParentFragment == null) {
//            setRetainINstance(true);
//        }

        // This is a handling for activity recreation for Samsung device capture image from camera
        if (savedInstanceState != null) {
            if (!savedInstanceState.getString(captureImageURIKey).equals("")) {
                captureImageURI = Uri.parse(savedInstanceState.getString(captureImageURIKey));
            }
        }

    }


    // custom onCreateView
    public void onCreateView(LayoutInflater inflater, int resource) {
        mContext = getBaseActivity();
        mView = inflater.inflate(resource, null, false);
        setupFragment();
    }


    //Now fragment is active on "onResume"
    @Override
    public void onResume() {
        super.onResume();
    }

    //now user moves to add/replace/remove fragment state
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    //on config change or back stack
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            ButterKnife.unbind(this);
        } catch (Exception e) {
            Log4a.printException(e);
        }
    }

    //special condition for low memory handling
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public LayoutInflater getLayoutInflater() {
        return (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public BaseActivity getBaseActivity() {
        return (BaseActivity) this.getActivity();
    }


    public List getLocalDataSource() {
        return localDataSource;
    }

    public void setLocalDataSource(List localDataSource) {
        this.localDataSource = localDataSource;
    }

    public void initViews() {
        //Injection Views
        ButterKnife.bind(this, mView);

        //set current base activity context
        mContext = getBaseActivity();

    }

    public void initObjects() {

    }

    public void initListenerOrAdapter() {

    }


    public void initNetworkCalls() {
        //setup network layer
        NetworkManager.setConfiguration(new NetworkConfig(getBaseActivity()));
    }


    //setup will be called by oncreateView
    private void setupFragment() {
        try {
            if (isFragmentLoaded.get() == false) {
                //1. Load object once
                initObjects();
            }

            // 2. inject view with butterknife or manually
            initViews();
            //3. rebind the views with listeners or adapter again for renewal created views.
            initListenerOrAdapter();

            if (isFragmentLoaded.get() == false) {
                //4. Network calls once
                initNetworkCalls();
            }

            //mark current fragment as loaded just recreate the views only.
            isFragmentLoaded.set(true);

        } catch (Exception e) {
            Log4a.printException(e);
        }
    }

    protected void showLoader() {
        CircularLoader.showProgressLoader(mContext);
    }

    protected void hideLoader() {
        CircularLoader.hideProgressLoader();
    }


    protected void captureCameraPicture() {
        ImageUtil.captureCameraImage(getBaseActivity(), CAPTURE_IMAGE_REQUEST_CODE);
    }

    protected String getCaptureCameraPictureFilePath() throws Exception {
        if (captureImageURI != null && captureImageURI.getPath() != null) {
            return captureImageURI.getPath();
        } else {
            throw new Exception("File not saved or captured");
        }
    }

    protected Bitmap getCaptureCameraPictureBitmap(int sampleSize) throws Exception {
        return FileUtil.getResizeBitmapObject(getCaptureCameraPictureFilePath(), sampleSize);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (captureImageURI == null) {
            outState.putString(captureImageURIKey, "");
        } else {
            outState.putString(captureImageURIKey, captureImageURI.toString());
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getBaseActivity().RESULT_OK) {
            if (requestCode == 1) {
                Log4a.d("Capture Image Name = ", FileUtil.getFileNameFromURI(captureImageURI));
            }
        } else if (resultCode == getBaseActivity().RESULT_CANCELED) {
            Intent returnIntent = new Intent();
            getBaseActivity().setResult(getBaseActivity().RESULT_CANCELED, returnIntent);
            getBaseActivity().finish();
        }
    }


    @Override
    public void responseWithError(Exception error, BaseNetworkRequest request) {
        try {
            Log4a.e("Webservice error", error == null || error.getMessage() == null ? "Exception" : error.getMessage() + "Data fetched for Request URL = " + request.getNetworkRequestEnum().getServiceName());
            hideLoader();
        } catch (Exception e) {
            Log4a.printException(e);
        }
    }

    @Override
    public void successWithData(Object data, BaseNetworkRequest request) {
        try {
            Log4a.e("Webservice success", "Data fetched for Request URL = " + request.getNetworkRequestEnum().getServiceName());
            hideLoader();
        } catch (Exception e) {
            Log4a.printException(e);
        }
    }
}
