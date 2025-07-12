package com.flexisaf.backendinternship.entity;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "courses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @EqualsAndHashCode.Exclude
    private UserEntity owner;

    public Course update(String content) {
        this.content = content;
        return this;
    }
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "course_to_subcribers",
        joinColumns = @JoinColumn(name="course_id"),
        inverseJoinColumns = @JoinColumn(name="user_id"))
    @EqualsAndHashCode.Exclude
    private Set<UserEntity> subscribers = new HashSet<>();

}
