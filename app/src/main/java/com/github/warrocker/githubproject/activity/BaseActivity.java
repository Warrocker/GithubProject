package com.github.warrocker.githubproject.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.annotation.IdRes;

import com.github.warrocker.githubproject.fragment.IFragmentBehavior;
import com.github.warrocker.githubproject.interfaces.IActivityContextSender;

/**
 * Created by Warrocker.
 * */

public abstract class BaseActivity extends Activity implements IActivityContextSender {
    @IdRes
    protected abstract int getFragmentContainerId();

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(getFragmentContainerId());
        if (fragment != null && fragment instanceof IFragmentBehavior) {
            boolean backPressOverride = ((IFragmentBehavior) fragment).isBackPressOverride();
            if (backPressOverride) return;
        }
        if (fragmentManager.getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }
}
