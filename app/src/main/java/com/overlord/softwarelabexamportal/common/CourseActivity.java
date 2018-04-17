package com.overlord.softwarelabexamportal.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseError;
import com.overlord.softwarelabexamportal.R;
import com.overlord.softwarelabexamportal.utils_mutable.Singleton;
import com.overlord.softwarelabexamportal.base.BaseActivity;
import com.overlord.softwarelabexamportal.model.Course;
import com.overlord.softwarelabexamportal.model.CourseHolder;
import com.overlord.softwarelabexamportal.student.GiveExamActivity;
import com.overlord.softwarelabexamportal.teacher.NewCourseActivity;

public class CourseActivity extends BaseActivity {

    private Singleton singleton = Singleton.getInstance();

    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<Course, CourseHolder> adapter;

    public void setupRecyclerView(int recyclerID){
        adapter = getAdapter();

        recyclerView = findViewById(recyclerID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, 0, 0, 2);
            }
        });

        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_course);

        setupRecyclerView(R.id.recycler_view);

        if (singleton.isTeacher)
            findViewById(R.id.fab_new_course)
                .setOnClickListener(view -> {
                    View dialogView = LayoutInflater.from(this).inflate(R.layout.edit_text_new_course, null);
                    EditText editText = dialogView.findViewById(R.id.new_course_edit_text);

                    new AlertDialog.Builder(this)
                            .setView(dialogView)
                            .setTitle("Enter New Course Name")
                            .setPositiveButton("Ok", (dialog, which) -> {
                                startActivity(NewCourseActivity.newIntent(this, editText.getText().toString(), "NA"));
                            })
                            .create()
                            .show();

                });
    }


    private FirebaseRecyclerOptions<Course> createOptions() {
        if (singleton.isTeacher)
            return new FirebaseRecyclerOptions.Builder<Course>()
                .setIndexedQuery(singleton.userRef.child("courseIDs"), singleton.firebaseRoot.child("courses"), Course.class)
                .build();
        else
            return new FirebaseRecyclerOptions.Builder<Course>()
                .setQuery(singleton.coursesRef, Course.class)
                .build();
    }


    private FirebaseRecyclerAdapter<Course, CourseHolder> getAdapter() {
        return new FirebaseRecyclerAdapter<Course, CourseHolder>(createOptions()) {
            @Override
            public void onDataChanged() {
                super.onDataChanged();
                enableRecyclerView(getItemCount() != 0, R.id.placeholder_text_view, R.id.recycler_view);
            }

            @Override
            public void onError(@NonNull DatabaseError error) {
                super.onError(error);
                Log.e("ERR", error.getMessage());
            }

            @Override
            protected void onBindViewHolder(@NonNull CourseHolder holder, int position, @NonNull Course model) {
                holder.bind(model);
                holder.itemView.setOnClickListener(v -> {
                    if (singleton.isTeacher)
                        startActivity(NewCourseActivity.newIntent(CourseActivity.this, model.getName(), model.getID()));
                    else
                        startActivity(GiveExamActivity.newIntent(CourseActivity.this, model.getID()));
                });

            }

            @NonNull
            @Override
            public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new CourseHolder(
                        LayoutInflater.from(parent.getContext())
                                    .inflate(R.layout.view_holder_course, parent, false)
                );
            }
        };
    }

    public static Intent createIntent(Activity activity) {
        Intent intent = new Intent(activity, CourseActivity.class);
        return intent;
    }
}
