package com.github.warrocker.githubproject.core.http;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;

import com.github.warrocker.githubproject.BuildConfig;
import com.github.warrocker.githubproject.R;
import com.github.warrocker.githubproject.core.ActivityContextKeeper;
import com.github.warrocker.githubproject.core.Preferences_;
import com.github.warrocker.githubproject.core.TheApplication;
import com.github.warrocker.githubproject.core.configuration.GsonConfiguredFactory;
import lombok.Getter;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Warrocker.
 */
@EBean(scope = EBean.Scope.Singleton)
public class NetworkModule {

    @Getter
    private IServerApi iServerApi;
    @Pref
    Preferences_ preferences;
    @App
    TheApplication application;

    public static final String BASE_URL = "https://api.github.com";

    @AfterInject
    public void afterInject() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonConfiguredFactory.getGson()))
                .validateEagerly(true)
                // no rx for this
                // .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(provideHttpClient());
        Retrofit build = builder.build();
        iServerApi = build.create(IServerApi.class);
    }

    private OkHttpClient provideHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.BASIC);

        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .followRedirects(false)
                .build();
    }

    public void showFailureProblem(Throwable t, String errorMessage) {
        Context context = ActivityContextKeeper.getInstance().getContext().getMainContext();
        Toast.makeText(context, R.string.failure_error, Toast.LENGTH_LONG).show();
        Log.d("RETROFIT ERROR", errorMessage, t);
    }
}