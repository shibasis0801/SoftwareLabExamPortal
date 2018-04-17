package com.overlord.softwarelabexamportal.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.overlord.softwarelabexamportal.R;

public class CourseHolder extends RecyclerView.ViewHolder {
    private TextView nameView;

    public CourseHolder(View view) {
        super(view);
        nameView = view.findViewById(R.id.view_name);
    }

    public void bind(Course course) {
        if (course != null) {
            nameView.setText(course.getName());
        }
    }

}
