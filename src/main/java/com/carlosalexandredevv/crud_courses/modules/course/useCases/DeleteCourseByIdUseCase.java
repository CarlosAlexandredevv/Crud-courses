package com.carlosalexandredevv.crud_courses.modules.course.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.carlosalexandredevv.crud_courses.modules.course.entities.CourseEntity;
import com.carlosalexandredevv.crud_courses.modules.course.repositories.CourseRepository;
import com.carlosalexandredevv.crud_courses.shared.MessageResponseDTO;

@Service
public class DeleteCourseByIdUseCase {
    
    @Autowired
    private CourseRepository courseRepository;

    public MessageResponseDTO execute(UUID id) {
        try {
            courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));
            
            courseRepository.deleteById(id);
            return new MessageResponseDTO("Course deleted successfully");
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error deleting course", e);
        }
    }
}
