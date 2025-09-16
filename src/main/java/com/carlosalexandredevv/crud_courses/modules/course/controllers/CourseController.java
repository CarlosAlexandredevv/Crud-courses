package com.carlosalexandredevv.crud_courses.modules.course.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import com.carlosalexandredevv.crud_courses.modules.course.DTOs.CreateCourseDTO;
import com.carlosalexandredevv.crud_courses.modules.course.DTOs.CourseListResponseDTO;
import com.carlosalexandredevv.crud_courses.modules.course.useCases.CreateCourseUseCase;
import com.carlosalexandredevv.crud_courses.modules.course.useCases.ListCoursesUseCase;
import com.carlosalexandredevv.crud_courses.shared.MessageResponseDTO;
import java.util.UUID;
import com.carlosalexandredevv.crud_courses.modules.course.DTOs.UpdateCourseDTO;
import com.carlosalexandredevv.crud_courses.modules.course.useCases.UpdateCourseByIdUseCase;
import com.carlosalexandredevv.crud_courses.modules.course.useCases.DeleteCourseByIdUseCase;
import com.carlosalexandredevv.crud_courses.modules.course.useCases.ActiveCourseUseCase;

@RestController
@RequestMapping("/courses")
@Tag(name = "Course", description = "Course API")
public class CourseController {

    @Autowired
    private CreateCourseUseCase createCourse;

    @Autowired
    private ListCoursesUseCase listCourses;

    @Autowired
    private UpdateCourseByIdUseCase updateCourseById;

    @Autowired
    private DeleteCourseByIdUseCase deleteCourseById;

    @Autowired
    private ActiveCourseUseCase activeCourseById;

    @PostMapping
    @Operation(summary = "Create course", description = "Create a new course")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Course created successfully", 
            content = @Content(schema = @Schema(implementation = MessageResponseDTO.class, example = "{\"message\": \"Course created successfully\"}"))),
        @ApiResponse(responseCode = "400", description = "Error creating course", 
            content = @Content(schema = @Schema(implementation = MessageResponseDTO.class, example = "{\"message\": \"Error creating course\"}"))),
    })
    public ResponseEntity<MessageResponseDTO> createCourse(@RequestBody @Valid CreateCourseDTO createCourseDTO) {
        return ResponseEntity.ok(createCourse.execute(createCourseDTO));  
    }

    @GetMapping
    @Operation(summary = "List courses", description = "List all courses with statistics")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = CourseListResponseDTO.class))),
    })
    public ResponseEntity<CourseListResponseDTO> listCourses(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String category,
        @RequestParam(required = false, defaultValue = "1") int page,
        @RequestParam(required = false, defaultValue = "5") int limit
    ) {
        return ResponseEntity.ok(listCourses.execute(name, category, page, limit));
    }

    
    @PutMapping("/{id}")
    @Operation(summary = "Update course by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Course updated successfully", 
            content = @Content(schema = @Schema(implementation = MessageResponseDTO.class, example = "{\"message\": \"Course updated successfully\"}"))),
        @ApiResponse(responseCode = "400", description = "Error updating course", 
            content = @Content(schema = @Schema(implementation = MessageResponseDTO.class, example = "{\"message\": \"Error updating course\"}"))),
        @ApiResponse(responseCode = "404", description = "Course not found", 
            content = @Content(schema = @Schema(implementation = MessageResponseDTO.class, example = "{\"message\": \"Course not found\"}"))),

    })
    public ResponseEntity<MessageResponseDTO> updateCourseById(@PathVariable UUID id, @RequestBody @Valid UpdateCourseDTO updateCourseDTO) {
        return ResponseEntity.ok(updateCourseById.execute(id, updateCourseDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete course by id", description = "Delete a course by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Course deleted successfully", 
            content = @Content(schema = @Schema(implementation = MessageResponseDTO.class, example = "{\"message\": \"Course deleted successfully\"}"))),
        @ApiResponse(responseCode = "404", description = "Course not found", 
            content = @Content(schema = @Schema(implementation = MessageResponseDTO.class, example = "{\"message\": \"Course not found\"}"))),
    })
    public ResponseEntity<MessageResponseDTO> deleteCourseById(@PathVariable UUID id) {
        return ResponseEntity.ok(deleteCourseById.execute(id));
    }

    @PatchMapping("/{id}/active")
    @Operation(summary = "Toggle course active status", description = "Toggle the active status of a course by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Course status updated successfully", 
            content = @Content(schema = @Schema(implementation = MessageResponseDTO.class, example = "{\"message\": \"Course status updated successfully\"}"))),
        @ApiResponse(responseCode = "404", description = "Course not found", 
            content = @Content(schema = @Schema(implementation = MessageResponseDTO.class, example = "{\"message\": \"Course not found\"}"))),
    })
    public ResponseEntity<MessageResponseDTO> activeCourseById(@PathVariable UUID id) {
        return ResponseEntity.ok(activeCourseById.execute(id));
    }
}
