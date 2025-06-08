package com.flexisaf.backendinternship.restController;

import com.flexisaf.backendinternship.entity.User;
import org.springframework.lang.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {
    @SuppressWarnings("null")
    @Override
    public EntityModel<User> toModel(@NonNull User staff) {
        return EntityModel.of(staff,
                linkTo(methodOn(UserController.class).getOne(staff.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).users()).withRel("staff")
                );
    }
}
