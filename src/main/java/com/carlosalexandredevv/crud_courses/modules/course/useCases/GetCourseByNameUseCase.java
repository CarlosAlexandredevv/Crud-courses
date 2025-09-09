package com.carlosalexandredevv.crud_courses.modules.course.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import com.carlosalexandredevv.crud_courses.modules.course.entities.CourseEntity;
import com.carlosalexandredevv.crud_courses.modules.course.repositories.CourseRepository;

@Service
public class GetCourseByNameUseCase {
    
    @Autowired
    private CourseRepository courseRepository;

    public List<CourseEntity> execute(String name) {
        try{
            return courseRepository.findByNameContainingIgnoreCase(name);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
        }
    }
}
