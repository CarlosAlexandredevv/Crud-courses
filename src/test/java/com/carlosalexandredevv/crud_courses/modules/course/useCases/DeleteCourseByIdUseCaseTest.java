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
@DisplayName("DeleteCourseByIdUseCase")
class DeleteCourseByIdUseCaseTest {
    
    @Mock
    private CourseRepository courseRepository;
    
    @InjectMocks
    private DeleteCourseByIdUseCase deleteCourseByIdUseCase;
    
    @Nested
    @DisplayName("Quando deletar curso com sucesso")
    class WhenDeletingCourseSuccessfully {
        
        @Test
        @DisplayName("deve retornar mensagem de sucesso")
        void shouldReturnSuccessMessage() {
            UUID courseId = UUID.randomUUID();
            CourseEntity course = new CourseEntity();
            course.setId(courseId);
            course.setName("Java Básico");
            course.setCategory("Programação");
            course.setActive(true);
            
            when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
            doNothing().when(courseRepository).deleteById(courseId);
            
            MessageResponseDTO result = deleteCourseByIdUseCase.execute(courseId);
            
            assertNotNull(result, "Resultado não deve ser nulo");
            assertEquals("Course deleted successfully", result.getMessage(), "Deve retornar mensagem de sucesso");
            verify(courseRepository).findById(courseId);
            verify(courseRepository).deleteById(courseId);
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
                deleteCourseByIdUseCase.execute(courseId);
            }, "Deve lançar exceção quando curso não for encontrado");
            
            verify(courseRepository).findById(courseId);
            verify(courseRepository, never()).deleteById(any());
        }
    }
}
