package com.github.warrocker.githubproject.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.warrocker.githubproject.R;
import com.github.warrocker.githubproject.entity.User;
import com.squareup.picasso.Picasso;


/**
 * Created by Warrocker.
 */
public class RvUsersAdapter extends BaseAdapter<User, RvUsersAdapter.Holder> {

    public RvUsersAdapter(Context context) {
        super(context);
        setHasStableIds(true);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(inflateView(R.layout.item_user, parent));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        User user = getItemById(position);
        holder.tvTitle.setText(user.getLogin());
        holder.tvLink.setText(user.getReposUrl());

        Picasso picasso = Picasso.with(context);
        String photoUrl = user.getAvatarUrl();

        picasso.load(photoUrl)
                .fit()
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.error)
                .into(holder.icon);

        setupTouchListener(holder.itemView, user, holder.getAdapterPosition());
    }

    class Holder extends BaseViewHolder {
        private ImageView icon;
        private TextView tvTitle;
        private TextView tvLink;

        public Holder(View itemView) {
            super(itemView);
            icon = getView(R.id.icon);
            tvTitle = getView(R.id.tvTitle);
            tvLink = getView(R.id.tvLink);
        }
    }
}