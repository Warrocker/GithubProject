package com.github.warrocker.githubproject.custom_view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.github.warrocker.githubproject.core.ActivityContextKeeper;

/**
 * Created by Warrocker.
 */
public class ArrowBackImageView extends android.support.v7.widget.AppCompatImageView implements View.OnClickListener {

    public ArrowBackImageView(Context context) {
        super(context);
        setOnClickListener(this);
    }

    public ArrowBackImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    public ArrowBackImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ((Activity) ActivityContextKeeper.getInstance().getContext().getMainContext()).onBackPressed();
    }
}