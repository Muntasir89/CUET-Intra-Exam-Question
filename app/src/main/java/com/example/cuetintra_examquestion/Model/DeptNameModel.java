package com.example.cuetintra_examquestion.Model;

public class DeptNameModel {
    private String name;
    private String priority;

    public DeptNameModel() {
        //Empty Constructor
    }
    public DeptNameModel(String name, String priority) {
        this.name = name;
        this.priority = priority;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority.toString();
    }
}
