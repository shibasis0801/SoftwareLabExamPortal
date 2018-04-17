package com.overlord.softwarelabexamportal.student;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.overlord.softwarelabexamportal.R;
import com.overlord.softwarelabexamportal.base.BaseActivity;
import com.overlord.softwarelabexamportal.model.Course;
import com.overlord.softwarelabexamportal.model.CourseStudentHolder;
import com.overlord.softwarelabexamportal.model.QuestionAnswerMark;
import com.overlord.softwarelabexamportal.model.Student;
import com.overlord.softwarelabexamportal.utils_immutable.Shorter;
import com.overlord.softwarelabexamportal.utils_mutable.Singleton;

import java.util.Map;

public class GiveExamActivity extends BaseActivity {

    private Singleton singleton = Singleton.getInstance();

    private DatabaseReference courseRef;
    private DatabaseReference courseQamRef;
    private DatabaseReference answersRef;
    private DatabaseReference studentCoursesRef;

    private double marks = 0;

    private void calculateScore() {
        courseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Course course = dataSnapshot.getValue(Course.class);

                answersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Student.Answers answers = dataSnapshot.getValue(Student.Answers.class);

                        Map<String, QuestionAnswerMark> qams = course.getQuestionAnswerMarks();

                        answers.getQuestionIDAnswer()
                                .forEach((questionID, answer) -> {

                                    QuestionAnswerMark selectedQam = qams.get(questionID);
                                    if (selectedQam.getAnswer().equalsIgnoreCase(answer))
                                        marks += selectedQam.getMarks();

                                    Log.i("", "Storing Actual Answers");
                                    answersRef.child(questionID).setValue(selectedQam.getAnswer());
                        });
                        toast("" + marks);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setupFab() {
        fab(R.id.fab_submit_answers)
                .setOnClickListener(v -> calculateScore());
    }

    private void initRefs(String courseID) {
        courseRef = singleton.coursesRef.child(courseID);
        answersRef = singleton.userRef.child("answersGiven").child(courseID);
        courseQamRef = courseRef.child("questionAnswerMarks");
        studentCoursesRef = singleton.userRef.child("courseIDs");
    }

    private void enrollCourse(String courseID) {
        studentCoursesRef.child(courseID).setValue(true)
            .addOnCompleteListener(Shorter.onCompleteListener(
                    task -> {
                        courseRef.child("studentIDs").child(singleton.userID)
                                .setValue(true).addOnCompleteListener(task1 -> setupViews());
                    }
            ));
    }

    private void setupViews() {
        setupRecyclerView(R.id.recycler_view_enroll_course, getAdapter(), this);
        setupFab();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_activity_give_exam);

        String courseID = getIntent().getStringExtra("courseID");

        initRefs(courseID);

        singleton.userRef.child("courseIDs").child(courseID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot == null ||  ! dataSnapshot.exists())
                            enrollCourse(courseID);
                        else
                            setupViews();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
    }



    private FirebaseRecyclerOptions<QuestionAnswerMark> createOptions() {
        return new FirebaseRecyclerOptions.Builder<QuestionAnswerMark>()
                .setQuery(courseQamRef, QuestionAnswerMark.class)
                .build();
    }

    private FirebaseRecyclerAdapter<QuestionAnswerMark, CourseStudentHolder> getAdapter() {

        return new FirebaseRecyclerAdapter<QuestionAnswerMark, CourseStudentHolder>(createOptions()) {
            @Override
            public void onDataChanged() {
                super.onDataChanged();
                enableRecyclerView(getItemCount() != 0,
                        R.id.placeholder_text_view_enroll_course,
                        R.id.recycler_view_enroll_course);
            }

            @Override
            public void onError(@NonNull DatabaseError error) {
                super.onError(error);
                Log.e("ERR", error.getMessage());
            }

            @Override
            protected void onBindViewHolder(@NonNull CourseStudentHolder holder, int position, @NonNull QuestionAnswerMark model) {
                toast(model.getQuestion());
                holder.bind(model, charSequence ->
                    answersRef.child(model.getQamID()).setValue(charSequence));
            }

            @NonNull
            @Override
            public CourseStudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new CourseStudentHolder(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.view_holder_qam_student, parent, false),
                        R.id.student_question_text_view,
                        R.id.student_answer_edit_text);
            }
        };
    }



    public static Intent newIntent(Activity activity, String courseID) {
        Intent intent = new Intent(activity, GiveExamActivity.class);
        intent.putExtra("courseID", courseID);
        return intent;
    }
}
