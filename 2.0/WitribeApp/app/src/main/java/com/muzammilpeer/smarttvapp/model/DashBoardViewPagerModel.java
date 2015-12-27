package com.muzammilpeer.smarttvapp.model;

import com.ranisaurus.baselayer.fragment.BaseFragment;
import com.ranisaurus.utilitylayer.reflection.ReflectionUtil;

/**
 * Created by muzammilpeer on 12/26/15.
 */
public class DashBoardViewPagerModel {

    private int fragmentTitleID;
    private Object fragmentObject;

    public DashBoardViewPagerModel(int fragmentTitleID, Object fragmentObject) {
        this.fragmentObject = fragmentObject;
        this.fragmentTitleID = fragmentTitleID;
    }

    public DashBoardViewPagerModel(int fragmentTitleID, Class fragmentClazz) {
        this.fragmentObject = ReflectionUtil.instantiate(fragmentClazz);
        this.fragmentTitleID = fragmentTitleID;
    }

    public int getFragmentTitleID() {
        return fragmentTitleID;
    }

    public void setFragmentTitleID(int fragmentTitleID) {
        this.fragmentTitleID = fragmentTitleID;
    }

    public BaseFragment getFragmentObject() {
        return (BaseFragment)fragmentObject;
    }

    public void setFragmentObject(Object fragmentObject) {
        this.fragmentObject = fragmentObject;
    }
}
