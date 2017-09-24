
package com.github.warrocker.githubproject.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsersStructure {
    private Integer totalCount;
    private Boolean incompleteResults;
    @SerializedName("items")
    private List<User> users = new ArrayList<>();
}