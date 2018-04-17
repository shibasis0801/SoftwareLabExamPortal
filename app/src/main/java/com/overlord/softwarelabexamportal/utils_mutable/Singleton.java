package com.overlord.softwarelabexamportal.utils_mutable;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Singleton {
    private static final Singleton ourInstance = new Singleton();

    public static Singleton getInstance() {
        return ourInstance;
    }

    private Singleton() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseRoot = FirebaseDatabase.getInstance().getReference();
        coursesRef = firebaseRoot.child("courses");

    }

    public FirebaseAuth firebaseAuth;
    public DatabaseReference firebaseRoot;
    public DatabaseReference userRef;
    public DatabaseReference coursesRef;

    public FirebaseUser user;
    public String userID;
    public boolean isTeacher;
}
