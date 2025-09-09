package com.carlosalexandredevv.crud_courses.modules.course.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carlosalexandredevv.crud_courses.modules.course.entities.CourseEntity;

public interface CourseRepository extends JpaRepository<CourseEntity, UUID> {
}
