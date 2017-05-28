package org.m2design.milcon.main;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.m2design.milcon.models.User;

/**
 * Created by ajmyr on 5/22/2017.
 */

public class UserProfileViewModel extends ViewModel {

    // Only used for remote users. IE: Other users on the internet
    private MutableLiveData<User> remoteUser;

    private DatabaseReference mRef;
    private FirebaseDatabase mDatabase;

    // Only used for the local user on the device
    private MutableLiveData<FirebaseUser> currentUser;


    public MutableLiveData<FirebaseUser> getMyUserProfile() {
        if (currentUser == null) {
            currentUser = new MutableLiveData<>();
            loadLocalUser();
        }
        return currentUser;
    }

    private void loadLocalUser() {
        // do async
        Handler myHandler = new Handler();

        //
        myHandler.postDelayed(() -> {
            currentUser.setValue(FirebaseAuth.getInstance().getCurrentUser());
        }, 5000);
    }

    public MutableLiveData<User> getRemoteUserProfile(String uId) {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
        }

        if (mRef == null) {
            mRef = mDatabase.getReference("users" + uId + "profile");
        }

        if (remoteUser == null) {

        }
        return remoteUser;
    }

}
