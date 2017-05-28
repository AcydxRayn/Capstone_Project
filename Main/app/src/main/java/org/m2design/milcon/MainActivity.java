package org.m2design.milcon;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.m2design.milcon.databinding.ActivityMainBinding;
import org.m2design.milcon.main.HomeScreenFragment;
import org.m2design.milcon.main.UserProfileViewModel;

public class MainActivity extends BaseActivity {

    private TextView mTextMessage;
    private ActivityMainBinding mBinding;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                return true;
            case R.id.navigation_dashboard:

                return true;
            case R.id.navigation_notifications:
                return true;
        }
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
        }

        UserProfileViewModel viewModel = ViewModelProviders.of(this).get(UserProfileViewModel
                .class);

        viewModel.getMyUserProfile().observe(this, user -> {
            // update ui
            assert user != null;
            HomeScreenFragment fragment = new HomeScreenFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, fragment)
                    .commit();

        });


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
