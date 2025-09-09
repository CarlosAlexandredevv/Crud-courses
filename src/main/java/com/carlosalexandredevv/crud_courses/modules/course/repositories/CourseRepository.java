package com.carlosalexandredevv.crud_courses.modules.course.repositories;

import java.util.UUID;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carlosalexandredevv.crud_courses.modules.course.entities.CourseEntity;

public interface CourseRepository extends JpaRepository<CourseEntity, UUID> {
    List<CourseEntity> findByNameContainingIgnoreCase(String name);
    List<CourseEntity> findByCategoryContainingIgnoreCase(String category);
    List<CourseEntity> findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(String name, String category);
}
