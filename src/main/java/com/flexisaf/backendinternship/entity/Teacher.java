package com.flexisaf.backendinternship.entity;



public class Teacher {
    private int id;
    private String discipline;
    private String fullName;
    private int staffId;
    public Teacher() {}

    public Teacher(int id) {
        this.id = id;
    }

    public Teacher(int id, String discipline, String fullName, int staffId) {
        this.id = id;
        this.discipline = discipline;
        this.fullName = fullName;
        this.staffId = staffId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
