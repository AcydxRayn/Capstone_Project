package org.m2design.milcon.model;

import android.databinding.ObservableArrayMap;

/**
 * Created by ajmyr on 5/22/2017.
 */

public class User {

    public ObservableArrayMap<String, Object> user = new ObservableArrayMap<>();
    private String email = "email";
    private String password = "password";
    private String displayName = "displayName";
    private String aboutMe = "aboutMe";
    private String branchOfService = "branchOfService";

    public User(String email, String password, String displayName, String aboutMe,
            String branchOfService) {

        user.put(this.email, email);
        user.put(this.password, password);
        user.put(this.displayName, displayName);
        user.put(this.aboutMe, aboutMe);
        user.put(this.branchOfService, branchOfService);
    }

}
