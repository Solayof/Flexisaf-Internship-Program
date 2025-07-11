package com.flexisaf.backendinternship.restController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.access.prepost.PreAuthorize;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import com.flexisaf.backendinternship.entity.UserEntity;
import com.flexisaf.backendinternship.exception.UserNotFoundException;
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
    @PreAuthorize("hasRole('MODERATOR') or hasRole('SUPERADMIN') or hasRole('ADMIN')")
    @Operation(
        summary = "Get list of Users in the server",
        description = "Retrieve list of users in the server and request user most right permission to retrieve data"
        )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "successfully get User list",
                    content = @Content(
                        mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = UserEntity.class)),
                        examples = @ExampleObject(
                            name = "userListExample",
                            summary = "Sample user list",
                            value = """
                            {
                                "_embedded": {
                                    "userEntityList": [
                                        
                                        {
                                            "id": "4dc10564-ce1c-443b-81ae-4eb452c32a88",
                                            "firstName": "Ahmad",
                                            "middleName": "Powlowski",
                                            "lastName": "Becker",
                                            "email": "luciano.hirthe@hotmail.com",
                                            "dob": "30/06/2025",
                                            "gender": "Female",
                                            "createdAt": "2025-06-30T21:16:02.956142",
                                            "roles": [
                                                {
                                                    "name": "ROLE_ADMIN"
                                                },
                                                {
                                                    "name": "ROLE_MODERATOR"
                                                }
                                            ],
                                            "_links": {
                                                "self": {
                                                    "href": "http://localhost:8080/users/4dc10564-ce1c-443b-81ae-4eb452c32a88"
                                                },
                                                "users": {
                                                    "href": "http://localhost:8080/users"
                                                }
                                            }
                                        },
                                        
                                        {
                                            "id": "d3a8d0d3-1b1a-4125-b107-c2106dd6db82",
                                            "firstName": "Allen",
                                            "middleName": "Schuster",
                                            "lastName": "Okuneva",
                                            "email": "sol.damore@hotmail.com",
                                            "dob": "30/06/2025",
                                            "gender": "Male",
                                            "createdAt": "2025-06-30T21:16:03.008791",
                                            "roles": [
                                                {
                                                    "name": "ROLE_USER"
                                                }
                                            ],
                                            "_links": {
                                                "self": {
                                                    "href": "http://localhost:8080/users/d3a8d0d3-1b1a-4125-b107-c2106dd6db82"
                                                },
                                                "users": {
                                                    "href": "http://localhost:8080/users"
                                                }
                                            }
                                        }
                                    ]
                                },
                                "_links": {
                                    "self": {
                                        "href": "http://localhost:8080/users"
                                    }
                                }
                            }
                            """
                        )
        )
            ),

            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden: No authencation provided or jwt as expired or user dont have right permission to access the resources",
                    content = @Content(
                        mediaType = "application/json",
                        
                        examples = @ExampleObject(
                            name = "ErrorExample",
                            summary = "return nothing",
                            value = """
                
                            """
                        )
        )
            ),
            
    })
    public CollectionModel<EntityModel<UserEntity>> users() {
        List<EntityModel<UserEntity>> Userlist = userRepository.findAll().stream()
                .map(assembler::toModel).toList();

        return CollectionModel.of(Userlist,
                linkTo(methodOn(UserController.class).users()).withSelfRel());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('SUPERADMIN') or hasRole('ADMIN')")
    @Operation(
        summary = "Get a user with the given id in the server",
        description = "Retrieve a user from the server and request user most right permission to retrieve data"
        )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "successfully get User list",
                    content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = UserEntity.class),
                        examples = @ExampleObject(
                            name = "userListExample",
                            summary = "Sample user list",
                            value = """
                            {
                                "id": "ad0facf3-3155-402f-8d5f-34b7e566e08c",
                                "firstName": "Solomon",
                                "middleName": "Ayofemi",
                                "lastName": "Moses",
                                "email": "solop@gmail.com",
                                "dob": "12/10/1992",
                                "createdAt": "2025-06-18T00:00:00",
                                "roles": [
                                    {
                                        "name": "ROLE_ADMIN"
                                    },
                                    {
                                        "name": "ROLE_USER"
                                    },
                                    {
                                        "name": "ROLE_MODERATOR"
                                    }
                                ],
                                "_links": {
                                    "self": {
                                        "href": "http://localhost:8080/users/ad0facf3-3155-402f-8d5f-34b7e566e08c"
                                    },
                                    "users": {
                                        "href": "http://localhost:8080/users"
                                    }
                                }
                            }
                            """
                        )
        )
            ),

            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden: No authencation provided or jwt as expired or user dont have right permission to access the resources",
                    content = @Content(
                        mediaType = "application/json",
                        
                        examples = @ExampleObject(
                            name = "ErrorExample",
                            summary = "return nothing",
                            value = """
                
                            """
                        )
        )
            ),
        
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(
                        mediaType = "application/plain",
                        examples = @ExampleObject(
                            name = "userListExample",
                            summary = "Sample user list",
                            value = """
                            Could not find user with id: ad0facf3-3155-402f-8d5f-034b7e566e08
                            """
                        )
        )
            )
            
    })
    public EntityModel<UserEntity> getOne(@Valid @PathVariable UUID id) {
        UserEntity user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException(id));
        return assembler.toModel(user);
    }

    // @PostMapping("")
    // public EntityModel<UserEntity> createOne(@RequestBody UserEntity newUser) {
    //     return assembler.toModel(userRepository.save(newUser));
    // }

    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERADMIN')")
    @Operation(
        summary = "Update a user with the given id in the server",
        description = "Update a user in the server and request user most right permission to retrieve data"
        )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "successfully update a user",
                    content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = UserEntity.class),
                        examples = @ExampleObject(
                            name = "userExample",
                            summary = "Sample user",
                            value = """
                            {
                                "id": "ad0facf3-3155-402f-8d5f-34b7e566e08c",
                                "firstName": "Solomon",
                                "middleName": "Ayofemi",
                                "lastName": "Moses",
                                "email": "solop@gmail.com",
                                "dob": "12/10/1992",
                                "createdAt": "2025-06-18T00:00:00",
                                "roles": [
                                    {
                                        "name": "ROLE_ADMIN"
                                    },
                                    {
                                        "name": "ROLE_USER"
                                    },
                                    {
                                        "name": "ROLE_MODERATOR"
                                    }
                                ],
                                "_links": {
                                    "self": {
                                        "href": "http://localhost:8080/users/ad0facf3-3155-402f-8d5f-34b7e566e08c"
                                    },
                                    "users": {
                                        "href": "http://localhost:8080/users"
                                    }
                                }
                            }
                            """
                        )
        )
            ),

            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden: No authencation provided or jwt as expired or user dont have right permission to access the resources",
                    content = @Content(
                        mediaType = "application/json",
                        
                        examples = @ExampleObject(
                            name = "ErrorExample",
                            summary = "return nothing",
                            value = """
                
                            """
                        )
        )
            ),
        
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(
                        mediaType = "application/plain",
                        examples = @ExampleObject(
                            name = "userExample",
                            summary = "Sample user",
                            value = """
                            Could not find user with id: ad0facf3-3155-402f-8d5f-034b7e566e08
                            """
                        )
        )
            )
            
    })
    public EntityModel<UserEntity> updateOne(@RequestBody UserEntity newUser, @Valid @PathVariable UUID id) {
        UserEntity usr = userRepository.findById(id)
            .map(user -> {
                user.setFirstName(newUser.getFirstName());
                user.setMiddleName(newUser.getMiddleName());
                user.setLastName(newUser.getLastName());
                user.setRoles(newUser.getRoles());

                return userRepository.save(user);
            })
            .orElseThrow(() -> new UserNotFoundException(id));
        return assembler.toModel(usr);
    }

}
