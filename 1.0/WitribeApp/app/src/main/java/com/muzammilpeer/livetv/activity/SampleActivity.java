package com.muzammilpeer.livetv.activity;

import android.os.Bundle;

import com.ranisaurus.baselayer.activity.BaseActivity;
import com.muzammilpeer.livetv.R;
import com.muzammilpeer.livetv.fragment.TestFragment;

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
