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
    public EntityModel<UserEntity> toModel(@NonNull UserEntity user) {
        // List<String> roles = user.getRoles().stream()
        // .map(role -> role.getName().name())
        // .collect(Collectors.toList());

        // JwtResponseDTO responseDTO = new JwtResponseDTO();
        // responseDTO.setEmail(user.getEmail());
        // responseDTO.setFirstName(user.getFirstName());
        // responseDTO.setMiddleName(user.getMiddleName());
        // responseDTO.setId(user.getId());
        // responseDTO.setLastName(user.getLastName());
        // responseDTO.setRoles(roles);
        return EntityModel.of(user,
                linkTo(methodOn(UserController.class).getOne(user.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).users()).withRel("users")
                );
    }
}
