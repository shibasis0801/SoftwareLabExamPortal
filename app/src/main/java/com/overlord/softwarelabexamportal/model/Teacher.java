package com.overlord.softwarelabexamportal.model;

import java.util.ArrayList;

public class Teacher {
    private String name;
    private String ID;

    private ArrayList<String> courseIDs;
    private ArrayList<String> studentIDs;

    public Teacher() {}

    private Teacher(String name, String ID, ArrayList<String> courseIDs, ArrayList<String> studentIDs) {
        this.name = name;
        this.ID = ID;
        this.courseIDs = courseIDs;
        this.studentIDs = studentIDs;
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

    public ArrayList<String> getCourseIDs() {
        return courseIDs;
    }

    public void setCourseIDs(ArrayList<String> courseIDs) {
        this.courseIDs = courseIDs;
    }

    public ArrayList<String> getStudentIDs() {
        return studentIDs;
    }

    public void setStudentIDs(ArrayList<String> studentIDs) {
        this.studentIDs = studentIDs;
    }

    public static class Builder {
        private String name;
        private String id;
        private ArrayList<String> courseIDs;
        private ArrayList<String> studentIDs;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setID(String id) {
            this.id = id;
            return this;
        }

        public Builder setCourseIDs(ArrayList<String> courseIDs) {
            this.courseIDs = courseIDs;
            return this;
        }

        public Builder setStudentIDs(ArrayList<String> studentIDs) {
            this.studentIDs = studentIDs;
            return this;
        }

        public Teacher createTeacher() {
            return new Teacher(name, id, courseIDs, studentIDs);
        }
    }
}
