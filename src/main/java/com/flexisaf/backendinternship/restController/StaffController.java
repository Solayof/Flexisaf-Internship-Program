package com.flexisaf.backendinternship.restController;

import com.flexisaf.backendinternship.entity.Teacher;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.PostConstruct;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import com.flexisaf.backendinternship.entity.Staff;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("staff")
public class StaffController {
    private List<Staff> staff;

    @PostConstruct
    public void staffList() {
        staff = new ArrayList<Staff>();

        staff.add(new Staff(1, "solomon", "moses", "solomonayofemi@gmail.com", "08088664424"));
        staff.add(new Staff(2,"Ojo", "Arisekola", "arisekola@top.com", "08088664424"));
        staff.add(new Staff(3,"Joy", "Pelumi", "pelumi@top.com", "08088664424"));
        staff.add(new Staff(4,"Tolu", "Mariam", "mariam@top.com", "08088664424"));
        staff.add(new Staff(5,"Loveth", "Arieren", "arieren@top.com", "08088664424"));

    }

    @GetMapping("")
    @Operation(summary = "Get list of staff in the server")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "successfully get staff list"
            )
    })
    public CollectionModel<EntityModel<Staff>> staff() {
        List<EntityModel<Staff>> stafflist = staff.stream()
                .map(astaff -> EntityModel.of(astaff,
                        linkTo(methodOn(StaffController.class).getOne(astaff.getId())).withSelfRel(),
                        linkTo(methodOn(StaffController.class).staff()).withRel("staff")
                )).toList();

        return CollectionModel.of(stafflist,
                linkTo(methodOn(StaffController.class).staff()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Staff> getOne(@PathVariable int id) {
        if (id == 0 || staff.size()  < id) {
            throw new ErrorResponseException(HttpStatusCode.valueOf(404));
        }
        return EntityModel.of(staff.get(id - 1),
                linkTo(methodOn(StaffController.class).getOne(id)).withSelfRel(),
                linkTo(methodOn(StaffController.class).staff()).withRel("staff")
                );
    }

    @PostMapping("")
    public EntityModel<Staff> createOne(@RequestBody Staff newStaff) {
        int id = staff.size();
        newStaff.setId(id);
        staff.add(newStaff);
        return EntityModel.of(staff.get(id),
                linkTo(methodOn(StaffController.class).createOne(newStaff)).withSelfRel(),
                linkTo(methodOn(StaffController.class).staff()).withRel("staff")
        );
    }

    @PutMapping("/{id}")
    public EntityModel<Staff> updateOne(@RequestBody Staff newStaff, @PathVariable int id) {
        if (id == 0 || staff.size()  < id) {
            throw new ErrorResponseException(HttpStatusCode.valueOf(404));
        }
        newStaff.setId(id);
        staff.set(id - 1, newStaff);
        return EntityModel.of(staff.get(id - 1),
                linkTo(methodOn(StaffController.class).updateOne(newStaff, id)).withSelfRel(),
                linkTo(methodOn(StaffController.class).staff()).withRel("staff")
        );
    }

}
