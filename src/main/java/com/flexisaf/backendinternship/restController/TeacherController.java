package com.flexisaf.backendinternship.restController;


import com.flexisaf.backendinternship.entity.Teacher;
import com.flexisaf.backendinternship.exception.TeacherNotFoundException;
import com.flexisaf.backendinternship.repository.TeacherRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherRepository teacherRepository;
    private final TeacherModelAssembler assembler;

    public TeacherController(TeacherRepository teacherRepository, TeacherModelAssembler assembler) {
        this.teacherRepository = teacherRepository;
        this.assembler = assembler;
    }

    

    @GetMapping("")
    @Operation(
        summary = "Get list of Teachers in the server",
        description = "Retrieve list of teachers in the server and request user most right permission to retrieve data"
        )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "successfully get teacher list",
                    content = @Content(
                        mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation =Teacher.class)),
                        examples = @ExampleObject(
                            name = "teacherListExample",
                            summary = "Sample teacher list",
                            value = """
                            
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
    public CollectionModel<EntityModel<Teacher>> teachers() {
        List<EntityModel<Teacher>> teacherlist = teacherRepository.findAll().stream()
                .map(assembler::toModel).toList();

        return CollectionModel.of(teacherlist,
                linkTo(methodOn(TeacherController.class).teachers()).withSelfRel());
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get a teacher with the given id in the server",
        description = "Retrieve a teacher from the server and request user most right permission to retrieve data"
        )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "successfully get a teacher",
                    content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = Teacher.class),
                        examples = @ExampleObject(
                            name = "teacherExample",
                            summary = "Sample teacher",
                            value = """
                            
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
                        description = "teacher not found",
                    content = @Content(
                        mediaType = "application/plain",
                        examples = @ExampleObject(
                            name = "teacherExample",
                            summary = "Sample teacher",
                            value = """
                            Could not find teacher with id: ad0facf3-3155-402f-8d5f-034b7e566e08
                            """
                        )
        )
            )
            
    })
    public EntityModel<Teacher> getOne(@PathVariable UUID id) {
        Teacher teacher = teacherRepository.findById(id)
        .orElseThrow(() -> new TeacherNotFoundException(id));
        return assembler.toModel(teacher);
    }

    
    @PutMapping("/{id}")
    @Operation(
        summary = "Update a teacher with the given id in the server",
        description = "Update a teacher in the server and request user most right permission to retrieve data"
        )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "successfully update a teacher",
                    content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = Teacher.class),
                        examples = @ExampleObject(
                            name = "teacherExample",
                            summary = "Sample teacher",
                            value = """
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
                    description = "teacher not found",
                    content = @Content(
                        mediaType = "application/plain",
                        examples = @ExampleObject(
                            name = "teacherExample",
                            summary = "Sample teacher",
                            value = """
                            Could not find teacher with id: ad0facf3-3155-402f-8d5f-034b7e566e08
                            """
                        )
        )
            )
            
    })
    public EntityModel<Teacher> updateOne(@RequestBody Teacher newteacher, @PathVariable UUID id) {
        return assembler.toModel(
            teacherRepository.findById(id)
            .map(teacher -> {
                teacher.setDiscipline(newteacher.getDiscipline());

                return teacherRepository.save(teacher);
            })
            .orElseThrow(() -> new TeacherNotFoundException(id))
        );
    }
}
