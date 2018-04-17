package com.overlord.softwarelabexamportal.model;

public class QuestionAnswerMark {
    private String qamID;
    private String question;
    private String answer;
    private double marks;

    public QuestionAnswerMark() {}

    public QuestionAnswerMark(String qamID, String question, String answer, double marks) {
        this.qamID = qamID;
        this.question = question;
        this.answer = answer;
        this.marks = marks;
    }

    public String getQamID() {
        return qamID;
    }

    public void setQamID(String qamID) {
        this.qamID = qamID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public double getMarks() {
        return marks;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }
}
