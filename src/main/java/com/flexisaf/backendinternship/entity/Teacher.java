package com.flexisaf.backendinternship.entity;


import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;

@Data
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


}
