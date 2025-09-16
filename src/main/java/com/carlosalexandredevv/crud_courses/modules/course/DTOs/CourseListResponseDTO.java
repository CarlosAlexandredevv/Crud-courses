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
    private int total;
    private int active;
    private int inactive;
    
    public CourseListResponseDTO(List<CourseEntity> courses) {
        this.courses = courses;
        this.total = courses.size();
        this.active = (int) courses.stream().filter(course -> course.getActive()).count();
        this.inactive = (int) courses.stream().filter(course -> !course.getActive()).count();
    }
}
