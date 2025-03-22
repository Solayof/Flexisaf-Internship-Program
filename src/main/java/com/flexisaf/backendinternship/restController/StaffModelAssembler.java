package com.flexisaf.backendinternship.restController;

import com.flexisaf.backendinternship.entity.Staff;
import org.springframework.lang.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class StaffModelAssembler implements RepresentationModelAssembler<Staff, EntityModel<Staff>> {
    @Override
    public EntityModel<Staff> toModel(@NonNull Staff staff) {
        return EntityModel.of(staff,
                linkTo(methodOn(StaffController.class).getOne(staff.getId())).withSelfRel(),
                linkTo(methodOn(StaffController.class).staff()).withRel("staff")
                );
    }
}
