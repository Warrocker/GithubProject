package com.github.warrocker.githubproject.core;


import com.github.warrocker.githubproject.core.http.NetworkModule;
import com.github.warrocker.githubproject.data_manager.UserDataManager;
import com.github.warrocker.githubproject.entity.Project;
import com.github.warrocker.githubproject.entity.UserDetails;
import com.github.warrocker.githubproject.entity.UsersStructure;
import com.github.warrocker.githubproject.interfaces.IResultCallback;
import com.github.warrocker.githubproject.utils.NetworkUtils;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.util.List;

import lombok.Getter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Warrocker.
 */
@EBean(scope = EBean.Scope.Singleton)
public class DataProvider {

    private static final String RETROFIT_ONFAILURE = "RETROFIT ONFAILURE";


    public static final String TYPE_ORG = "type:org";

    @Getter
    @Bean
    NetworkModule networkModule;
    @Bean
    UserDataManager userDataManager;
    @Pref
    Preferences_ pref;
    @Bean
    NetworkUtils networkUtils;
    @App
    TheApplication application;

    private boolean hasNetwork() {
        return networkUtils.hasNetworkConnection();
    }

    public void getUserList(final IResultCallback callBack, String type, String searchField, int page) {
        final WeakReference<IResultCallback> ref = new WeakReference<>(callBack);

            networkModule.getIServerApi().getUserList(searchField + "+" + type, page).enqueue(new Callback<UsersStructure>() {
                @Override
                public void onResponse(Call<UsersStructure> call, Response<UsersStructure> response) {
                    int code = response.code();
                    IResultCallback callBack = ref.get();
                    if (code == HttpURLConnection.HTTP_OK) {
                        userDataManager.setUsersStructure(response.body());
                        callBack.initResult(true);
                    } else {
                        callBack.initResult(false);
                    }
                }


                @Override
                public void onFailure(Call<UsersStructure> call, Throwable t) {
                    networkModule.showFailureProblem(t, RETROFIT_ONFAILURE);
                    callBack.initResult(false);
                }
            });
    }

    public void getUserDetails(final IResultCallback callBack, String id) {
        final WeakReference<IResultCallback> ref = new WeakReference<>(callBack);

        networkModule.getIServerApi().getUserDetails(id).enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                int code = response.code();
                IResultCallback callBack = ref.get();
                if (code == HttpURLConnection.HTTP_OK) {
                    userDataManager.setUserDetails(response.body());
                    callBack.initResult(true);
                } else {
                    callBack.initResult(false);
                }
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                networkModule.showFailureProblem(t, RETROFIT_ONFAILURE);
                callBack.initResult(false);
            }
        });
    }
    public void getProjectsByUser(final IResultCallback callBack, String name) {
        final WeakReference<IResultCallback> ref = new WeakReference<>(callBack);

        networkModule.getIServerApi().getUserProjects(name).enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                int code = response.code();
                IResultCallback callBack = ref.get();
                if (code == HttpURLConnection.HTTP_OK) {
                    userDataManager.setUsersProjects(response.body());
                    callBack.initResult(true);
                } else {
                    callBack.initResult(false);
                }
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
                networkModule.showFailureProblem(t, RETROFIT_ONFAILURE);
                callBack.initResult(false);
            }
        });
    }
}