package com.github.warrocker.githubproject.fragment;

import android.app.Fragment;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.warrocker.githubproject.R;
import com.github.warrocker.githubproject.core.ActivityContextKeeper;
import com.github.warrocker.githubproject.core.AnimatorHelper;
import com.github.warrocker.githubproject.core.DetailsTransition;
import com.github.warrocker.githubproject.utils.FragmentUtils;

import net.frakbot.jumpingbeans.JumpingBeans;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;


@EFragment(R.layout.fragment_splash_screen)
public class SplashFragment extends Fragment {

    public static final int BEANS_DURATION = 1200;
    public static final int BEANS_DELAY = 3000;
    public static final int ANIM_DELAY = 1500;
    @ViewById
    TextView tvTitle;
    @ViewById
    ImageView ivLogo;

    JumpingBeans jumpingBuilder;

    @AfterViews
    public void initAfterView() {
        jumpingBuilder = JumpingBeans
                .with(tvTitle)
                .appendJumpingDots()
                .setIsWave(true)
                .setLoopDuration(BEANS_DURATION).build();

        new Handler().postDelayed(this::startLoginFragment, BEANS_DELAY);
        new Handler().postDelayed(() -> AnimatorHelper.likeAnimation(R.drawable.github_logo_for_anim, ivLogo), ANIM_DELAY);
    }

    private void startLoginFragment() {

        if (getActivity() == null) return;
        UserListFragment_ userListFragment = new UserListFragment_();
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fadeout);
        tvTitle.startAnimation(animation);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            DetailsTransition transition = new DetailsTransition();
            userListFragment.setSharedElementEnterTransition(transition);
            userListFragment.setSharedElementReturnTransition(transition);


            getActivity().getFragmentManager()
                    .beginTransaction()
                    .addSharedElement(ivLogo, getString(R.string.logo))
                    .replace(ActivityContextKeeper.getInstance().getContext().getContainer(), userListFragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            Animation animationMove = AnimationUtils.loadAnimation(getActivity(), R.anim.fadeout_kitkat);
            ivLogo.startAnimation(animationMove);

            FragmentUtils.replaceFragment(userListFragment, true);
        }
    }
}