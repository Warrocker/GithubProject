package com.github.warrocker.githubproject.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.warrocker.githubproject.R;
import com.github.warrocker.githubproject.entity.Project;

/**
 * Created by Warrocker.
 */

public class RvRepoAdapter extends BaseAdapter<Project, RvRepoAdapter.Holder> {

    public RvRepoAdapter(Context context) {
        super(context);
        setHasStableIds(true);
    }

    @Override
    public RvRepoAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RvRepoAdapter.Holder(inflateView(R.layout.item_repo, parent));
    }

    @Override
    public void onBindViewHolder(RvRepoAdapter.Holder holder, int position) {
        Project project = getItemById(position);
        holder.tvTitle.setText(project.getName());
        holder.tvDesc.setText(project.getDescription());


    }

    class Holder extends BaseViewHolder {
        private TextView tvTitle;
        private TextView tvDesc;

        public Holder(View itemView) {
            super(itemView);
            tvTitle = getView(R.id.tvTitle);
            tvDesc = getView(R.id.tvDesc);
        }
    }
}
