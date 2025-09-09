package com.carlosalexandredevv.crud_courses.modules.course.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlosalexandredevv.crud_courses.modules.course.repositories.CourseRepository;
import com.carlosalexandredevv.crud_courses.modules.course.entities.CourseEntity;
import com.carlosalexandredevv.crud_courses.modules.course.DTOs.UpdateCourseDTO;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import com.carlosalexandredevv.crud_courses.shared.MessageResponseDTO;

@Service
public class UpdateCourseByIdUseCase {
    
    @Autowired
    private CourseRepository courseRepository;

    public MessageResponseDTO execute(UUID id, UpdateCourseDTO updateCourseDTO) {
        try {
            CourseEntity existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));
            
            if (updateCourseDTO.getName() != null && !updateCourseDTO.getName().trim().isEmpty()) {
                existingCourse.setName(updateCourseDTO.getName());
            }
            
            if (updateCourseDTO.getCategory() != null && !updateCourseDTO.getCategory().trim().isEmpty()) {
                existingCourse.setCategory(updateCourseDTO.getCategory());
            }
            
            courseRepository.save(existingCourse);
            
            return new MessageResponseDTO("Course updated successfully");
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error updating course", e);
        }
    }
}
