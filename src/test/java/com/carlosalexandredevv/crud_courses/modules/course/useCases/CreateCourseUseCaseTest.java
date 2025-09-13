package com.carlosalexandredevv.crud_courses.modules.course.useCases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.carlosalexandredevv.crud_courses.modules.course.DTOs.CreateCourseDTO;
import com.carlosalexandredevv.crud_courses.modules.course.entities.CourseEntity;
import com.carlosalexandredevv.crud_courses.modules.course.repositories.CourseRepository;
import com.carlosalexandredevv.crud_courses.shared.MessageResponseDTO;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateCourseUseCase")
class CreateCourseUseCaseTest {
    
    @Mock
    private CourseRepository courseRepository;
    
    @InjectMocks
    private CreateCourseUseCase createCourseUseCase;
    
    private CreateCourseDTO createCourseDTO;
    
    @BeforeEach
    void setUp() {
        createCourseDTO = new CreateCourseDTO();
        createCourseDTO.setName("Java Básico");
        createCourseDTO.setCategory("Programação");
        createCourseDTO.setActive(true);
    }
    
    @Nested
    @DisplayName("Quando criar curso com sucesso")
    class WhenCreatingCourseSuccessfully {
        
        @Test
        @DisplayName("deve retornar mensagem de sucesso")
        void shouldReturnSuccessMessage() {
            MessageResponseDTO result = createCourseUseCase.execute(createCourseDTO);
                
            assertNotNull(result, "Resultado não deve ser nulo");
            assertEquals("Course created successfully", result.getMessage(), 
                "Deve retornar mensagem de sucesso");
        }
        
        @Test
        @DisplayName("deve salvar a entidade no repositório")
        void shouldSaveEntityToRepository() {
            createCourseUseCase.execute(createCourseDTO);
                
            verify(courseRepository, times(1)).save(any(CourseEntity.class));
        }
        
        @Test
        @DisplayName("deve criar entidade com dados corretos")
        void shouldCreateEntityWithCorrectData() {
            ArgumentCaptor<CourseEntity> entityCaptor = ArgumentCaptor.forClass(CourseEntity.class);
            
            createCourseUseCase.execute(createCourseDTO);
            
            verify(courseRepository).save(entityCaptor.capture());
            CourseEntity savedEntity = entityCaptor.getValue();
            
            assertEquals("Java Básico", savedEntity.getName(), "Nome deve ser mapeado corretamente");
            assertEquals("Programação", savedEntity.getCategory(), "Categoria deve ser mapeada corretamente");
            assertTrue(savedEntity.getActive(), "Status ativo deve ser mapeado corretamente");
        }
    }
    
    @Nested
    @DisplayName("Quando ocorre erro ao salvar")
    class WhenErrorOccursWhileSaving {
        
        @Test
        @DisplayName("deve lançar ResponseStatusException com BAD_REQUEST")
        void shouldThrowResponseStatusExceptionWithBadRequest() {
            when(courseRepository.save(any(CourseEntity.class)))
                .thenThrow(new RuntimeException("Database error"));
            
            ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> createCourseUseCase.execute(createCourseDTO),
                "Deveria lançar ResponseStatusException"
            );
            
            assertEquals(400, exception.getStatusCode().value(), 
                "Status code deve ser BAD_REQUEST (400)");
            assertTrue(exception.getReason().contains("Error creating course"), 
                "Mensagem deve conter 'Error creating course'");
        }
        
        @Test
        @DisplayName("deve preservar a causa original da exceção")
        void shouldPreserveOriginalExceptionCause() {
            RuntimeException originalException = new RuntimeException("Database connection failed");
            when(courseRepository.save(any(CourseEntity.class)))
                .thenThrow(originalException);
            
            ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> createCourseUseCase.execute(createCourseDTO)
            );
            
            assertEquals(originalException, exception.getCause(), 
                "Causa original deve ser preservada");
        }
    }
    
    @Nested
    @DisplayName("Quando dados de entrada são inválidos")
    class WhenInputDataIsInvalid {
        
        @Test
        @DisplayName("deve tratar DTO nulo")
        void shouldHandleNullDTO() {
            ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> createCourseUseCase.execute(null),
                "Deveria lançar ResponseStatusException para DTO nulo"
            );
            
            assertEquals(400, exception.getStatusCode().value(), 
                "Status code deve ser BAD_REQUEST (400)");
            assertTrue(exception.getReason().contains("Error creating course"), 
                "Mensagem deve conter 'Error creating course'");
        }
        
        @Test
        @DisplayName("deve tratar DTO com campos nulos")
        void shouldHandleDTOWithNullFields() {
            CreateCourseDTO invalidDTO = new CreateCourseDTO(); 
            assertDoesNotThrow(
                () -> createCourseUseCase.execute(invalidDTO),
                "Deve executar sem erro para DTO com campos nulos (comportamento atual)"
            );
        }
    }
}