package com.github.warrocker.githubproject.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.Setter;

/**
 * Created by Warrocker.
 */

public abstract class BaseAdapter<T, V extends BaseViewHolder> extends RecyclerView.Adapter<V> {

    protected Context context;
    private List<T> list = new ArrayList<>();
    @Setter
    private IRecyclerTouchListener<T> recyclerTouchListener;

    public BaseAdapter(Context context) {
        this.context = context;
        setHasStableIds(true);
    }

    public void remove(T data) {
        Iterator<T> iterator = list.iterator();
        int position = -1;
        while (iterator.hasNext()) {
            position++;
            T next = iterator.next();
            if (next.equals(data)) {
                iterator.remove();
                break;
            }
        }
        if (position != -1) {
            notifyItemRemoved(position);
        }
    }

    protected T getItemById(int id) {
        return list.get(id);
    }

    protected View inflateView(@LayoutRes int layoutId, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(layoutId, viewGroup, false);
    }

    public void clear() {
        this.list.clear();
        notifyDataSetChanged();
    }

    public void addData(T data) {
        if (list.contains(data)) return;
        this.list.add(data);
        notifyItemInserted(list.size() - 1);
    }

    public void addData(@NonNull List<T> data) {
        int size = getItemCount();
        this.list.addAll(data);
        notifyItemRangeInserted(size, this.list.size());
    }

    public void addDataToStart(@NonNull List<T> data) {
        int size = getItemCount();
        if (this.list.isEmpty()) {
            this.list.addAll(data);
        } else {
            this.list.addAll(0, data);
        }
        notifyItemRangeInserted(0, data.size());
    }

    public void setData(List<T> list) {
        this.list = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    public void setupTouchListener(View view, T object, int position) {
        view.setOnClickListener(v -> {
            if (recyclerTouchListener != null) recyclerTouchListener.onTouch(object, position);
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public List<T> getData() {
        return list;
    }
}