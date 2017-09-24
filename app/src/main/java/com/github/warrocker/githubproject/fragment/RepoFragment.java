package com.github.warrocker.githubproject.fragment;

import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.github.warrocker.githubproject.R;
import com.github.warrocker.githubproject.adapters.RvRepoAdapter;
import com.github.warrocker.githubproject.adapters.RvUsersAdapter;
import com.github.warrocker.githubproject.core.DataProvider;
import com.github.warrocker.githubproject.core.ItemOffsetDecorator;
import com.github.warrocker.githubproject.data_manager.UserDataManager;
import com.github.warrocker.githubproject.entity.Project;
import com.github.warrocker.githubproject.entity.User;
import com.github.warrocker.githubproject.entity.UsersStructure;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by Warrocker.
 */
@EFragment(R.layout.fragment_user_repo)
public class RepoFragment extends BaseFragment {

    public static final String KEY = "REPO";

    @ViewById
    TextView tvTitle;
    @ViewById
    RecyclerView rv;
    @ViewById
    ProgressBar progressBar;

    RvRepoAdapter adapter;

    @FragmentArg(value = KEY)
    User user;

    @Bean
    UserDataManager userDataManager;

    @Bean
    DataProvider dataProvider;
    String titleText;
    @AfterViews
    protected void initUI(){
        titleText = user.getLogin() + " " + getString(R.string.repos);
        tvTitle.setText(titleText);
        adapter = new RvRepoAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        rv.setLayoutManager(linearLayoutManager);
        rv.addItemDecoration(new ItemOffsetDecorator());
        rv.setAdapter(adapter);
        rv.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
        queryData();

    }
    @Override
    public void onNetworkEstablished() {
        super.onNetworkEstablished();
        adapter.clear();
        queryData();
    }

    private void queryData() {
        switchLoading(true);
        progressBar.setVisibility(View.VISIBLE);
        dataProvider.getProjectsByUser(isSuccess -> {
            List<Project> usersProjects = userDataManager.getUsersProjects();
            adapter.setData(usersProjects);
            tvTitle.setText(new StringBuilder(titleText).append("(").append(usersProjects.size()).append(")"));
            switchLoading(false);
        }, user.getLogin());
    }

    private void switchLoading(boolean isLoad) {
        if (isLoad) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
