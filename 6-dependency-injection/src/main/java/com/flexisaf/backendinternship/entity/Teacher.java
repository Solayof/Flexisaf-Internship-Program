package com.flexisaf.backendinternship.entity;


import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "discipline")
    private String discipline;

    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public Teacher(UUID id, String discipline, UserEntity user) {
        this.id = id;
        this.discipline = discipline;
        this.user = user;
    }

    public Teacher(){}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
