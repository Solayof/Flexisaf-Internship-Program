package com.flexisaf.backendinternship.restController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.annotation.PostConstruct;
import com.flexisaf.backendinternship.entity.Staff;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StaffController {
    private List<Staff> staff;

    @PostConstruct
    public void staffList() {
        staff = new ArrayList<Staff>();

        staff.add(new Staff(1, "solomon", "moses", "solomonayofemi@gmail.com", "08088664424"));
        staff.add(new Staff(2,"Ojo", "Arisekola", "arisekola@top.com", "08088664424"));
        staff.add(new Staff(3,"Joy", "Pelumi", "pelumi@top.com", "08088664424"));
        staff.add(new Staff(4,"Tolu", "Mariam", "mariam@top.com", "08088664424"));
        staff.add(new Staff(5,"Loveth", "Arieren", "arieren@top.com", "08088664424"));

    }

    @GetMapping("/staff")
    public List<Staff> staff() {

        return staff;
    }

}
