package org.m2design.milcon;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.m2design.milcon.databinding.ActivityLoginBinding;
import org.m2design.milcon.homescreen.MainActivity;


public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";

    ActivityLoginBinding mLoginBinding;

    // InputTextLayout views. Used to Hide/Show based on registering or not.
    private TextInputLayout mInputLayoutDisplayName;
    private TextInputLayout mInputLayoutPasswordConfirm;
    private TextInputLayout mInputLayoutEmail;
    private TextInputLayout mInputLayoutPassword;

    // Buttons
    private Button mButtonCreateAccount;
    private Button mButtonRegister;
    private Button mButtonLogin;
    private Button mButtonCancel;

    // RootView for this activity
    private ConstraintLayout mRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // No need to go through the login workflow if we're already logged in.
        if (getUser() != null) {
            // User is logged in, start MainActivity
            startActivity(new Intent(this, MainActivity.class));
        }

        mLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        // Set DataBinding loginActivity variable
        mLoginBinding.setLoginActivity(this);
        bindViews();
    }

    /**
     * Bind all views to their applicable fields.
     */
    private void bindViews() {
        mRootView = mLoginBinding.rootView;

        mInputLayoutDisplayName = mLoginBinding.textInputLayoutDisplayName;
        mInputLayoutPasswordConfirm = mLoginBinding.textInputLayoutPasswordConfirm;
        mInputLayoutPassword = mLoginBinding.textInputLayoutPassword;
        mInputLayoutEmail = mLoginBinding.textInputLayoutEmail;

        mButtonCreateAccount = mLoginBinding.buttonCreateAccount;
        mButtonCancel = mLoginBinding.buttonCancel;
        mButtonLogin = mLoginBinding.buttonLogin;
        mButtonRegister = mLoginBinding.buttonRegister;
    }

    /**
     * Handle button clicks. Method is assigned in the Layout file via DataBinding.
     *
     * @param view The view that was clicked. Use the view's id to choose the switch case.
     */
    public void onButtonClicked(View view) {
        // Id used in the switch statement.
        int id = view.getId();
        switch (id) {
            case R.id.buttonLogin:
                signInWithEmail();
                break;
            case R.id.buttonCreateAccount:
                // Toggle the creation form views
                toggleViewVisibility();
                resetPasswordInputFields();
                mInputLayoutDisplayName.requestFocus();
                break;
            case R.id.buttonRegister:
                registerNewUser();
                break;
            case R.id.buttonCancel:
                // Toggle the creation form views
                toggleViewVisibility();
                resetPasswordInputFields();
                mInputLayoutEmail.requestFocus();
                break;
        }
    }

    @SuppressWarnings("ConstantConditions")
    private void signInWithEmail() {
        // Get our strings from the
        String email = mInputLayoutEmail.getEditText().getText().toString();
        String password = mInputLayoutPassword.getEditText().getText().toString();

        showProgressDialog();

        getAuth().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in successful
                        Log.d(TAG, "signInWithEmail: User sign-in success");
                        startActivity(new Intent(this, MainActivity.class));
                        hideProgressDialog();
                    } else {
                        // If sign in fails, display a message ot the user.
                        Log.w(TAG, "signInWithEmail: Authentication Failure", task.getException());

                        hideProgressDialog();

                        // TODO: 5/24/2017 Replace this with signin snackbar using string resource
                        Toast.makeText(LoginActivity.this, "Authentication Failure",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    @SuppressWarnings("ConstantConditions")
    private void resetPasswordInputFields() {
        mInputLayoutPassword.setError(null);
        mInputLayoutPasswordConfirm.setError(null);
        mInputLayoutPassword.getEditText().setText(getString(R.string.empty_string));
        mInputLayoutPasswordConfirm.getEditText().setText(getString(R.string.empty_string));
    }

    @SuppressWarnings("ConstantConditions")
    private void registerNewUser() {
        if (validatePasswordFieldsMatch()) {
            showProgressDialog();
            getAuth().createUserWithEmailAndPassword(mInputLayoutEmail.getEditText().getText()
                    .toString(), mInputLayoutPassword.getEditText().getText().toString())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {

                            // User creation success, Start MainActivity.
                            startActivity(new Intent(this, MainActivity.class));
                        } else {
                            // User creation failed
                            hideProgressDialog();
                            // TODO: 5/24/2017 Replace Toast with SnackBar and use a String
                            // resource for message
                            Toast.makeText(this, "Account creation failed, please try again.",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                    });
        } else {
            mInputLayoutPassword.setError(getString(R.string.error_password_match));
            mInputLayoutPasswordConfirm.setError(getString(R.string.error_password_match));
        }
    }

    @SuppressWarnings("ConstantConditions")
    private boolean validatePasswordFieldsMatch() {
        // Get our password Strings
        String password = mInputLayoutPassword.getEditText().getText().toString();
        String passwordConfirmation =
                mInputLayoutPasswordConfirm.getEditText().getText().toString();

        // Verify the password Strings match
        return password.equals(passwordConfirmation);
    }

    /**
     * Toggle the registration views visibility.
     */
    private void toggleViewVisibility() {
        // Toggle Buttons
        mButtonLogin.setVisibility(
                mButtonLogin.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        mButtonCreateAccount.setVisibility(
                mButtonCreateAccount.getVisibility() == View.VISIBLE ? View.GONE : View
                        .VISIBLE);
        mButtonRegister.setVisibility(
                mButtonRegister.getVisibility() == View.VISIBLE ? View.GONE : View
                        .VISIBLE);
        mButtonCancel.setVisibility(mButtonCancel.getVisibility() == View.VISIBLE ? View.GONE : View
                .VISIBLE);

        // Toggle input fields
        mInputLayoutPasswordConfirm.setVisibility(
                mInputLayoutPasswordConfirm.getVisibility() == View.VISIBLE ? View
                        .GONE : View.VISIBLE);
        mInputLayoutDisplayName.setVisibility(
                mInputLayoutDisplayName.getVisibility() == View.VISIBLE ? View.GONE :
                        View.VISIBLE);
    }

}
