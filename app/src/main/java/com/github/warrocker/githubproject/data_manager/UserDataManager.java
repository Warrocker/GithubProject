package com.github.warrocker.githubproject.data_manager;

import com.github.warrocker.githubproject.entity.Project;
import com.github.warrocker.githubproject.entity.UserDetails;
import com.github.warrocker.githubproject.entity.UsersStructure;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

/**
 * Created by Warrocker.
 */
@Setter
@EBean(scope = EBean.Scope.Singleton)
public class UserDataManager {
    private UserDetails userDetails;
    private UsersStructure usersStructure;
    private List<Project> usersProjects;

    public UserDetails getUserDetails() {
        if (userDetails == null)
            return new UserDetails();
        return userDetails;
    }

    public UsersStructure getUsersStructure() {
        if (usersStructure == null)
            return new UsersStructure();
        return usersStructure;
    }
    public List<Project> getUsersProjects() {
        if (usersProjects == null)
            return new ArrayList<>(0);
        return usersProjects;
    }
}