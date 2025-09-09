package com.carlosalexandredevv.crud_courses.modules.course.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.carlosalexandredevv.crud_courses.modules.course.entities.CourseEntity;
import com.carlosalexandredevv.crud_courses.modules.course.repositories.CourseRepository;

@Service
public class ListCoursesUseCase {

    @Autowired
    private CourseRepository courseRepository;

    public List<CourseEntity> execute(String name, String category) {
        boolean hasName = name != null && !name.isBlank();
        boolean hasCategory = category != null && !category.isBlank();

        if (hasName && hasCategory) {
            return courseRepository.findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(name, category);
        }

        if (hasName) {
            return courseRepository.findByNameContainingIgnoreCase(name);
        }

        if (hasCategory) {
            return courseRepository.findByCategoryContainingIgnoreCase(category);
        }

        return courseRepository.findAll();
    }
}