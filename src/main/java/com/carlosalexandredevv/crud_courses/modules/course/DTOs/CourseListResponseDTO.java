package com.carlosalexandredevv.crud_courses.modules.course.DTOs;

import java.util.List;
import com.carlosalexandredevv.crud_courses.modules.course.entities.CourseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseListResponseDTO {
    
    private List<CourseEntity> courses;
    private int active;
    private int inactive;
    private int totalPages;
    private boolean hasNextPage;
    private boolean hasPreviousPage;
    private int totalItems;
}
