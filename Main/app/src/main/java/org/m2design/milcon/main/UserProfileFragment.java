package org.m2design.milcon.main;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.m2design.milcon.R;

/**
 * Created by ajmyr on 5/23/2017.
 */

public class UserProfileFragment extends LifecycleFragment {

    private static final String UID_KEY = "uid";
    private UserProfileViewModel mProfileViewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String userId = getArguments().getString(UID_KEY);
        mProfileViewModel = ViewModelProviders.of(this).get(UserProfileViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_profile, container, false);
    }
}
