package org.m2design.militaryconnect;

import static org.m2design.militaryconnect.util.Logging.onDebugLog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.m2design.militaryconnect.util.Logging;

import java.util.Arrays;

import butterknife.ButterKnife;

/**
 * Created by ajmyr on 5/15/2017.
 */

public class BaseActivity extends AppCompatActivity {

    // Choose an arbitrary request code value
    private static final int RC_SIGN_IN = 123;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get FirebaseAuth references
//        authNullCheck();

        userLoginCheck();
    }

    private void userLoginCheck() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            // User is already Signed in.
        } else {
            // Not signed in, start sign-in flow
            startActivityForResult(
                    // Get an instance of AuthUI based on the default app
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                    .build(),
                    RC_SIGN_IN);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        // Make sure Auth and AuthListener are not null
//        authNullCheck();
//        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        mAuth.removeAuthStateListener(mAuthStateListener);
    }

    /**
     * Get a {@link FirebaseAuth} reference.
     */
    private void initAuth() {
        if (mAuth == null)
            mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Initialize {@link FirebaseAuth.AuthStateListener}.
     */
    private void initAuthListener() {
        if (mAuthStateListener == null) {
            mAuthStateListener = firebaseAuth -> {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Logging.onDebugLogUser("signed in user:");
                } else {
                    // Go back to login screen
                    //startActivity(new Intent(BaseActivity.this, LoginActivity.class));
                    onDebugLog("signed out");
                }
            };
        }
    }

    /**
     * Verify our {@link FirebaseAuth} and {@link FirebaseAuth.AuthStateListener} references are
     * not null.
     */
    private void authNullCheck() {
        // Null check
        if (mAuth == null) {
         onDebugLog( FirebaseAuth.class.getSimpleName() + " reference is null, initializing.");
            initAuth();
        }

        if (mAuthStateListener == null) {
            onDebugLog( FirebaseAuth.AuthStateListener.class.getSimpleName() +
                    " reference is null, initializing.");
            initAuthListener();
        }
    }

    /**
     * Get a {@link FirebaseAuth} reference.
     * @return {@link FirebaseAuth}
     */
    public FirebaseAuth getAuth() {
        // Make sure FirebaseAuth isn't null.
        authNullCheck();
        return mAuth;
    }

    /**
     * Uses {@link ButterKnife.Setter} to switch the visibility of any View parameter based on
     * the boolean parameter.
     */
    static final ButterKnife.Setter<View, Boolean> VIEW_GONE = (view, value, i) -> view
            .setVisibility(value ? View.GONE : View.VISIBLE);

}
