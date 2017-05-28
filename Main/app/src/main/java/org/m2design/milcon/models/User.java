package org.m2design.milcon.models;

import android.databinding.ObservableArrayMap;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ajmyr on 5/22/2017.
 */

@IgnoreExtraProperties
public class User {

    public ObservableArrayMap<String, Object> user = new ObservableArrayMap<>();
    public String uid = "uid";
    public String email = "email";
    public String displayName = "displayName";
    public String aboutMe = "aboutMe";
    public String branchOfService = "branchOfService";

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String uid, String email, String displayName, String aboutMe,
            String branchOfService) {
        user.put(this.email, email);
        user.put(this.displayName, displayName);
        user.put(this.aboutMe, aboutMe);
        user.put(this.branchOfService, branchOfService);
    }

    public User(String uid, String email,
            String displayName) {
        this.user = user;
        this.uid = uid;
        this.email = email;
        this.displayName = displayName;
    }

    @Exclude
    public Map<String, Object> userUpdateToMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("email", email);
        result.put("displayName", displayName);
        result.put("aboutMe", aboutMe);
        result.put("branchOfService", branchOfService);

        return result;
    }

    @Exclude
    public Map<String, Object> newUserToMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("email", email);
        result.put("displayName", displayName);

        return result;
    }



}
