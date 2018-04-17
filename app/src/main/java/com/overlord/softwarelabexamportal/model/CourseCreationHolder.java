package com.overlord.softwarelabexamportal.model;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.function.Consumer;

import static com.overlord.softwarelabexamportal.utils_immutable.Shorter.textWatcher;

public class CourseCreationHolder extends RecyclerView.ViewHolder {

    public View itemView;
    
    private TextView questionTextView;
    private TextView answerTextView;
    private TextView marksTextView;
    
    private QuestionAnswerMark qam;

    public CourseCreationHolder(View itemView, int questionTextViewID, int answerTextViewID, int marksTextViewID) {
        super(itemView);
        this.itemView = itemView;
        questionTextView = itemView.findViewById(questionTextViewID);
        answerTextView = itemView.findViewById(answerTextViewID);
        marksTextView = itemView.findViewById(marksTextViewID);
    }

    public void bind(QuestionAnswerMark qam) {
        this.qam = qam;

        if(qam != null) {
            questionTextView.setText("Q > " + qam.getQuestion());
            answerTextView.setText("A > " + qam.getAnswer());
            marksTextView.setText("Marks = " + qam.getMarks());
        }
    }
}
