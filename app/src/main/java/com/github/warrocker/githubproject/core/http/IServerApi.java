package com.github.warrocker.githubproject.core.http;

import com.github.warrocker.githubproject.entity.Project;
import com.github.warrocker.githubproject.entity.UserDetails;
import com.github.warrocker.githubproject.entity.UsersStructure;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Warrocker.
 */

public interface IServerApi {

    @GET("/search/users")
    Call<UsersStructure> getUserList(@Query(value = "q", encoded = true) String searchField, @Query("page") int page);

    @GET("/user/{id}")
    Call<UserDetails> getUserDetails(@Path("id") String id);

    @GET("/orgs/{name}/repos")
    Call<List<Project>> getUserProjects(@Path("name") String name);
}