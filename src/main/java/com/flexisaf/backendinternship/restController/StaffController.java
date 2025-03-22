package com.flexisaf.backendinternship.restController;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.PostConstruct;
import com.flexisaf.backendinternship.entity.Staff;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("staff")
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

    @GetMapping("")
    public List<Staff> staff() {

        return staff;
    }

    @GetMapping("/{id}")
    public Staff getOne(@PathVariable int id) {
        if (staff.size() + 1 <= id) {
            throw new ErrorResponseException(HttpStatusCode.valueOf(400));
        }
        return staff.get(id - 1);
    }

    @PostMapping("/")
    public Staff createOne(@RequestBody Staff newStaff) {
        int id = staff.size();
        newStaff.setId(id);
        staff.add(newStaff);
        return staff.get(id);
    }

    @PutMapping("/{id}")
    public Staff updateOne(@RequestBody Staff newStaff, @PathVariable int id) {
        if (staff.size() + 1 <= id) {
            throw new ErrorResponseException(HttpStatusCode.valueOf(400));
        }
        newStaff.setId(id);
        staff.set(id, newStaff);
        return staff.get(id - 1);
    }

}
