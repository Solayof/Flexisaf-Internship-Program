package com.flexisaf.backendinternship.restController;

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
import java.util.List;

@RestController
@RequestMapping("staff")
public class StaffController {
    private List<Staff> staff;
    private final StaffModelAssembler assembler;

    public StaffController(List<Staff> staff, StaffModelAssembler assembler) {
        this.assembler = assembler;
        this.staff = staff;
    }

    @PostConstruct
    public void staffList() {
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
                .map(assembler::toModel).toList();

        return CollectionModel.of(stafflist,
                linkTo(methodOn(StaffController.class).staff()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Staff> getOne(@PathVariable int id) {
        if (id == 0 || staff.size()  < id) {
            throw new ErrorResponseException(HttpStatusCode.valueOf(404));
        }
        return assembler.toModel(staff.get(id - 1));
    }

    @PostMapping("")
    public EntityModel<Staff> createOne(@RequestBody Staff newStaff) {
        int id = staff.size();
        newStaff.setId(id);
        staff.add(newStaff);
        return assembler.toModel(staff.get(id));
    }

    @PutMapping("/{id}")
    public EntityModel<Staff> updateOne(@RequestBody Staff newStaff, @PathVariable int id) {
        if (id == 0 || staff.size()  < id) {
            throw new ErrorResponseException(HttpStatusCode.valueOf(404));
        }
        newStaff.setId(id);
        staff.set(id - 1, newStaff);
        return assembler.toModel(staff.get(id - 1));
    }

}
