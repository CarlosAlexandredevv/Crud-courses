package com.carlosalexandredevv.crud_courses.modules.course.DTOs;

import com.carlosalexandredevv.crud_courses.modules.course.entities.CourseEntity;

public class UpdateCourseMapper {
    
    public static CourseEntity toEntity(UpdateCourseDTO dto) {
        return new CourseEntity(null, dto.getName(), dto.getCategory(), null, null, null);
    }
}
