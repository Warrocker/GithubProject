package com.github.warrocker.githubproject.fragment;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.warrocker.githubproject.R;
import com.github.warrocker.githubproject.core.DataProvider;
import com.github.warrocker.githubproject.data_manager.UserDataManager;
import com.github.warrocker.githubproject.entity.User;
import com.github.warrocker.githubproject.entity.UserDetails;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.text.MessageFormat;


/**
 * Created by Warrocker.
 */
@EFragment(R.layout.fragment_user_details)
public class UserDetailsFragment extends BaseFragment {

    public static final String KEY = "DATA";
    @Bean
    DataProvider dataProvider;
    @Bean
    UserDataManager dataManager;
    @Bean
    UserDataManager userDataManager;

    @ViewById
    ImageView ivPhoto, ivLoader;
    @ViewById
    EditText etLogin, etName;
    @ViewById
    TextView tvFollowers, tvRepos;


    @FragmentArg(value = KEY)
    User user;

    @AfterViews
    public void show() {
        // loading once again - in case of different size
        dataProvider.getUserDetails(isSuccess -> {
            if(getActivity() == null || etLogin == null) return;
            if(isSuccess) {
                UserDetails userDetails = dataManager.getUserDetails();
                etLogin.setText(userDetails.getLogin());
                etName.setText(userDetails.getName());
                tvFollowers.setText(MessageFormat.format("Отслеживающих :{0}", userDetails.getFollowers()));
                tvRepos.setText(MessageFormat.format("Репозитории и подробная информация:\n{0}", userDetails.getHtmlUrl()));
            } else {

            }
        }, user.getId());

        Picasso.with(getActivity())
                .load(user.getAvatarUrl())
                .error(R.drawable.error)
                .into(ivPhoto, new Callback() {
                    @Override
                    public void onSuccess() {
                        ivLoader.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        ivLoader.setVisibility(View.GONE);
                    }
                });
    }
}