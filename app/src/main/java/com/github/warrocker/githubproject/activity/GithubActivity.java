package com.github.warrocker.githubproject.activity;

import android.app.Fragment;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.github.warrocker.githubproject.R;
import com.github.warrocker.githubproject.core.ActivityContextKeeper;
import com.github.warrocker.githubproject.core.NetworkData;
import com.github.warrocker.githubproject.core.Preferences_;
import com.github.warrocker.githubproject.core.http.NetworkConnectivityAware_;
import com.github.warrocker.githubproject.fragment.BaseFragment;
import com.github.warrocker.githubproject.fragment.SplashFragment_;
import com.github.warrocker.githubproject.utils.FragmentUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;



@EActivity(R.layout.activity_github)
public class GithubActivity extends BaseActivity {
    @Pref
    Preferences_ preferences;

    NetworkConnectivityAware_ receiver;

    @AfterViews
    void afterViews() {
        ActivityContextKeeper.getInstance().setContext(this);
        FragmentUtils.replaceFragment(new SplashFragment_(), false);
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.container;
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkConnectivityAware_();
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onStop() {
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
        super.onStop();
    }

    @Subscribe
    public void onNetworkStateChanged(NetworkData networkData) {
        if (networkData.isConnected()) {
            Fragment fragmentById = getFragmentManager().findFragmentById(getFragmentContainerId());
            if (fragmentById != null && fragmentById instanceof BaseFragment) {
                ((BaseFragment) fragmentById).onNetworkEstablished();
            }
        } else {
            //Show info
            Toast.makeText(this, R.string.network_error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public Context getMainContext() {
        return this;
    }

    @Override
    public int getContainer() {
        return R.id.container;
    }
}