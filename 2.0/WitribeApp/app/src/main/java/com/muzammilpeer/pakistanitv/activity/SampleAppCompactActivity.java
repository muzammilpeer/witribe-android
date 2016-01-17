package com.muzammilpeer.pakistanitv.activity;

import android.os.Bundle;

import com.muzammilpeer.pakistanitv.fragment.TestFragment;
import com.ranisaurus.baselayer.activity.BaseAppCompactActivity;
import com.muzammilpeer.pakistanitv.R;

/**
 * Created by muzammilpeer on 10/25/15.
 */
public class SampleAppCompactActivity extends BaseAppCompactActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        addFragment(new TestFragment(),R.id.container_main);


    }


}
