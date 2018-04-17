package com.overlord.softwarelabexamportal.model;

import java.util.ArrayList;
import java.util.Map;

public class Course {
    private String ID;
    private String name;
    private Map<String, QuestionAnswerMark> questionAnswerMarks;

    private String teacherID;
    private Map<String, Boolean> studentIDs;

    public Course() {}

    private Course(String ID, String name, String teacherID, Map<String, Boolean> studentIDs, Map<String, QuestionAnswerMark> questionAnswerMarks) {
        this.ID = ID;
        this.name = name;
        this.teacherID = teacherID;
        this.studentIDs = studentIDs;
        this.questionAnswerMarks = questionAnswerMarks;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public Map<String, Boolean> getStudentIDs() {
        return studentIDs;
    }

    public void setStudentIDs(Map<String, Boolean> studentIDs) {
        this.studentIDs = studentIDs;
    }

    public Map<String, QuestionAnswerMark> getQuestionAnswerMarks() {
        return questionAnswerMarks;
    }

    public void setQuestionAnswerMarks(Map<String, QuestionAnswerMark> questionAnswerMarks) {
        this.questionAnswerMarks = questionAnswerMarks;
    }

    public static class Builder {
        private String id;
        private String name;
        private String teacherID;
        private Map<String, Boolean> studentIDs;
        private Map<String, QuestionAnswerMark> questionAnswerMarks;

        public Builder setID(String id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setTeacherID(String teacherID) {
            this.teacherID = teacherID;
            return this;
        }

        public Builder setStudentIDs(Map<String, Boolean> studentIDs) {
            this.studentIDs = studentIDs;
            return this;
        }

        public Builder setQuestionAnswerMarks(Map<String, QuestionAnswerMark> questionAnswerMarks) {
            this.questionAnswerMarks = questionAnswerMarks;
            return this;
        }

        public Course createCourse() {
            return new Course(id, name, teacherID, studentIDs, questionAnswerMarks);
        }
    }
}
