package com.carlosalexandredevv.crud_courses.modules.course.DTOs;

import com.carlosalexandredevv.crud_courses.modules.course.entities.CourseEntity;

public class CreateCourseMapper {
    public static CourseEntity toEntity(CreateCourseDTO dto) {
        return new CourseEntity(null, dto.getName(), dto.getCategory(), dto.getActive(), null, null);
    }
}