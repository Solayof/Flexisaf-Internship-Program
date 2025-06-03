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

        Staff st = new Staff();
        st.setLastName("moses");
        st.setEmail("solomonayofemi@gmail.com");
        st.setPhone("08088664424");
        st.setFirstName("Solomon");
        st.setId(1);

        staff.add(st);

        Staff st1 = new Staff();
        st1.setLastName("Arisekola");
        st1.setEmail("arisekola@top.com");
        st1.setPhone("08088664424");
        st1.setFirstName("Ojo");
        st1.setId(2);

        staff.add(st1);

        Staff st2 = new Staff();
        st2.setLastName("Pelumi");
        st2.setEmail("pelumi@top.com");
        st2.setPhone("08088664424");
        st2.setFirstName("pelumi@top.com");
        st2.setId(3);

        staff.add(st2);

        Staff st3 = new Staff();
        st3.setLastName("Mariam");
        st3.setEmail("mariam@top.com");
        st3.setPhone("08088664424");
        st3.setFirstName("Tolu");
        st3.setId(3);

        staff.add(st3);

        Staff st4 = new Staff();
        st4.setLastName("Arieren");
        st4.setEmail("arieren@top.com");
        st4.setPhone("08088664424");
        st4.setFirstName("Loveth");
        st4.setId(4);

        staff.add(st4);

        Teacher teacher;

        teachers = new  ArrayList<Teacher>();

        teacher = new Teacher(1);
        teacher.setStaff(staff.get(1));
        teacher.setFullName(staff.get(1).getFirstName() + " " + staff.get(1).getLastName());
        teacher.setDiscipline("Mathematics");
        teachers.add(teacher);

        teacher = new Teacher(2);
        teacher.setStaff(staff.get(0));
        teacher.setFullName(staff.get(0).getFirstName() + " " + staff.get(0).getLastName());
        teacher.setDiscipline("Chemistry");
        teachers.add(teacher);

        teacher = new Teacher(3);
        teacher.setStaff(staff.get(2));
        teacher.setFullName(staff.get(2).getFirstName() + " " + staff.get(2).getLastName());
        teacher.setDiscipline("Physics");
        teachers.add(teacher);

        teacher = new Teacher(4);
        teacher.setStaff(staff.get(3));
        teacher.setFullName(staff.get(3).getFirstName() + " " + staff.get(3).getLastName());
        teacher.setDiscipline("Biology");
        teachers.add(teacher);

        teacher = new Teacher(5);
        teacher.setStaff(staff.get(4));
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
