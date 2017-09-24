package com.github.warrocker.githubproject.adapters;

/**
 * Created by Warrocker.
 */

public interface IRecyclerTouchListener<T> {
    void onTouch(T model, int position);
}
