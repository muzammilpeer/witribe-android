package com.muzammilpeer.smarttvapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.muzammilpeer.smarttvapp.MainActivity;
import com.muzammilpeer.smarttvapp.R;
import com.muzammilpeer.smarttvapp.fragment.LoginFragment;
import com.muzammilpeer.smarttvapp.manager.UserManager;
import com.ranisaurus.baselayer.activity.BaseActivity;

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
