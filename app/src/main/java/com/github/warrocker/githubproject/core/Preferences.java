package com.github.warrocker.githubproject.core;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by Warrocker.
 */
@SharedPref(value = SharedPref.Scope.APPLICATION_DEFAULT)
public interface Preferences {

    @DefaultString(value = "")
    String token();

    @DefaultString(value = "")
    String user();

    @DefaultBoolean(value = true)
    boolean shouldShowTutorial();
}