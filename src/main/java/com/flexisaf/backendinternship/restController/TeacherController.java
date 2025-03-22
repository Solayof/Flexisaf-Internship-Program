package com.flexisaf.backendinternship.restController;

import com.flexisaf.backendinternship.entity.Staff;
import com.flexisaf.backendinternship.entity.Teacher;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.web.servlet.error.ErrorController;
import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private List<Staff> staff;
    private List<Teacher> teachers;

    @PostConstruct
    public void teacherList() {
        staff = new ArrayList<Staff>();

        staff.add(new Staff(1, "solomon", "moses", "solomonayofemi@gmail.com", "08088664424"));
        staff.add(new Staff(2,"Ojo", "Arisekola", "arisekola@top.com", "08088664424"));
        staff.add(new Staff(3,"Joy", "Pelumi", "pelumi@top.com", "08088664424"));
        staff.add(new Staff(4,"Tolu", "Mariam", "mariam@top.com", "08088664424"));
        staff.add(new Staff(5,"Loveth", "Arieren", "arieren@top.com", "08088664424"));

        Teacher teacher;

        teachers = new  ArrayList<Teacher>();

        teacher = new Teacher(4);
        teacher.setStaffId(staff.get(1).getId());
        teacher.setFullName(staff.get(1).getFirstName() + " " + staff.get(1).getLastName());
        teacher.setDiscipline("Mathematics");
        teachers.add(teacher);

        teacher = new Teacher(2);
        teacher.setStaffId(staff.get(0).getId());
        teacher.setFullName(staff.get(0).getFirstName() + " " + staff.get(0).getLastName());
        teacher.setDiscipline("Chemistry");
        teachers.add(teacher);

        teacher = new Teacher(1);
        teacher.setStaffId(staff.get(2).getId());
        teacher.setFullName(staff.get(2).getFirstName() + " " + staff.get(2).getLastName());
        teacher.setDiscipline("Physics");
        teachers.add(teacher);

        teacher = new Teacher(5);
        teacher.setStaffId(staff.get(3).getId());
        teacher.setFullName(staff.get(3).getFirstName() + " " + staff.get(3).getLastName());
        teacher.setDiscipline("Biology");
        teachers.add(teacher);

        teacher = new Teacher(3);
        teacher.setStaffId(staff.get(4).getId());
        teacher.setFullName(staff.get(4).getFirstName() + " " + staff.get(4).getLastName());
        teacher.setDiscipline("Software Engineering");
        teachers.add(teacher);

    }

    @GetMapping("/")
    public List<Teacher> teachers() {

        return teachers;
    }

    @GetMapping("/{id}")
    public Teacher getOne(@PathVariable int id) {
        if (teachers.size() <= id) {
            throw new ErrorResponseException(HttpStatusCode.valueOf(400));
        }
        return teachers.get(id);
    }

    @PostMapping("/")
    public Teacher createOne(@RequestBody Teacher newTeacher) {
        int id = teachers.size();
        newTeacher.setId(id);
        teachers.add(newTeacher);
        return teachers.get(id);
    }

    @PutMapping("/{id}")
    public Teacher updateOne(@RequestBody Teacher teacher, @PathVariable int id) {
        if (teachers.size() <= id) {
            throw new ErrorResponseException(HttpStatusCode.valueOf(400));
        }
        teacher.setId(id);
        teachers.add(teacher);
        return teachers.get(id);
    }
}
