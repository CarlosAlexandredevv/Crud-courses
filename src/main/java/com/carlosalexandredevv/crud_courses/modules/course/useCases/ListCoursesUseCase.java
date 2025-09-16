package com.carlosalexandredevv.crud_courses.modules.course.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.carlosalexandredevv.crud_courses.modules.course.entities.CourseEntity;
import com.carlosalexandredevv.crud_courses.modules.course.repositories.CourseRepository;
import com.carlosalexandredevv.crud_courses.modules.course.DTOs.CourseListResponseDTO;

@Service
public class ListCoursesUseCase {

    @Autowired
    private CourseRepository courseRepository;

    public CourseListResponseDTO execute(String name, String category, int page, int limit) {
        boolean hasName = name != null && !name.isBlank();
        boolean hasCategory = category != null && !category.isBlank();

        List<CourseEntity> allCourses;

        if (hasName && hasCategory) {
            allCourses = courseRepository.findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(name, category);
        } else if (hasName) {
            allCourses = courseRepository.findByNameContainingIgnoreCase(name);
        } else if (hasCategory) {
            allCourses = courseRepository.findByCategoryContainingIgnoreCase(category);
        } else {
            allCourses = courseRepository.findAll();
        }

        int safeLimit = limit > 0 ? limit : 5;
        int totalAll = allCourses.size();
        int totalPages = safeLimit == 0 ? 0 : (int) Math.ceil((double) totalAll / safeLimit);

        int safePage = page < 1 ? 1 : page;
        if (totalPages > 0 && safePage > totalPages) {
            safePage = totalPages;
        }

        int startIndex = totalPages == 0 ? 0 : (safePage - 1) * safeLimit;
        int endIndex = Math.min(startIndex + safeLimit, totalAll);

        List<CourseEntity> pageItems = totalAll == 0 ? List.of() : allCourses.subList(startIndex, endIndex);

        int totalItems = pageItems.size();
        int active = (int) pageItems.stream().filter(c -> c.getActive()).count();
        int inactive = (int) pageItems.stream().filter(c -> !c.getActive()).count();

        boolean hasNextPage = totalPages > 0 && safePage < totalPages;
        boolean hasPreviousPage = totalPages > 0 && safePage > 1;

        return new CourseListResponseDTO(
            pageItems,
            totalItems,
            totalAll,
            active,
            inactive,
            totalPages,
            hasNextPage,
            hasPreviousPage
        );
    }


    public CourseListResponseDTO execute(String name, String category) {
        return execute(name, category, 1, 0);
    }
}