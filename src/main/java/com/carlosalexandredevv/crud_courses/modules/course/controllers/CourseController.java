package com.carlosalexandredevv.crud_courses.modules.course.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CreateCourseUseCase createCourse;

    @Autowired
    private ListCoursesUseCase listCourses;

    @PostMapping
    public ResponseEntity<MessageResponseDTO> createCourse(@RequestBody @Valid CreateCourseDTO createCourseDTO) {
        return ResponseEntity.ok(createCourse.execute(createCourseDTO));  
    }

    @GetMapping
    public ResponseEntity<List<CourseEntity>> listCourses(@RequestParam(required = false) String name, @RequestParam(required = false) String category) {
        return ResponseEntity.ok(listCourses.execute(name, category));
    }
}
