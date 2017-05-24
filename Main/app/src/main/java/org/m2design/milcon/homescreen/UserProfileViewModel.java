package org.m2design.milcon.homescreen;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import org.m2design.milcon.model.User;

/**
 * Created by ajmyr on 5/22/2017.
 */

public class UserProfileViewModel extends ViewModel {

    private String userId;
    private LiveData<User> user;

    public void init(String email) {
        this.userId = email;
    }

    public LiveData<User> getUser() {
        return user;
    }
}
