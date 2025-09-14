package com.carlosalexandredevv.crud_courses.modules.course.useCases;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import com.carlosalexandredevv.crud_courses.modules.course.DTOs.UpdateCourseDTO;
import com.carlosalexandredevv.crud_courses.modules.course.repositories.CourseRepository;
import com.carlosalexandredevv.crud_courses.modules.course.entities.CourseEntity;
import com.carlosalexandredevv.crud_courses.shared.MessageResponseDTO;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Nested
@DisplayName("UpdateCourseByIdUseCaseTeste")
class UpdateCourseByIdUseCaseTeste {
    
    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private UpdateCourseByIdUseCase updateCourseByIdUseCase;

    @Test
    @DisplayName("deve atualizar um curso com sucesso")
    void shouldUpdateCourseSuccessfully() {
        UUID courseId = UUID.randomUUID();
        UpdateCourseDTO updateCourseDTO = new UpdateCourseDTO();
        updateCourseDTO.setName("Java Avançado");
        updateCourseDTO.setCategory("Programação");

        CourseEntity existingCourse = new CourseEntity();
        existingCourse.setId(courseId);
        existingCourse.setName("Java Básico");
        existingCourse.setCategory("Programação");
        existingCourse.setActive(true);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(existingCourse));
        when(courseRepository.save(any(CourseEntity.class))).thenReturn(existingCourse);

        MessageResponseDTO result = updateCourseByIdUseCase.execute(courseId, updateCourseDTO);

        assertNotNull(result);
        assertEquals("Course updated successfully", result.getMessage());
        verify(courseRepository).findById(courseId);
        verify(courseRepository).save(existingCourse);
    }

    @Test
    @DisplayName("deve retornar uma exceção quando o curso não for encontrado")
    void shouldReturnExceptionWhenCourseNotFound() {
        UUID courseId = UUID.randomUUID();
        UpdateCourseDTO updateCourseDTO = new UpdateCourseDTO();
        updateCourseDTO.setName("Java Avançado");
        updateCourseDTO.setCategory("Programação");

        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, 
            () -> updateCourseByIdUseCase.execute(courseId, updateCourseDTO));
        
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Course not found", exception.getReason());
        verify(courseRepository).findById(courseId);
        verify(courseRepository, never()).save(any(CourseEntity.class));
    }

    @Test
    @DisplayName("deve atualizar apenas o nome quando a categoria for nula")
    void shouldUpdateOnlyNameWhenCategoryIsNull() {
        UUID courseId = UUID.randomUUID();
        UpdateCourseDTO updateCourseDTO = new UpdateCourseDTO();
        updateCourseDTO.setName("Java Avançado");
        updateCourseDTO.setCategory(null);

        CourseEntity existingCourse = new CourseEntity();
        existingCourse.setId(courseId);
        existingCourse.setName("Java Básico");
        existingCourse.setCategory("Programação");
        existingCourse.setActive(true);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(existingCourse));
        when(courseRepository.save(any(CourseEntity.class))).thenReturn(existingCourse);

        MessageResponseDTO result = updateCourseByIdUseCase.execute(courseId, updateCourseDTO);

        assertNotNull(result);
        assertEquals("Course updated successfully", result.getMessage());
        assertEquals("Java Avançado", existingCourse.getName());
        assertEquals("Programação", existingCourse.getCategory());
        verify(courseRepository).findById(courseId);
        verify(courseRepository).save(existingCourse);
    }

    @Test
    @DisplayName("deve atualizar apenas a categoria quando o nome for nulo")
    void shouldUpdateOnlyCategoryWhenNameIsNull() {
        UUID courseId = UUID.randomUUID();
        UpdateCourseDTO updateCourseDTO = new UpdateCourseDTO();
        updateCourseDTO.setName(null);
        updateCourseDTO.setCategory("Desenvolvimento");

        CourseEntity existingCourse = new CourseEntity();
        existingCourse.setId(courseId);
        existingCourse.setName("Java Básico");
        existingCourse.setCategory("Programação");
        existingCourse.setActive(true);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(existingCourse));
        when(courseRepository.save(any(CourseEntity.class))).thenReturn(existingCourse);

        MessageResponseDTO result = updateCourseByIdUseCase.execute(courseId, updateCourseDTO);

        assertNotNull(result);
        assertEquals("Course updated successfully", result.getMessage());
        assertEquals("Java Básico", existingCourse.getName());
        assertEquals("Desenvolvimento", existingCourse.getCategory());
        verify(courseRepository).findById(courseId);
        verify(courseRepository).save(existingCourse);
    }

    @Test
    @DisplayName("não deve atualizar quando nome e categoria forem vazios")
    void shouldNotUpdateWhenNameAndCategoryAreEmpty() {
        UUID courseId = UUID.randomUUID();
        UpdateCourseDTO updateCourseDTO = new UpdateCourseDTO();
        updateCourseDTO.setName("");
        updateCourseDTO.setCategory("   ");

        CourseEntity existingCourse = new CourseEntity();
        existingCourse.setId(courseId);
        existingCourse.setName("Java Básico");
        existingCourse.setCategory("Programação");
        existingCourse.setActive(true);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(existingCourse));
        when(courseRepository.save(any(CourseEntity.class))).thenReturn(existingCourse);

        MessageResponseDTO result = updateCourseByIdUseCase.execute(courseId, updateCourseDTO);

        assertNotNull(result);
        assertEquals("Course updated successfully", result.getMessage());
        assertEquals("Java Básico", existingCourse.getName());
        assertEquals("Programação", existingCourse.getCategory());
        verify(courseRepository).findById(courseId);
        verify(courseRepository).save(existingCourse);
    }
}
