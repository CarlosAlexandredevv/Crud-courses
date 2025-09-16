package com.carlosalexandredevv.crud_courses.modules.course.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.carlosalexandredevv.crud_courses.modules.course.entities.CourseEntity;
import com.carlosalexandredevv.crud_courses.modules.course.repositories.CourseRepository;
import com.carlosalexandredevv.crud_courses.modules.course.DTOs.CourseListResponseDTO;

@Service
public class ListCoursesUseCase {

    @Autowired
    private CourseRepository courseRepository;

    public CourseListResponseDTO execute(String name, String category) {
        boolean hasName = name != null && !name.isBlank();
        boolean hasCategory = category != null && !category.isBlank();

        List<CourseEntity> courses;

        if (hasName && hasCategory) {
            courses = courseRepository.findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(name, category);
        } else if (hasName) {
            courses = courseRepository.findByNameContainingIgnoreCase(name);
        } else if (hasCategory) {
            courses = courseRepository.findByCategoryContainingIgnoreCase(category);
        } else {
            courses = courseRepository.findAll();
        }

        return new CourseListResponseDTO(courses);
    }
}