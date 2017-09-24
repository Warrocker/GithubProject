package com.github.warrocker.githubproject.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.warrocker.githubproject.R;
import com.github.warrocker.githubproject.activity.GithubActivity;
import com.github.warrocker.githubproject.adapters.RvUsersAdapter;
import com.github.warrocker.githubproject.core.DataProvider;
import com.github.warrocker.githubproject.core.EndlessRecyclerViewScrollListener;
import com.github.warrocker.githubproject.core.ItemOffsetDecorator;
import com.github.warrocker.githubproject.core.TheApplication;
import com.github.warrocker.githubproject.data_manager.UserDataManager;
import com.github.warrocker.githubproject.entity.User;
import com.github.warrocker.githubproject.entity.UsersStructure;
import com.github.warrocker.githubproject.utils.FragmentUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by Warrocker.
 */
@EFragment(R.layout.fragment_user_list)
public class UserListFragment extends BaseFragment {

    @Bean
    DataProvider dataProvider;
    @App
    TheApplication app;
    @Bean
    UserDataManager userDataManager;
    @ViewById
    SearchView svSearch;
    @ViewById
    RecyclerView rv;
    @ViewById
    ProgressBar progressBar;
    @ViewById
    TextView tvTitle, tvEmpty;
    @ViewById
    RelativeLayout rlHeader;

    public static final int PER_PAGE = 30;

    Handler handler;
    private RvUsersAdapter adapter;
    boolean isLoading;
    boolean isEndOfPhotos;

    @AfterViews
    void afterViews() {
        svSearch.setIconified(false);
        svSearch.setQueryHint(getString(R.string.search_hint));

        adapter = new RvUsersAdapter(getActivity());
        adapter.setRecyclerTouchListener((user, position) -> {

            InputMethodManager imm = (InputMethodManager) app.getSystemService(GithubActivity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(rv.getWindowToken(), 0);

            Bundle args = new Bundle();
            args.putSerializable(RepoFragment.KEY, user);
            FragmentUtils.addFragment(new RepoFragment_(), args, true);
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        rv.setLayoutManager(linearLayoutManager);
        rv.addItemDecoration(new ItemOffsetDecorator());
        rv.setAdapter(adapter);
        rv.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));

        EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                queryData(page);
            }
        };

        rv.addOnScrollListener(endlessRecyclerViewScrollListener);

        handler = new Handler();
        Runnable runnable = () -> {
            if (getActivity() == null || svSearch == null) return;
            endlessRecyclerViewScrollListener.resetState();
            userDataManager.setUsersStructure(new UsersStructure());
            adapter.clear();
            tvEmpty.setVisibility(View.GONE);
            if (svSearch.getQuery().length() >= 3) {
                rlHeader.animate().y(0).setDuration(500);
                queryData(0);
            }
        };

        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    rlHeader.animate().y(80).setDuration(500);
                }
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 300);
                return false;
            }
        });

    }

    @Override
    public void onNetworkEstablished() {
        super.onNetworkEstablished();
        if(svSearch.getQuery().toString().length() >= 2) {
            userDataManager.setUsersStructure(new UsersStructure());
            adapter.clear();
            queryData(0);
        }
    }

    private void queryData(int page) {
        switchLoading(true);
        progressBar.setVisibility(View.VISIBLE);
        dataProvider.getUserList(isSuccess -> {
            if (getActivity() == null || svSearch == null) return;

            switchLoading(false);
            List<User> list = userDataManager.getUsersStructure().getUsers();
            if (adapter.isEmpty()) {
                adapter.setData(list);
            } else {
                adapter.addData(list);
            }
            if (list.size() < PER_PAGE)
                isEndOfPhotos = true;
            tvEmpty.setVisibility(list.isEmpty() ? View.VISIBLE : View.GONE);
        }, DataProvider.TYPE_ORG, svSearch.getQuery().toString(), page);
    }

    private void switchLoading(boolean isLoad) {
        if (isLoad) {
            isLoading = true;
            progressBar.setVisibility(View.VISIBLE);
        } else {
            isLoading = false;
            progressBar.setVisibility(View.GONE);
        }
    }
}