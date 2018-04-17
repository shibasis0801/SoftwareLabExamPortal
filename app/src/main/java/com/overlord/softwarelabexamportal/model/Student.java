package com.overlord.softwarelabexamportal.model;

import java.util.ArrayList;
import java.util.Map;

public class Student {

    public static class Answers {
        Map<String, String> questionIDAnswer;

        public Answers() {}

        public Answers(Map<String, String> questionIDAnswer) {
            this.questionIDAnswer = questionIDAnswer;
        }

        public Map<String, String> getQuestionIDAnswer() {
            return questionIDAnswer;
        }

        public void setQuestionIDAnswer(Map<String, String> questionIDAnswer) {
            this.questionIDAnswer = questionIDAnswer;
        }
    }


    private String name;
    private String ID;

    private Map<String, Boolean> courseIDs;
    private Map<String, Answers> answersGiven;

    public Student() {}

    private Student(String name, String ID, Map<String, Boolean> courseIDs, Map<String, Answers> answersGiven) {
        this.name = name;
        this.ID = ID;
        this.courseIDs = courseIDs;
        this.answersGiven = answersGiven;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Map<String, Boolean> getCourseIDs() {
        return courseIDs;
    }

    public void setCourseIDs(Map<String, Boolean> courseIDs) {
        this.courseIDs = courseIDs;
    }

    public Map<String, Answers> getAnswersGiven() {
        return answersGiven;
    }

    public void setAnswersGiven(Map<String, Answers> answersGiven) {
        this.answersGiven = answersGiven;
    }
}
