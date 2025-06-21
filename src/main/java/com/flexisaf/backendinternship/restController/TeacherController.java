package com.flexisaf.backendinternship.restController;


import com.flexisaf.backendinternship.entity.Teacher;
import com.flexisaf.backendinternship.exception.TeacherNotFoundException;
import com.flexisaf.backendinternship.repository.TeacherRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherRepository teacherRepository;
    private final TeacherModelAssembler assembler;

    public TeacherController(TeacherRepository teacherRepository, TeacherModelAssembler assembler) {
        this.teacherRepository = teacherRepository;
        this.assembler = assembler;
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
        List<EntityModel<Teacher>> teacherlist = teacherRepository.findAll().stream()
                .map(assembler::toModel).toList();

        return CollectionModel.of(teacherlist,
                linkTo(methodOn(TeacherController.class).teachers()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Teacher> getOne(@PathVariable UUID id) {
        Teacher teacher = teacherRepository.findById(id)
        .orElseThrow(() -> new TeacherNotFoundException(id));
        return assembler.toModel(teacher);
    }

    @PostMapping("")
    public EntityModel<Teacher> createOne(@RequestBody Teacher newTeacher) {
        return assembler.toModel(teacherRepository.save(newTeacher));
    }

    @PutMapping("/{id}")
    public EntityModel<Teacher> updateOne(@RequestBody Teacher newteacher, @PathVariable UUID id) {
        return assembler.toModel(
            teacherRepository.findById(id)
            .map(teacher -> {
                teacher.setDiscipline(newteacher.getDiscipline());

                return teacherRepository.save(teacher);
            })
            .orElseThrow(() -> new TeacherNotFoundException(id))
        );
    }
}
