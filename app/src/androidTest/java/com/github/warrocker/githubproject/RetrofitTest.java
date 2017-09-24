package com.github.warrocker.githubproject;

import com.github.warrocker.githubproject.core.http.IServerApi;
import com.github.warrocker.githubproject.core.http.NetworkModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.assertEquals;

/**
 * Created by Warrocker.
 */

public class RetrofitTest {
    private static IServerApi iService;
    private static final String TEST = "TEST";
    private static final String MOCK_SEARCH = "a";
    private static final String MOCK_SEARCH_FAILED = "";
    private static final String MOCK_USER_ID = "1";
    private static final String MOCK_USER_ID_FAILED = "-1";
    private static final String MOCK_USER_NAME = "google";
    private static final String MOCK_USER_NAME_FAILED = "";


    @Before
    public void setUpRetrofit() {
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder()
                    .header("cookie", "TRANSLATE_LANGUAGE=ru");
            Request request = requestBuilder.build();
            return chain.proceed(request);

        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkModule.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.connectTimeout(2000, TimeUnit.MILLISECONDS).build())
                .build();
        iService = retrofit.create(IServerApi.class);
    }


    @Test
    public void checkRetrofit() throws IOException {
        assertEquals(iService.getUserList(MOCK_SEARCH_FAILED, 0).execute().code() == HttpURLConnection.HTTP_OK, false);
        assertEquals(iService.getUserList(MOCK_SEARCH, 0).execute().code() == HttpURLConnection.HTTP_OK, true);
        assertEquals(iService.getUserDetails(MOCK_USER_ID_FAILED).execute().code() == HttpURLConnection.HTTP_OK, false);
        assertEquals(iService.getUserDetails(MOCK_USER_ID).execute().code() == HttpURLConnection.HTTP_OK, true);
        assertEquals(iService.getUserProjects(MOCK_USER_NAME_FAILED).execute().code() == HttpURLConnection.HTTP_OK, false);
        assertEquals(iService.getUserProjects(MOCK_USER_NAME).execute().code() == HttpURLConnection.HTTP_OK, true);
    }
}
