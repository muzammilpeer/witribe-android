package com.witribe.witribeapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.witribe.witribeapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by muzammilpeer on 10/25/15.
 */
public class TestFragment extends Fragment {

    @Bind(R.id.etTest)
    EditText etTest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View mView = inflater.inflate(R.layout.fragment_test, null, true);
        ButterKnife.bind(this, mView);
        etTest.setHint("enter some test");

        return  mView;
    }

}
