package com.github.warrocker.githubproject.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EBean;

import com.github.warrocker.githubproject.core.TheApplication;

/**
 * Created by Warrocker.
 */
@EBean
public class NetworkUtils {
    @App
    TheApplication theApplication;

    public boolean hasNetworkConnection() {
        ConnectivityManager cm = (ConnectivityManager) theApplication.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}