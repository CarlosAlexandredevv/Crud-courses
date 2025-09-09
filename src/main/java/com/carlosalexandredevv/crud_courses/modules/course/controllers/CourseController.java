package com.carlosalexandredevv.crud_courses.modules.course.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import com.carlosalexandredevv.crud_courses.modules.course.DTOs.CreateCourseDTO;
import com.carlosalexandredevv.crud_courses.modules.course.useCases.CreateCourse;
import com.carlosalexandredevv.crud_courses.shared.MessageResponseDTO;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CreateCourse createCourse;

    @PostMapping
    public ResponseEntity<MessageResponseDTO> createCourse(@RequestBody @Valid CreateCourseDTO createCourseDTO) {
        return ResponseEntity.ok(createCourse.execute(createCourseDTO));  
    }
    
}
