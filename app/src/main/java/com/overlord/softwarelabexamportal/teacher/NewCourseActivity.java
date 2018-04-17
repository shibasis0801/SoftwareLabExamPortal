package com.overlord.softwarelabexamportal.teacher;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.overlord.softwarelabexamportal.R;
import com.overlord.softwarelabexamportal.model.CourseCreationHolder;
import com.overlord.softwarelabexamportal.model.QuestionAnswerMark;
import com.overlord.softwarelabexamportal.utils_immutable.Shorter;
import com.overlord.softwarelabexamportal.utils_mutable.Singleton;
import com.overlord.softwarelabexamportal.base.BaseActivity;
import com.overlord.softwarelabexamportal.model.Course;

import java.util.function.Consumer;

public class NewCourseActivity extends BaseActivity {

    private Singleton singleton = Singleton.getInstance();

    private Course course;
    private DatabaseReference courseRef;
    private DatabaseReference courseQamRef;

    private void createAlertDialog(Consumer<QuestionAnswerMark> qamConsumer, QuestionAnswerMark qam){
        View dialogView = LayoutInflater.from(this).inflate(R.layout.view_holder_qam_input, null);

        EditText questionET = dialogView.findViewById(R.id.question_edit_text);
        if (qam != null && qam.getQuestion() != null)
            questionET.setText(qam.getQuestion());

        EditText answerET = dialogView.findViewById(R.id.answer_edit_text);
        if (qam != null && qam.getAnswer() != null)
            answerET.setText(qam.getAnswer());

        EditText marksET = dialogView.findViewById(R.id.marks_edit_text);
        if (qam != null)
            marksET.setText("" + qam.getMarks());

        new AlertDialog.Builder(this)
                .setView(dialogView)
                .setTitle("Enter New Question")
                .setPositiveButton("Ok", (dialog, which) -> {
                    qamConsumer.accept(new QuestionAnswerMark(
                            "NOT_SET",
                            questionET.getText().toString(),
                            answerET.getText().toString(),
                            Double.parseDouble(marksET.getText().toString())
                    ));
                })
                .create()
                .show();
    }

    private void createCourse(String courseName, String courseID) {
        course = new Course
                .Builder()
                .setID(courseID)
                .setTeacherID(singleton.userID)
                .setName(courseName)
                .createCourse();
    }

    private void saveToFirebaseFanOut(Course course, OnCompleteListener<Void> onCompleteListener) {
        courseRef.setValue(course)
                .addOnCompleteListener(Shorter.onCompleteListener(
                        task -> {
                            singleton.userRef
                                    .child("courseIDs")
                                    .child(course.getID())
                                    .setValue(true)
                                    .addOnCompleteListener(onCompleteListener);
                        }
                ));
    }

    private void storeQAM(QuestionAnswerMark qam) {
        String qamID = courseQamRef.push().getKey();
        qam.setQamID(qamID);

        courseQamRef.child(qamID).setValue(qam);
    }

    private void setupFab() {
        fab(R.id.fab_new_question)
                .setOnClickListener(v ->
                    createAlertDialog(this::storeQAM, null));
    }

    private void startCourseCreation(String courseName) {
        String courseID = singleton.coursesRef.push().getKey();
        createCourse(courseName, courseID);

        initRefs(courseID);

        saveToFirebaseFanOut(course, Shorter.onCompleteListener(
                task -> {
                    setupRecyclerView(R.id.recycler_view_new_course, getAdapter(), this);
                    setupFab();
                }
        ));
    }

    private void initRefs(String courseID) {
        courseRef = singleton.coursesRef.child(courseID);
        courseQamRef = courseRef.child("questionAnswerMarks");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_activity_new_course);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String courseID = getIntent().getStringExtra("courseID");
        String courseName = getIntent().getStringExtra("courseName");

        if ( courseID.equalsIgnoreCase("NA") )
            startCourseCreation(courseName);
        else {
            initRefs(courseID);
            setupFab();
            setupRecyclerView(R.id.recycler_view_new_course, getAdapter(), this);
        }
    }



    private FirebaseRecyclerOptions<QuestionAnswerMark> createOptions() {
        return new FirebaseRecyclerOptions.Builder<QuestionAnswerMark>()
                .setQuery(courseQamRef, QuestionAnswerMark.class)
                .build();
    }

    private FirebaseRecyclerAdapter<QuestionAnswerMark, CourseCreationHolder> getAdapter() {

        return new FirebaseRecyclerAdapter<QuestionAnswerMark, CourseCreationHolder>(createOptions()) {
            @Override
            public void onDataChanged() {
                super.onDataChanged();
                enableRecyclerView(getItemCount() != 0,
                        R.id.placeholder_text_view_new_course,
                        R.id.recycler_view_new_course);
            }

            @Override
            public void onError(@NonNull DatabaseError error) {
                super.onError(error);
                Log.e("ERR", error.getMessage());
            }

            @Override
            protected void onBindViewHolder(@NonNull CourseCreationHolder holder, int position, @NonNull QuestionAnswerMark model) {
                holder.bind(model);
                holder.itemView.setOnClickListener(v ->
                    createAlertDialog(qam -> {
                        qam.setQamID(model.getQamID());
                        courseQamRef
                                .child(qam.getQamID())
                                .setValue(qam);
                    }, model)
                );
            }

            @NonNull
            @Override
            public CourseCreationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new CourseCreationHolder(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.view_holder_qam, parent, false),
                        R.id.question_text_view,
                        R.id.answer_text_view,
                        R.id.marks_text_view);
            }
        };
    }



    public static Intent newIntent(Activity activity, String courseName, String courseID) {
        Intent intent = new Intent(activity, NewCourseActivity.class);
        intent.putExtra("courseName", courseName);
        intent.putExtra("courseID", courseID);
        return intent;
    }
}
