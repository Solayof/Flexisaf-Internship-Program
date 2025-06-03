package com.flexisaf.backendinternship.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "discipline")
    private String discipline;
    @Column(name = "fullName")
    private String fullName;

    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "staff_id")
    private Staff staff;

    public Teacher() {}

    public Teacher(int id) {
        this.id = id;
    }

}
