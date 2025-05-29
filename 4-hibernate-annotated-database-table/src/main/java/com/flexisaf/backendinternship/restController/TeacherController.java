package com.flexisaf.backendinternship.restController;

import com.flexisaf.backendinternship.entity.Staff;
import com.flexisaf.backendinternship.entity.Teacher;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private List<Staff> staff;
    private List<Teacher> teachers;
    private final TeacherModelAssembler assembler;

    public TeacherController(List<Staff> staff, TeacherModelAssembler assembler) {
        this.staff = staff;
        this.assembler = assembler;
    }

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

        teacher = new Teacher(1);
        teacher.setStaffId(staff.get(1).getId());
        teacher.setFullName(staff.get(1).getFirstName() + " " + staff.get(1).getLastName());
        teacher.setDiscipline("Mathematics");
        teachers.add(teacher);

        teacher = new Teacher(2);
        teacher.setStaffId(staff.get(0).getId());
        teacher.setFullName(staff.get(0).getFirstName() + " " + staff.get(0).getLastName());
        teacher.setDiscipline("Chemistry");
        teachers.add(teacher);

        teacher = new Teacher(3);
        teacher.setStaffId(staff.get(2).getId());
        teacher.setFullName(staff.get(2).getFirstName() + " " + staff.get(2).getLastName());
        teacher.setDiscipline("Physics");
        teachers.add(teacher);

        teacher = new Teacher(4);
        teacher.setStaffId(staff.get(3).getId());
        teacher.setFullName(staff.get(3).getFirstName() + " " + staff.get(3).getLastName());
        teacher.setDiscipline("Biology");
        teachers.add(teacher);

        teacher = new Teacher(5);
        teacher.setStaffId(staff.get(4).getId());
        teacher.setFullName(staff.get(4).getFirstName() + " " + staff.get(4).getLastName());
        teacher.setDiscipline("Software Engineering");
        teachers.add(teacher);

    }


    @GetMapping("")
    @Operation(summary = "Get list of teachers in the server")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "successfully get teachers list"
            )
    })
    public CollectionModel<EntityModel<Teacher>> teachers() {
        List<EntityModel<Teacher>> teacherlist = teachers.stream()
                .map(assembler::toModel).toList();

        return CollectionModel.of(teacherlist,
                linkTo(methodOn(TeacherController.class).teachers()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Teacher> getOne(@PathVariable int id) {
        if (id == 0 || teachers.size()  < id) {
            throw new ErrorResponseException(HttpStatusCode.valueOf(404));
        }
        return assembler.toModel(teachers.get(id - 1));
    }

    @PostMapping("")
    public EntityModel<Teacher> createOne(@RequestBody Teacher newTeacher) {
        int id = teachers.size();
        newTeacher.setId(id);
        teachers.add(newTeacher);
        return assembler.toModel(teachers.get(id));
    }

    @PutMapping("/{id}")
    public EntityModel<Teacher> updateOne(@RequestBody Teacher teacher, @PathVariable int id) {
        if (id == 0 || teachers.size()  < id) {
            throw new ErrorResponseException(HttpStatusCode.valueOf(404));
        }
        teacher.setId(id);
        teachers.set(id - 1, teacher);
        return assembler.toModel(teachers.get(id - 1));
    }
}
