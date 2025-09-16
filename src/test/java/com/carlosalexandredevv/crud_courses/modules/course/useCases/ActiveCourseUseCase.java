package com.carlosalexandredevv.crud_courses.modules.course.useCases;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.carlosalexandredevv.crud_courses.modules.course.repositories.CourseRepository;
import com.carlosalexandredevv.crud_courses.modules.course.entities.CourseEntity;
import com.carlosalexandredevv.crud_courses.shared.MessageResponseDTO;
import java.util.UUID;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ActiveCourseUseCase")
class ActiveCourseUseCaseTest {
    
    @Mock
    private CourseRepository courseRepository;
    
    @InjectMocks
    private ActiveCourseUseCase activeCourseUseCase;
    
    @Nested
    @DisplayName("Quando ativar curso")
    class WhenActivatingCourse {
        
        @Test
        @DisplayName("deve ativar curso inativo com sucesso")
        void shouldActivateInactiveCourseSuccessfully() {
            UUID courseId = UUID.randomUUID();
            CourseEntity course = new CourseEntity();
            course.setId(courseId);
            course.setName("Java Básico");
            course.setCategory("Programação");
            course.setActive(false); 
            
            when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
            when(courseRepository.save(any(CourseEntity.class))).thenReturn(course);
            
            MessageResponseDTO result = activeCourseUseCase.execute(courseId);
            
            assertNotNull(result, "Resultado não deve ser nulo");
            assertEquals("Course activated successfully", result.getMessage(), "Deve retornar mensagem de ativação");
            assertTrue(course.getActive(), "Curso deve estar ativo");
            verify(courseRepository).findById(courseId);
            verify(courseRepository).save(course);
        }
    }
    
    @Nested
    @DisplayName("Quando desativar curso")
    class WhenDeactivatingCourse {
        
        @Test
        @DisplayName("deve desativar curso ativo com sucesso")
        void shouldDeactivateActiveCourseSuccessfully() {
            UUID courseId = UUID.randomUUID();
            CourseEntity course = new CourseEntity();
            course.setId(courseId);
            course.setName("Java Básico");
            course.setCategory("Programação");
            course.setActive(true); // Curso ativo
            
            when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
            when(courseRepository.save(any(CourseEntity.class))).thenReturn(course);
            
            MessageResponseDTO result = activeCourseUseCase.execute(courseId);
            
            assertNotNull(result, "Resultado não deve ser nulo");
            assertEquals("Course deactivated successfully", result.getMessage(), "Deve retornar mensagem de desativação");
            assertFalse(course.getActive(), "Curso deve estar inativo");
            verify(courseRepository).findById(courseId);
            verify(courseRepository).save(course);
        }
    }
    
    @Nested
    @DisplayName("Quando curso não for encontrado")
    class WhenCourseNotFound {
        
        @Test
        @DisplayName("deve lançar exceção 404")
        void shouldThrowNotFoundException() {
            UUID courseId = UUID.randomUUID();
            when(courseRepository.findById(courseId)).thenReturn(Optional.empty());
            
            assertThrows(org.springframework.web.server.ResponseStatusException.class, () -> {
                activeCourseUseCase.execute(courseId);
            }, "Deve lançar exceção quando curso não for encontrado");
            
            verify(courseRepository).findById(courseId);
            verify(courseRepository, never()).save(any());
        }
    }
    
    @Nested
    @DisplayName("Quando ocorrer erro ao salvar")
    class WhenErrorOccursWhileSaving {
        
        @Test
        @DisplayName("deve lançar exceção 400")
        void shouldThrowBadRequestException() {
            UUID courseId = UUID.randomUUID();
            CourseEntity course = new CourseEntity();
            course.setId(courseId);
            course.setName("Java Básico");
            course.setCategory("Programação");
            course.setActive(false);
            
            when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
            when(courseRepository.save(any(CourseEntity.class))).thenThrow(new RuntimeException("Database error"));
            
            assertThrows(org.springframework.web.server.ResponseStatusException.class, () -> {
                activeCourseUseCase.execute(courseId);
            }, "Deve lançar exceção quando ocorrer erro ao salvar");
            
            verify(courseRepository).findById(courseId);
            verify(courseRepository).save(course);
        }
    }
}
