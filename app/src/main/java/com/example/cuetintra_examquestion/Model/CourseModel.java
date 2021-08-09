package com.example.cuetintra_examquestion.Model;

public class CourseModel {
    String CourseName;
    String Priority;
    String ShortName;

    public CourseModel(String courseName, String priority, String shortname) {
        CourseName = courseName;
        Priority = priority;
        ShortName = shortname;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String priority) {
        Priority = priority;
    }

    public String getShortName() {
        return ShortName;
    }

    public void setShortName(String shortName) {
        ShortName = shortName;
    }
}
