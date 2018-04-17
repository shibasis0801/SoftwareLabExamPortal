package com.overlord.softwarelabexamportal.model;

import java.util.ArrayList;
import java.util.Map;

public class Teacher {
    private String name;
    private String ID;

    private Map<String, Boolean> courseIDs;
    private Map<String, Boolean> studentIDs;

    public Teacher() {}

    public Teacher(String name, String ID, Map<String, Boolean> courseIDs, Map<String, Boolean> studentIDs) {
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

    public Map<String, Boolean> getCourseIDs() {
        return courseIDs;
    }

    public void setCourseIDs(Map<String, Boolean> courseIDs) {
        this.courseIDs = courseIDs;
    }

    public Map<String, Boolean> getStudentIDs() {
        return studentIDs;
    }

    public void setStudentIDs(Map<String, Boolean> studentIDs) {
        this.studentIDs = studentIDs;
    }

    public static class Builder {
        private String name;
        private String id;
        private Map<String, Boolean> courseIDs;
        private Map<String, Boolean> studentIDs;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setID(String id) {
            this.id = id;
            return this;
        }

        public Builder setCourseIDs(Map<String, Boolean> courseIDs) {
            this.courseIDs = courseIDs;
            return this;
        }

        public Builder setStudentIDs(Map<String, Boolean> studentIDs) {
            this.studentIDs = studentIDs;
            return this;
        }

        public Teacher createTeacher() {
            return new Teacher(name, id, courseIDs, studentIDs);
        }
    }
}
