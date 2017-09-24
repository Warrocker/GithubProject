package com.github.warrocker.githubproject.core;

import android.app.Application;

import org.androidannotations.annotations.EApplication;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Warrocker.
 */

@EApplication
public class TheApplication extends Application {
    @Getter
    @Setter
    private boolean hasNetworkConnection = true;
}
