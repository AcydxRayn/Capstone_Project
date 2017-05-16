package org.m2design.militaryconnect;

import static org.m2design.militaryconnect.util.Logging.onDebugLog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ajmyr on 5/15/2017.
 */

public class LoginActivity extends BaseActivity  {

    // EditText fields for Email, Username, and Password
    @BindView(R.id.et_email) TextInputLayout mEtEmail;
    @BindView(R.id.et_password) TextInputLayout mEtPassword;
    @BindView(R.id.et_username) TextInputLayout mEtUsername;

    // List of buttons for button visibility changes.
    @BindViews({R.id.btn_register, R.id.btn_login}) List<Button> mLoginButtonList;
    @BindViews({R.id.btn_create, R.id.btn_cancel}) List<Button> mCreateUserButtonList;

    // ViewGroup for Animation change
    @BindView(R.id.layout_container) ViewGroup mLayoutContainer;

    // Boolean for toggling the Register button text and Username text input layout visibility
    boolean isUserRegistering;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        isUserRegistering = false;
    }

    @OnClick({R.id.btn_register, R.id.btn_login, R.id.btn_create, R.id.btn_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                onToggleLayoutVisibility();
                break;
            case R.id.btn_login:
                break;
            case R.id.btn_cancel:
                onToggleLayoutVisibility();
                break;
            case R.id.btn_create:
                onCreateUserWithEmail();
                break;
        }
    }

    private void onLoginButtonEnableCheck() {

    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(mEtEmail.getEditText().getText().toString())) {
            mEtEmail.setError("Required");
            result = false;
        } else {
            mEtEmail.setError(null);
        }

        if (TextUtils.isEmpty(mEtPassword.getEditText().getText().toString())) {
            mEtPassword.setError("Required");
            result = false;
        } else {
            mEtPassword.setError(null);
        }
        return result;
    }

    /**
     * Uses {@link ButterKnife.Setter} inside {@link BaseActivity} to change button and text
     * input layout visibility.
     */
    private void onToggleLayoutVisibility() {
        // Create TransitionGroup and toggle visibility boolean
        TransitionManager.beginDelayedTransition(mLayoutContainer);
        isUserRegistering = !isUserRegistering;

        // Use ButterKnife view list to toggle buttons for register, login, cancel, and create
        ButterKnife.apply(mLoginButtonList, VIEW_GONE, isUserRegistering);
        ButterKnife.apply(mCreateUserButtonList, VIEW_GONE, !isUserRegistering);
        ButterKnife.apply(mEtUsername, VIEW_GONE, !isUserRegistering);
    }

    private void onCreateUserWithEmail(@NonNull String email, @NonNull String password) {
        getAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {

                });
    }
}
