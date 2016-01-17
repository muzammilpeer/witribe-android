package com.ranisaurus.baselayer.fragment;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.v17.leanback.app.BrowseFragment;
import android.view.LayoutInflater;
import android.view.View;

import com.ranisaurus.baselayer.activity.BaseActivity;
import com.ranisaurus.baselayer.view.CircularLoader;
import com.ranisaurus.newtorklayer.manager.NetworkConfig;
import com.ranisaurus.newtorklayer.manager.NetworkManager;
import com.ranisaurus.newtorklayer.protocols.IResponseProtocol;
import com.ranisaurus.newtorklayer.requests.BaseNetworkRequest;
import com.ranisaurus.utilitylayer.logger.Log4a;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import butterknife.ButterKnife;

/**
 * Created by muzammilpeer on 1/2/16.
 */
public class BaseBrowseFragment extends BrowseFragment implements IResponseProtocol {

    protected View mView; //main layout inflated by fragment
    protected Context mContext;
    protected AtomicBoolean isFragmentLoaded = new AtomicBoolean(false);
    private List localDataSource = new ArrayList();


    // custom onCreateView
    public void onCreateView(LayoutInflater inflater, int resource) {
        mView = inflater.inflate(resource, null, true);
        setupFragment();
    }


    public LayoutInflater getLayoutInflater() {
        return (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
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
//        getBaseActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
        //set current base activity context
        mContext = getBaseActivity();
    }

    public void initObjects() {

    }

    public void initListenerOrAdapter() {

    }


    public void initNetworkCalls() {
        //setup network layer
//        NetworkManager.setConfiguration(new NetworkConfig(getBaseActivity()));
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

    protected void hideLoader(boolean isError) {
        CircularLoader.hideProgressLoader();
    }


    @Override
    public void responseWithError(Exception error, BaseNetworkRequest request) {
        hideLoader(true);
        try {
            Log4a.e("Webservice error", error == null || error.getMessage() == null ? "Exception" : error.getMessage() + "Data fetched for Request URL = " + request.getNetworkRequestEnum().getServiceName() + " , Method Name = " + request.getNetworkRequestEnum().getMethodName());
        } catch (Exception e) {
            Log4a.printException(e);
        }
    }

    @Override
    public void successWithData(Object data, BaseNetworkRequest request) {
        hideLoader(false);
        try {
            Log4a.e("Webservice success", "Data fetched for Request URL = " + request.getNetworkRequestEnum().getServiceName() + " , Method Name = " + request.getNetworkRequestEnum().getMethodName());
        } catch (Exception e) {
            Log4a.printException(e);
        }
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
}
