package org.m2design.militaryconnect.util;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.m2design.militaryconnect.BuildConfig;

/**
 * A Logging helper class.
 * Created by ajmyr on 5/15/2017.
 */
public class Logging {

    private static final String ON_DEBUG_LOG = "onDebugLogUser:";

    /**
     * Utility method that will get the calling class and method and print a {@link Log} message.
     * @param msg   The message to print to the log.
     */
    public static void onDebugLog(@NonNull String msg) {
        // Early out
        if (!isDebug())
            return;
        Log.d(getClassSimpleName(), ON_DEBUG_LOG + getMethodName() + ":" + msg);
    }

    /**
     * Print {@link Log} message that contains a class name which isn't the calling class.
     * @param clazz Class that needs to be printed in the Log and is not the calling class.
     * @param msg Message to post in the Log.
     */
    public static void onDebugLog(@NonNull Class clazz, @NonNull String msg) {
        // Early out
        if (!isDebug())
            return;
        Log.d(getClassSimpleName(), ON_DEBUG_LOG + clazz.getSimpleName() + ":" + msg);
    }

    /**
     * Print a {@link Log} message that includes the Uid of the current user.
     * Additionally, it will print the current Uid {@link FirebaseUser} to the message.
     * @param msg   The message to print.
     */
    public static void onDebugLogUser(@NonNull String msg) {
        // Early out
        if (!isDebug())
            return;
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d(getClassSimpleName(), ON_DEBUG_LOG + getMethodName() + ":" + userId + msg);
    }

    /**
     * Get the SimpleClass name for the calling class.
     * @return String SimpleName of the class.
     */
    private static String getClassSimpleName() {
        return new Object(){}.getClass().getName();
    }

    /**
     * Get the name of the calling Method.
     * @return String name of the method.
     */
    private static String getMethodName() {
        return new Object(){}.getClass().getEnclosingMethod().getName();
    }

    /**
     * Check if we're running in Debug mode or not. No reason to print Debug logs if we aren't.
     * @return boolean
     */
    private static boolean isDebug() {
        return BuildConfig.DEBUG;
    }
}
