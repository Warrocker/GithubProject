package com.github.warrocker.githubproject.fragment;

import android.app.Fragment;

/**
 * Created by Warrocker.
 */
public class BaseFragment extends Fragment implements IFragmentBehavior, IFragmentLifecycleAware {

    private static final String TAG = BaseFragment.class.getSimpleName();

    protected boolean isFragmentAlive() {
        return getActivity() != null && !isDetached();
    }

    public void onNetworkEstablished() {
    }

    @Override
    public void onShown() {
    }

    @Override
    public void onHide() {
    }

    @Override
    public boolean isBackPressOverride() {
        return false;
    }
}