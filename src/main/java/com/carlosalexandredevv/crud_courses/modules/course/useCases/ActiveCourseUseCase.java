package com.carlosalexandredevv.crud_courses.modules.course.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlosalexandredevv.crud_courses.modules.course.repositories.CourseRepository;
import com.carlosalexandredevv.crud_courses.modules.course.entities.CourseEntity;
import com.carlosalexandredevv.crud_courses.shared.MessageResponseDTO;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ActiveCourseUseCase {

    @Autowired
    private CourseRepository courseRepository;
    
    public MessageResponseDTO execute(UUID id) {    
        try {
            CourseEntity course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));
            
            boolean newActiveState = !course.getActive();
            
            course.setActive(newActiveState);
            
            courseRepository.save(course);
            
            String message = newActiveState ? "Course activated successfully" : "Course deactivated successfully";
           
            return new MessageResponseDTO(message);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error toggling course status", e);
        }
    }
}
