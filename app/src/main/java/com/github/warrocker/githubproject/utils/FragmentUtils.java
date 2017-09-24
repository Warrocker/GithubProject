package com.github.warrocker.githubproject.utils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.github.warrocker.githubproject.R;
import com.github.warrocker.githubproject.core.ActivityContextKeeper;
import com.github.warrocker.githubproject.interfaces.IActivityContextSender;

/**
 * Created by Warrocker.
 */
public abstract class FragmentUtils {

    public static void replaceFragment(Fragment fragment, boolean addToBackStack) {
        replaceFragment(fragment, null, addToBackStack);
    }

    public static void addFragment(Fragment fragment, boolean addToBackStack) {
        addFragment(fragment, null, addToBackStack);
    }

    public static void addFragment(Fragment fragment, Bundle args, boolean addToBackStack) {
        changeFragmentInternal(fragment, args, addToBackStack, false);
    }

    public static void replaceFragment(Fragment fragment, Bundle args, boolean addToBackStack) {
        changeFragmentInternal(fragment, args, addToBackStack, true);
    }

    private static void changeFragmentInternal(Fragment fragment, Bundle args, boolean addToBackStack, boolean asReplace) {
        IActivityContextSender sender = ActivityContextKeeper.getInstance().getContext();
        FragmentTransaction transaction = ((Activity) sender).getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.animator.fade_in, R.animator.fade_out, R.animator.fade_in, R.animator.fade_out);
        fragment.setArguments(args);
        String name = fragment.getClass().getName();
        if (asReplace) {
            transaction.replace(sender.getContainer(), fragment, name);
        } else {
            transaction.add(sender.getContainer(), fragment, name);
        }
        if (addToBackStack) transaction.addToBackStack(name);
        transaction.commit();
    }
}