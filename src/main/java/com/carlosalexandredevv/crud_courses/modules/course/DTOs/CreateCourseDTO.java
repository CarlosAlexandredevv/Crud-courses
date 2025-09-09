package com.carlosalexandredevv.crud_courses.modules.course.DTOs;

import lombok.Data;

@Data
public class CreateCourseDTO {
    
    private String name;
    private String category;
    private Boolean active;
}
