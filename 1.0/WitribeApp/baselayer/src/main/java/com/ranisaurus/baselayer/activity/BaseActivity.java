package com.ranisaurus.baselayer.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.view.View;

import com.koushikdutta.ion.Ion;
import com.ranisaurus.utilitylayer.logger.Log4a;

import java.util.concurrent.atomic.AtomicBoolean;

import butterknife.ButterKnife;

abstract public class BaseActivity extends AppCompatActivity {

    protected AtomicBoolean isFragmentLoaded = new AtomicBoolean(false);
    protected ActionBarDrawerToggle mToggle;
    protected DrawerLayout mDrawer;
    protected Toolbar mainToolbar;

    private TabLayout tabLayoutView;

    public boolean isFullScreenOptionEnable = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void initViews() {
        //Injection Views
        ButterKnife.bind(this);
    }

    public void initObjects() {

    }

    public void initListenerOrAdapter() {

    }


    public void initNetworkCalls() {
        //setup network layer
//        NetworkManager.setConfiguration(new NetworkConfig(this);
    }


    public void refreshNavigationViewData()
    {

    }

    public void setupNavigationDrawer()
    {

    }


    //setup will be called by oncreateView
    protected void setupActivity() {
        try {
            // 1. inject view with butterknife or manually
            initViews();

            if (isFragmentLoaded.get() == false) {
                //2. Load object once
                initObjects();
                //3. Network calls once
                initNetworkCalls();
            }

            //4. rebind the views with listeners or adapter again for renewal created views.
            initListenerOrAdapter();


            //mark current fragment as loaded just recreate the views only.
            isFragmentLoaded.set(true);

        } catch (Exception e) {
            Log4a.printException(e);
        }
    }


    public void refreshNavigationToolbar() {

    }

    public Toolbar getMainToolbar() {
        return mainToolbar;
    }

    public TabLayout getTabLayoutView() {
        return tabLayoutView;
    }

    public void setTabLayoutView(TabLayout tabLayoutView) {
        this.tabLayoutView = tabLayoutView;
    }

    public void restoreToolBarColorWithStatusBar() {

    }

    public void changeToolBarColorWithStatusBar(int color) {

    }

    public void showBackButton() {

    }

    public void hideBackButton() {

    }

    public void showToolBar() {
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().show();
            getSupportActionBar().setHideOffset(0);
        }
    }

    public void hideToolBar() {
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }
    }

    public void setScreenTitle(int title) {

    }

    public void hideKeyboard() {
//        View view = getCurrentFocus();
//        if (view != null) {
//            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
//                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//        }
    }

    public void replaceFragment(Fragment sourceFragment,Fragment destinationFragment, int containerID,int transitionElementID,int transitionID,String shared_element_key,String add_to_back_stack_key) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        // Check that the device is running lollipop
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

//             Inflate transitions to apply
//            Transition changeTransform = TransitionInflater.from(this).
//                    inflateTransition(android.R.transition.slide_left);
//            Transition explodeTransform = TransitionInflater.from(this).
//                    inflateTransition(android.R.transition.no_transition);

            // Setup exit transition on first fragment
            sourceFragment.setSharedElementReturnTransition(TransitionInflater.from(this).
                    inflateTransition(android.R.transition.explode));
            sourceFragment.setExitTransition(TransitionInflater.from(this).
                    inflateTransition(android.R.transition.no_transition));

            // Setup enter transition on second fragment
            destinationFragment.setSharedElementEnterTransition(TransitionInflater.from(this).
                    inflateTransition(android.R.transition.no_transition));
            destinationFragment.setEnterTransition(TransitionInflater.from(this).
                    inflateTransition(android.R.transition.explode));

            // Find the shared element (in Fragment A)
            View ivProfile = (View) findViewById(transitionElementID);

            // Add second fragment by replacing first
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction()
                    .replace(containerID, destinationFragment)
                    .addToBackStack(add_to_back_stack_key)
                    .addSharedElement(ivProfile, shared_element_key);
            // Apply the transaction
            ft.commit();
        }
        else {
            // Code to run on older devices
            fragmentManager
                    .beginTransaction()
                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    .replace(containerID, destinationFragment)
                    .addToBackStack(null)
                    .commit();
        }

    }

    public void replaceFragmentWithoutStack(Fragment frag, int containerID) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(containerID, frag).commit();
    }

    public void addFragment(Fragment frag, int containerID) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(containerID, frag).commit();
    }


    public void popAllFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        for (int i = 0; i < manager.getBackStackEntryCount(); ++i) {
            trans.remove(manager.getFragments().get(i));
        }
        trans.commit();
        manager.popBackStack();
    }

    public void popFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        trans.remove(fragment);
        trans.commit();
        manager.popBackStack();
    }

    public void popFragmentTillLast() {
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount() - 1; ++i) {
            fm.popBackStack();
        }
    }

    public Fragment getLastFragment() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() >= 0) {
            return fm.getFragments().get(fm.getBackStackEntryCount());
        }
        return null;
    }

    public int getFragmentsCount() {
        FragmentManager fm = getSupportFragmentManager();
        return fm.getBackStackEntryCount();
    }

    //recording features

//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    abstract public void startScreenRecording();
//
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    abstract public void stopScreenRecording();

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            Ion.getDefault(this).cancelAll(getApplicationContext());
        } catch (Exception e) {
            Log4a.printException(e);
        }
    }


}
