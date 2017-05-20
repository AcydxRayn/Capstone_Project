package org.m2design.militaryconnect.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import org.m2design.militaryconnect.BR;

/**
 * Created by ajmyr on 5/16/2017.
 */

public class User extends BaseObservable {

    private String uId;
    private String userName;
    private String email;
    private String password;

    public User(String uId, String userName, String email, String password) {
        this.uId = uId;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    @Bindable
    public String getuId() { return uId; }

    public void setuId(String uId) {
        this.uId = uId;
        notifyPropertyChanged(BR.uId);
    }

    @Bindable
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        notifyPropertyChanged(BR.userName);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }
}
