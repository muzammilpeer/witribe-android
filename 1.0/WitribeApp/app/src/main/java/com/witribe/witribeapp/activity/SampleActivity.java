package com.witribe.witribeapp.activity;

import android.os.Bundle;

import com.ranisaurus.baselayer.activity.BaseActivity;
import com.witribe.witribeapp.R;
import com.witribe.witribeapp.fragment.TestFragment;

/**
 * Created by muzammilpeer on 10/25/15.
 */
public class SampleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        addFragment(new TestFragment(),R.id.container_main);


    }


}
