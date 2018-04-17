package com.overlord.softwarelabexamportal.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.overlord.softwarelabexamportal.R;
import com.overlord.softwarelabexamportal.utils_mutable.Singleton;
import com.overlord.softwarelabexamportal.base.BaseActivity;
import com.overlord.softwarelabexamportal.model.Student;
import com.overlord.softwarelabexamportal.model.Teacher;

import java.util.Arrays;

public class LoginActivity extends BaseActivity {

    private Singleton singleton = Singleton.getInstance();
    private static final int RC_SIGN_IN = 123;

    public static Intent createIntent(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_login);

        FirebaseUser currentUser = singleton.firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            startApp(currentUser, getUserRef(currentUser));
        } else {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setIsSmartLockEnabled(false)
                            .setAvailableProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.GoogleBuilder().build()
                            ))
                            .build()
                    ,RC_SIGN_IN
            );
        }
    }

    public void newUserSignUp(FirebaseUser user) {

        DatabaseReference userRef = getUserRef(user);

        if (singleton.isTeacher) {

            Teacher teacher =
                    new Teacher.Builder()
                    .setName(user.getDisplayName())
                    .setID(user.getUid())
                    .createTeacher();

            userRef.setValue(teacher);

        }
        else {

            Student student =
                    new Student.Builder()
                            .setName(user.getDisplayName())
                            .setID(user.getUid())
                            .createStudent();

            userRef.setValue(student);
        }

        startApp(user, userRef);
    }

    private DatabaseReference getUserRef(FirebaseUser user) {
        return singleton.firebaseRoot.child(singleton.isTeacher?"teachers":"students").child(user.getUid());
    }


    public void startApp(FirebaseUser user, DatabaseReference userRef) {
        singleton.user = user;
        singleton.userRef = userRef;
        singleton.userID = user.getUid();

        startActivity(CourseActivity.createIntent(this));
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {

                newUserSignUp(singleton.firebaseAuth.getCurrentUser());
            }
            else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    return;
                }

                Log.e("TAG", "Sign-in error: ", response.getError());
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
