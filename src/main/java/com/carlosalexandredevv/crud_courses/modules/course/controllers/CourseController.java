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
import jakarta.validation.Valid;

import com.carlosalexandredevv.crud_courses.modules.course.DTOs.CreateCourseDTO;
import com.carlosalexandredevv.crud_courses.modules.course.useCases.CreateCourseUseCase;
import com.carlosalexandredevv.crud_courses.modules.course.useCases.ListCoursesUseCase;
import com.carlosalexandredevv.crud_courses.shared.MessageResponseDTO;
import com.carlosalexandredevv.crud_courses.modules.course.entities.CourseEntity;
import java.util.List;
import java.util.UUID;
import com.carlosalexandredevv.crud_courses.modules.course.DTOs.UpdateCourseDTO;
import com.carlosalexandredevv.crud_courses.modules.course.useCases.UpdateCourseByIdUseCase;
import com.carlosalexandredevv.crud_courses.modules.course.useCases.DeleteCourseByIdUseCase;
import com.carlosalexandredevv.crud_courses.modules.course.useCases.ActiveCourseUseCase;

@RestController
@RequestMapping("/courses")
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
    public ResponseEntity<MessageResponseDTO> createCourse(@RequestBody @Valid CreateCourseDTO createCourseDTO) {
        return ResponseEntity.ok(createCourse.execute(createCourseDTO));  
    }

    @GetMapping
    public ResponseEntity<List<CourseEntity>> listCourses(@RequestParam(required = false) String name, @RequestParam(required = false) String category) {
        return ResponseEntity.ok(listCourses.execute(name, category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> updateCourseById(@PathVariable UUID id, @RequestBody @Valid UpdateCourseDTO updateCourseDTO) {
        return ResponseEntity.ok(updateCourseById.execute(id, updateCourseDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> deleteCourseById(@PathVariable UUID id) {
        return ResponseEntity.ok(deleteCourseById.execute(id));
    }

    @PatchMapping("/{id}/active")
    public ResponseEntity<MessageResponseDTO> activeCourseById(@PathVariable UUID id) {
        return ResponseEntity.ok(activeCourseById.execute(id));
    }
}
