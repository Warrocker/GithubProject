package com.github.warrocker.githubproject.interfaces;

import android.content.Context;

/**
 * Created by Warrocker.
 */
public interface IActivityContextSender {
    Context getMainContext();

    int getContainer();
    // TODO not for this project
//    void stopProgressBar();
//    void runProgressBar();
//    void stopProgressDialog();
//    void runProgressDialog(String title, String message);
//    void setTitleToActionBar(String title);
//    void updateCounter();
//    void updatePrivilegesAndUI();
}