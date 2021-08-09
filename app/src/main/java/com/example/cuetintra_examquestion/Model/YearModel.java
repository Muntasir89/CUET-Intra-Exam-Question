package com.example.cuetintra_examquestion.Model;

public class YearModel {
    String path;
    String year;
    public YearModel(String path, String y) {
        this.path = path;
        year = y;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
