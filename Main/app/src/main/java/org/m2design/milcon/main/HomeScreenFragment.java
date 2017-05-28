package org.m2design.milcon.main;

import android.arch.lifecycle.LifecycleFragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.m2design.milcon.R;
import org.m2design.milcon.databinding.FragmentHomeScreenBinding;
import org.m2design.milcon.models.User;

/**
 * Created by ajmyr on 5/23/2017.
 */

public class HomeScreenFragment extends LifecycleFragment {

    FragmentHomeScreenBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        User user = new User();
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_screen, container,
                false);
        mBinding.setUser(user);
        View v = mBinding.getRoot();

        return v;
    }
}
