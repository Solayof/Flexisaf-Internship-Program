package com.flexisaf.backendinternship.entity;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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

}
