package com.carlosalexandredevv.crud_courses.modules.course.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.carlosalexandredevv.crud_courses.modules.course.DTOs.CreateCourseDTO;
import com.carlosalexandredevv.crud_courses.modules.course.entities.CourseEntity;
import com.carlosalexandredevv.crud_courses.modules.course.DTOs.CreateCourseMapper;
import com.carlosalexandredevv.crud_courses.shared.MessageResponseDTO;
import com.carlosalexandredevv.crud_courses.modules.course.repositories.CourseRepository;

@Service
public class CreateCourseUseCase {

    @Autowired
    private CourseRepository courseRepository;
    
    public MessageResponseDTO execute(CreateCourseDTO createCourseDTO) {
        try {
            CourseEntity entity = CreateCourseMapper.toEntity(createCourseDTO);
            courseRepository.save(entity);
            return new MessageResponseDTO("Course created successfully");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error creating course", e);
        }
    }
}
