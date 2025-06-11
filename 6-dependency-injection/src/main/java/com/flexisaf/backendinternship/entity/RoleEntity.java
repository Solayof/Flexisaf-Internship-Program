package com.flexisaf.backendinternship.entity;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flexisaf.backendinternship.constant.ERole;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class RoleEntity {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private ERole name;
}