package com.github.warrocker.githubproject.entity;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Warrocker.
 */
@Getter
@Setter
public class UserDetails extends User {
    private String name;
    private String company;
    private String blog;
    private String location;
    private String email;
    private Boolean hireable;
    private String bio;
    @SerializedName("private_repos")
    private Integer privateRepos;
    @SerializedName("private_gists")
    private Integer privateGists;
    private Integer followers;
    private Integer following;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
}
