package com.flexisaf.backendinternship.restController;

import com.flexisaf.backendinternship.entity.UserEntity;
import org.springframework.lang.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<UserEntity, EntityModel<UserEntity>> {
    @SuppressWarnings("null")
    @Override
    public EntityModel<UserEntity> toModel(@NonNull UserEntity staff) {
        return EntityModel.of(staff,
                linkTo(methodOn(UserController.class).getOne(staff.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).users()).withRel("staff")
                );
    }
}
