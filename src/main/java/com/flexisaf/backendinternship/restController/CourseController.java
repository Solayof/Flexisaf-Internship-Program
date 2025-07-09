package com.flexisaf.backendinternship.restController;


import com.flexisaf.backendinternship.entity.Course;
import com.flexisaf.backendinternship.entity.UserEntity;
import com.flexisaf.backendinternship.repository.UserRepository;
import com.flexisaf.backendinternship.service.CourseService;
import com.flexisaf.backendinternship.util.CommonUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseModelAssembler assembler;
   @Autowired
   private CommonUtil commonUtil;

   @PostMapping("")
   @Operation(
        summary = "Create a new course in the server",
        description = "Create a new course in the server and request user most right permission to create data"
        )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "successfully created a course",
                    content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = Course.class),
                        examples = @ExampleObject(
                            name = "courseExample",
                            summary = "Sample course",
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
    public EntityModel<Course> createCourse(@RequestBody Course course) {
        return assembler.toModel(courseService.createCourse(course));
    }

    @GetMapping("")
    @Operation(
        summary = "Get list of Courses in the server",
        description = "Retrieve list of courses in the server and request user most right permission to retrieve data"
        )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "successfully get course list",
                    content = @Content(
                        mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation =Course.class)),
                        examples = @ExampleObject(
                            name = "courseListExample",
                            summary = "Sample course list",
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
    public CollectionModel<EntityModel<Course>> courses() {
        List<EntityModel<Course>> courses = courseService.getAllCourses().stream()
                .map(assembler::toModel).toList();

        return CollectionModel.of(courses,
                linkTo(methodOn(CourseController.class).courses()).withSelfRel());
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get a course with the given id in the server",
        description = "Retrieve a course from the server and request user most right permission to retrieve data"
        )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "successfully get a course",
                    content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = Course.class),
                        examples = @ExampleObject(
                            name = "courseExample",
                            summary = "Sample course",
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
                        description = "course not found",
                    content = @Content(
                        mediaType = "application/plain",
                        examples = @ExampleObject(
                            name = "courseExample",
                            summary = "Sample course",
                            value = """
                            Could not find course with id: ad0facf3-3155-402f-8d5f-034b7e566e08
                            """
                        )
        )
            )
            
    })
    public EntityModel<Course> getOne(@PathVariable UUID id) {
        Course course = courseService.findById(id);
        return assembler.toModel(course);
    }

    
    @PutMapping("/{id}")
    @Operation(
        summary = "Update a course with the given id in the server",
        description = "Update a course in the server and request user most right permission to retrieve data"
        )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "successfully update a course",
                    content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = Course.class),
                        examples = @ExampleObject(
                            name = "courseExample",
                            summary = "Sample course",
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
                    description = "course not found",
                    content = @Content(
                        mediaType = "application/plain",
                        examples = @ExampleObject(
                            name = "courseExample",
                            summary = "Sample course",
                            value = """
                            Could not find course with id: ad0facf3-3155-402f-8d5f-034b7e566e08
                            """
                        )
        )
            )
            
    })
    public EntityModel<Course> updateOne(@RequestBody Course newCourse, @PathVariable UUID id) {
        return assembler.toModel(
            courseService.updateCourse(
                courseService.findById(id)
                .update(newCourse.getContent())
        ));
    }
}
