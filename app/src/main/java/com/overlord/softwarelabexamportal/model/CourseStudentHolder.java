package com.overlord.softwarelabexamportal.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.overlord.softwarelabexamportal.utils_immutable.Shorter;

import java.util.function.Consumer;

public class CourseStudentHolder extends RecyclerView.ViewHolder{
    public View itemView;

    private TextView questionTextView;
    private EditText answerInputView;

    private QuestionAnswerMark qam;

    public CourseStudentHolder(View itemView, int questionTextViewID, int answerEditTextID) {
        super(itemView);
        this.itemView = itemView;
        questionTextView = itemView.findViewById(questionTextViewID);
        answerInputView = itemView.findViewById(answerEditTextID);
    }

    public void bind(QuestionAnswerMark qam) {
        this.qam = qam;

        if(qam != null) {
            questionTextView.setText("Q > " + qam.getQuestion());
        }
    }
}
