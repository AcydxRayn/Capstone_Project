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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.m2design.milcon.databinding.ActivityLoginBinding;
import org.m2design.milcon.models.User;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";

    ActivityLoginBinding mLoginBinding;

    // InputTextLayout views.
    private TextInputLayout mInputLayoutDisplayName;
    private TextInputLayout mInputLayoutPasswordConfirm;
    private TextInputLayout mInputLayoutEmail;
    private TextInputLayout mInputLayoutPassword;

    // Buttons
    private Button mButtonCreateAccount;
    private Button mButtonRegister;
    private Button mButtonLogin;
    private Button mButtonCancel;

    private DatabaseReference mDatabase;

    // RootView for this activity
    private ConstraintLayout mRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set DataBinding layout and the loginActivity variable to this
        mLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
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
    @SuppressWarnings("ConstantConditions")
    public void onButtonClicked(View view) {

        String email = mInputLayoutEmail.getEditText().getText().toString();
        String password = mInputLayoutPassword.getEditText().getText().toString();
        // Display name is not assigned until we receive a register click to avoid any error.
        String displayName;

        if (!mInputLayoutDisplayName.getEditText().getText().toString().isEmpty()) {

        }

        // Id used in the switch statement.
        int id = view.getId();
        switch (id) {
            case R.id.buttonLogin:
                signInWithEmail(email, password);
                break;
            case R.id.buttonCreateAccount:
                // Toggle the creation form views
                toggleViewVisibility();
                resetPasswordInputFields();
                mInputLayoutDisplayName.requestFocus();
                break;
            case R.id.buttonRegister:
                displayName = mInputLayoutDisplayName.getEditText().getText().toString();
                registerNewUser(email, password, displayName);
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
    private void signInWithEmail(String email, String password) {

        showProgressDialog();

        getAuth().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in successful
                        onAuthSuccessStartMainActivity();
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
    private void registerNewUser(String email, String password, String displayName) {
        if (validatePasswordFieldsMatch()) {
            showProgressDialog();
            getAuth().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            // Add user to Firebase RealTimeDatabase
                            writeNewUserToDatabase(user.getUid(), email, displayName);

                            // Add user display name to FirebaseAuth
                            updateUserProfileWithDisplayName(displayName);
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
    private void updateUserProfileWithDisplayName(String displayName) {
        // Creation success, add users display name to his new account.
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            UserProfileChangeRequest profileUpdates = new
                    UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            // Start MainActivity.
                            onAuthSuccessStartMainActivity();
                        }
                    });
        }
    }

    @SuppressWarnings("ConstantConditions")
    private void writeNewUserToDatabase(String uid, String email, String displayName) {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        String key = mDatabase.child("users").push().getKey();

        User user = new User(uid, email, displayName);
        Map<String, Object> userValues = user.newUserToMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/users/" + key, userValues);
        mDatabase.updateChildren(childUpdates);
    }

    private void onAuthSuccessStartMainActivity() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        hideProgressDialog();
        finish();
    }

    @SuppressWarnings("ConstantConditions")
    private void resetPasswordInputFields() {
        mInputLayoutPassword.setError(null);
        mInputLayoutPasswordConfirm.setError(null);
        mInputLayoutPassword.getEditText().setText(getString(R.string.empty_string));
        mInputLayoutPasswordConfirm.getEditText().setText(getString(R.string.empty_string));
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
