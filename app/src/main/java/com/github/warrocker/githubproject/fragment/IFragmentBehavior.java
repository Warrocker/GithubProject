package com.github.warrocker.githubproject.fragment;

/**
 * Created by Warrocker.
 * <p>
 * Bridge between activity back press and fragment
 */
public interface IFragmentBehavior {
    /**
     * Define behavior
     *
     * @return If return false then activity will be used default behavior ({@code {@link com.github.warrocker.githubproject.activity.BaseActivity#onBackPressed()}})
     */
    boolean isBackPressOverride();
}