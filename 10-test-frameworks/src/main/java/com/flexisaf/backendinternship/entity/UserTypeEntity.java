package com.flexisaf.backendinternship.entity;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flexisaf.backendinternship.constant.UserType;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usertypes")
public class UserTypeEntity {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private UserType name;
}