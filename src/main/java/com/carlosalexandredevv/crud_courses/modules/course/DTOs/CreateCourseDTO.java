package com.carlosalexandredevv.crud_courses.modules.course.DTOs;

import com.carlosalexandredevv.crud_courses.modules.course.entities.CourseEntity;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
public class CreateCourseDTO {


    
    @NotBlank(message = "name is required")
    @Size(min = 3, max = 255, message = "name must be between 3 and 255 characters")
    private String name;

    @NotBlank(message = "category is required")
    @Size(min = 3, max = 25, message = "category must be between 3 and 25 characters")
    private String category;

    @NotNull(message = "active is required")
    private Boolean active;
}
