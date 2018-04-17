package com.overlord.softwarelabexamportal.base;

import android.app.Activity;
import android.graphics.Rect;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.overlord.softwarelabexamportal.R;
import com.overlord.softwarelabexamportal.teacher.NewCourseActivity;

public abstract class BaseActivity extends AppCompatActivity {
    public Button button(int id) {
        return findViewById(id);
    }

    public FloatingActionButton fab(int id) {
        return findViewById(id);
    }

    public void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void log(String text) {
        Log.i("BaseActivity", text);
    }
    public void error(String text) {
        Log.e("Base Activity", text);
    }

    public void enableRecyclerView(final boolean listNotEmpty, int placeholderID, int recyclerID) {
        if( listNotEmpty ) {
            findViewById(placeholderID).setVisibility(View.GONE);
            findViewById(recyclerID).setVisibility(View.VISIBLE);
        } else {

            findViewById(placeholderID).setVisibility(View.VISIBLE);
            findViewById(recyclerID).setVisibility(View.GONE);
        }
    }

    FirebaseRecyclerAdapter adapter;

    public void setupRecyclerView(int recyclerID, FirebaseRecyclerAdapter firebaseRecyclerAdapter, Activity activity){
        adapter = firebaseRecyclerAdapter;

        RecyclerView recyclerView = findViewById(recyclerID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
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
    protected void onStop() {
        super.onStop();

        if (adapter != null)
            adapter.stopListening();
    }
}
