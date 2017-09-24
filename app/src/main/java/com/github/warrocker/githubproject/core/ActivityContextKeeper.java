package com.github.warrocker.githubproject.core;

import com.github.warrocker.githubproject.interfaces.IActivityContextSender;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Warrocker.
 */
public class ActivityContextKeeper {

    private static ActivityContextKeeper instance;
    @Setter
    @Getter
    private IActivityContextSender context;

    private ActivityContextKeeper() {
    }

    public static ActivityContextKeeper getInstance() {
        if (instance == null)
            instance = new ActivityContextKeeper();
        return instance;
    }
}