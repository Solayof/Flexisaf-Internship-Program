package com.flexisaf.backendinternship.restController;

import com.flexisaf.backendinternship.entity.Teacher;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class TeacherModelAssembler implements RepresentationModelAssembler<Teacher, EntityModel<Teacher>> {
    @Override
    public EntityModel<Teacher> toModel(Teacher teacher) {
        return EntityModel.of(
                teacher,
                linkTo(methodOn(TeacherController.class).getOne(teacher.getId())).withSelfRel(),
                linkTo(methodOn(TeacherController.class).teachers()).withSelfRel()
        );
    }
}
