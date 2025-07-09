package com.flexisaf.backendinternship.restController;

import com.flexisaf.backendinternship.entity.Course;
import org.springframework.lang.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CourseModelAssembler implements RepresentationModelAssembler<Course, EntityModel<Course>> {
    @SuppressWarnings("null")
    @Override
    public EntityModel<Course> toModel(@NonNull Course course) {
        return EntityModel.of(
                course,
                linkTo(methodOn(CourseController.class).getOne(course.getId())).withSelfRel(),
                linkTo(methodOn(CourseController.class).courses()).withSelfRel()
        );
    }
}
