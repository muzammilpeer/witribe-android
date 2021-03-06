package com.muzammilpeer.smarttvapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.muzammilpeer.smarttvapp.MainActivity;
import com.muzammilpeer.smarttvapp.R;
import com.muzammilpeer.smarttvapp.manager.UserManager;
import com.ranisaurus.baselayer.fragment.BaseFragment;
import com.ranisaurus.newtorklayer.enums.NetworkRequestEnum;
import com.ranisaurus.newtorklayer.manager.NetworkManager;
import com.ranisaurus.newtorklayer.models.DataSingleResponseModel;
import com.ranisaurus.newtorklayer.requests.BaseNetworkRequest;
import com.ranisaurus.newtorklayer.requests.WitribeAMFRequest;
import com.ranisaurus.utilitylayer.logger.Log4a;
import com.ranisaurus.utilitylayer.network.GsonUtil;

import butterknife.Bind;

/**
 * Created by muzammilpeer on 10/12/15.
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener {

    // UI references.
    @Bind(R.id.email)
    EditText mEmailView;

    @Bind(R.id.password)
    EditText mPasswordView;

    @Bind(R.id.email_sign_in_button)
    Button mEmailSignInButton;

    @Bind(R.id.login_progress)
    View mProgressView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, R.layout.fragment_login);

        return mView;
    }


    @Override
    public void initViews() {
        super.initViews();

    }

    @Override
    public void initObjects() {
        super.initObjects();

    }

    @Override
    public void initListenerOrAdapter() {
        super.initListenerOrAdapter();

        mEmailSignInButton.setOnClickListener(this);
    }

    @Override
    public void initNetworkCalls() {
        super.initNetworkCalls();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.email_sign_in_button: {
                Log4a.e("Login request ", "hit");
                loginDataRequest();
            }
            break;

        }

    }

    private void loginDataRequest() {
        showLoader();
        String[] params = new String[2];
        params[0] = mEmailView.getText().toString() + "";
        params[1] = mPasswordView.getText().toString() + "";

        WitribeAMFRequest request = new WitribeAMFRequest(params, NetworkRequestEnum.LOGIN_WITRIBE_USER);
        try {
            NetworkManager.getInstance().executeRequest(request, this);

        } catch (Exception e) {
            Log4a.printException(e);
        }
    }

    @Override
    public void responseWithError(Exception error, BaseNetworkRequest request) {
        super.responseWithError(error, request);
        try {
            if (mView != null) {
                switch (request.getNetworkRequestEnum()) {
                    case LOGIN_WITRIBE_USER: {
                        hideLoader(true);
                        Log4a.e("LoginError ", "some error in network");
                        Snackbar.make(mView, getString(R.string.invalid_credentials), Snackbar.LENGTH_SHORT).show();
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
                        hideLoader(false);
                        DataSingleResponseModel model = (DataSingleResponseModel) GsonUtil.getObjectFromJsonObject(data, DataSingleResponseModel.class);
                        Log4a.e("LoginResponse ", model.toString() + "");

                        if (model.getData().response.getToken().length() > 0) {
                            UserManager.getInstance().saveUserProfile(model.getData().response);
                            Snackbar.make(mView, getString(R.string.valid_credentials), Snackbar.LENGTH_SHORT).show();

                            getBaseActivity().popAllFragment();
                            Log4a.e("Fragment Count after login  = ", getBaseActivity().getFragmentsCount() + "");
                            Intent intent = new Intent(getBaseActivity(), MainActivity.class);
                            startActivity(intent);
                            getBaseActivity().finish();
                        }else {
                            Snackbar.make(mView, getString(R.string.invalid_credentials), Snackbar.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
            }
        } catch (Exception e) {
            Log4a.printException(e);
        }
    }


}
