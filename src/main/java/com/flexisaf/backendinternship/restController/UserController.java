package com.flexisaf.backendinternship.restController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import com.flexisaf.backendinternship.entity.UserEntity;
import com.flexisaf.backendinternship.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserRepository userRepository;
    private final UserModelAssembler assembler;

    public UserController(UserRepository userRepository, UserModelAssembler assembler) {
        this.userRepository = userRepository;
        this.assembler = assembler;
    }


    @GetMapping("")
    @Operation(summary = "Get list of UserEntity in the server")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "successfully get UserEntity list"
            )
    })
    public CollectionModel<EntityModel<UserEntity>> users() {
        List<EntityModel<UserEntity>> Userlist = userRepository.findAll().stream()
                .map(assembler::toModel).toList();

        return CollectionModel.of(Userlist,
                linkTo(methodOn(UserController.class).users()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<UserEntity> getOne(@PathVariable UUID id) {
        UserEntity user = userRepository.findById(id)
        .orElseThrow(() -> new ErrorResponseException(HttpStatusCode.valueOf(404)));
        return assembler.toModel(user);
    }

    @PostMapping("")
    public EntityModel<UserEntity> createOne(@RequestBody UserEntity newUser) {
        return assembler.toModel(userRepository.save(newUser));
    }

    @PutMapping("/{id}")
    public EntityModel<UserEntity> updateOne(@RequestBody UserEntity newUser, @PathVariable UUID id) {
        UserEntity usr = userRepository.findById(id)
            .map(user -> {
                user.setFirstName(newUser.getFirstName());
                user.setMiddleName(newUser.getMiddleName());
                user.setLastName(newUser.getLastName());
                user.setRoles(newUser.getRoles());

                return userRepository.save(user);
            })
            .orElseGet(() -> {
                return userRepository.save(newUser);
            });
            // .orElseThrow(() -> new RuntimeException("UserEntity not found with id: " + id));;
        return assembler.toModel(usr);
    }

}
