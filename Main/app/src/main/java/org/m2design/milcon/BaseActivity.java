package org.m2design.milcon;

import android.app.ProgressDialog;
import android.arch.lifecycle.LifecycleActivity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BaseActivity extends LifecycleActivity {

    ProgressDialog mProgressDialog;
    FirebaseAuth mAuth;

    public static boolean isReleaseBuild() {
        return BuildConfig.DEBUG;
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        hideProgressDialog();
    }

    // [START_FIREBASE_TOOLS]

    private void authNullCheck() {
        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
        }
    }

    @NonNull
    public FirebaseAuth getAuth() {
        authNullCheck();
        return mAuth;
    }

    @Nullable
    public FirebaseUser getUser() {
        authNullCheck();
        if (mAuth.getCurrentUser() != null) {
            return mAuth.getCurrentUser();
        }
        return null;
    }

    // [END_FIREBASE_TOOLS]
}
