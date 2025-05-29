package com.flexisaf.backendinternship.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Staff {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    public Staff() {}
    public Staff(int id, String firstName, String lastName, String email, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

}
