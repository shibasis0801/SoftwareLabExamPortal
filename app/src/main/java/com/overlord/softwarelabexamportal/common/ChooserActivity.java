package com.overlord.softwarelabexamportal.common;

import android.os.Bundle;

import com.overlord.softwarelabexamportal.R;
import com.overlord.softwarelabexamportal.utils_mutable.Singleton;
import com.overlord.softwarelabexamportal.base.BaseActivity;

public class ChooserActivity extends BaseActivity {
    private Singleton singleton = Singleton.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_chooser);


        button(R.id.button_teacher)
                .setOnClickListener(
                        view -> {
                            singleton.isTeacher = true;
                            startActivity(LoginActivity.createIntent(this));
                        }
                );

        button(R.id.button_student)
                .setOnClickListener(
                        view -> {
                            singleton.isTeacher = false;
                            startActivity(LoginActivity.createIntent(this));
                        }
                );
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
