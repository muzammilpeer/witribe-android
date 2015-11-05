package com.witribe.witribeapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.ranisaurus.baselayer.activity.BaseActivity;
import com.witribe.witribeapp.MainActivity;
import com.witribe.witribeapp.R;
import com.witribe.witribeapp.fragment.LoginFragment;
import com.witribe.witribeapp.manager.UserManager;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by muzammilpeer on 11/5/15.
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);


        //setup first screen
        if (savedInstanceState == null) {

            if (UserManager.getInstance().isUserLoggedIn()) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                LoginFragment fragment = new LoginFragment();
                addFragment(fragment, R.id.container_main);
            }
        }
    }

}
