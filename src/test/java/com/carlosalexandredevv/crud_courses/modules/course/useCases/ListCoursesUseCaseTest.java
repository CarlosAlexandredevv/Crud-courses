package com.carlosalexandredevv.crud_courses.modules.course.useCases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.carlosalexandredevv.crud_courses.modules.course.entities.CourseEntity;
import com.carlosalexandredevv.crud_courses.modules.course.repositories.CourseRepository;
import com.carlosalexandredevv.crud_courses.modules.course.DTOs.CourseListResponseDTO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@DisplayName("ListCoursesUseCase")
class ListCoursesUseCaseTest {
    
    @Mock
    private CourseRepository courseRepository;
    
    @InjectMocks
    private ListCoursesUseCase listCoursesUseCase;
    
    private CourseEntity javaCourse;
    private CourseEntity springCourse;
    private CourseEntity javascriptCourse;
    
    @BeforeEach
    void setUp() {
        javaCourse = createCourse("Java Básico", "Programação", true);
        springCourse = createCourse("Spring Boot", "Framework", true);
        javascriptCourse = createCourse("JavaScript Avançado", "Programação", false);
    }
    
    private CourseEntity createCourse(String name, String category, Boolean active) {
        CourseEntity course = new CourseEntity();
        course.setId(UUID.randomUUID());
        course.setName(name);
        course.setCategory(category);
        course.setActive(active);
        return course;
    }

    @Nested
    @DisplayName("Quando não há filtros")
    class WhenNoFilters {
        
        @Test
        @DisplayName("deve retornar todos os cursos")
        void shouldReturnAllCourses() {
            List<CourseEntity> expectedCourses = Arrays.asList(javaCourse, springCourse, javascriptCourse);
            when(courseRepository.findAll()).thenReturn(expectedCourses);
            
            CourseListResponseDTO result = listCoursesUseCase.execute(null, null, 1, 10);
            
            assertNotNull(result, "Resultado não deve ser nulo");
            assertNotNull(result.getCourses(), "Lista de cursos não deve ser nula");
            assertEquals(3, result.getCourses().size(), "Deve retornar 3 cursos");
            assertEquals(3, result.getTotalItems(), "Total deve ser 3");
            assertEquals(2, result.getActive(), "Deve ter 2 cursos ativos");
            assertEquals(1, result.getInactive(), "Deve ter 1 curso inativo");
            assertTrue(result.getCourses().containsAll(expectedCourses), "Deve conter todos os cursos esperados");
            verify(courseRepository).findAll();
            verify(courseRepository, never()).findByNameContainingIgnoreCase(any());
            verify(courseRepository, never()).findByCategoryContainingIgnoreCase(any());
            verify(courseRepository, never()).findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(any(), any());
        }
    }
    
    @Nested
    @DisplayName("Quando há filtro por nome")
    class WhenNameFilter {
        
        @Test
        @DisplayName("deve retornar cursos que contenham o nome")
        void shouldReturnCoursesByName() {
            String nameFilter = "Java";
            List<CourseEntity> expectedCourses = Arrays.asList(javaCourse);
            when(courseRepository.findByNameContainingIgnoreCase(nameFilter)).thenReturn(expectedCourses);
            
            CourseListResponseDTO result = listCoursesUseCase.execute(nameFilter, null, 1, 10);
            
            assertNotNull(result, "Resultado não deve ser nulo");
            assertNotNull(result.getCourses(), "Lista de cursos não deve ser nula");
            assertEquals(1, result.getCourses().size(), "Deve retornar 1 curso");
            assertEquals(1, result.getTotalItems(), "Total deve ser 1");
            assertEquals(1, result.getActive(), "Deve ter 1 curso ativo");
            assertEquals(0, result.getInactive(), "Deve ter 0 cursos inativos");
            assertEquals("Java Básico", result.getCourses().get(0).getName(), "Deve retornar o curso Java Básico");
            verify(courseRepository).findByNameContainingIgnoreCase(nameFilter);
            verify(courseRepository, never()).findAll();
        }
        
        @Test
        @DisplayName("deve retornar lista vazia quando não encontrar cursos")
        void shouldReturnEmptyListWhenNoCoursesFound() {
            String nameFilter = "Python";
            when(courseRepository.findByNameContainingIgnoreCase(nameFilter)).thenReturn(Collections.emptyList());
            
            CourseListResponseDTO result = listCoursesUseCase.execute(nameFilter, null, 1, 10);
            
            assertNotNull(result, "Resultado não deve ser nulo");
            assertNotNull(result.getCourses(), "Lista de cursos não deve ser nula");
            assertTrue(result.getCourses().isEmpty(), "Deve retornar lista vazia");
            assertEquals(0, result.getTotalItems(), "Total deve ser 0");
            assertEquals(0, result.getActive(), "Deve ter 0 cursos ativos");
            assertEquals(0, result.getInactive(), "Deve ter 0 cursos inativos");
            verify(courseRepository).findByNameContainingIgnoreCase(nameFilter);
        }
    }
    
    @Nested
    @DisplayName("Quando há filtro por categoria")
    class WhenCategoryFilter {
        
        @Test
        @DisplayName("deve retornar cursos da categoria especificada")
        void shouldReturnCoursesByCategory() {
            String categoryFilter = "Programação";
            List<CourseEntity> expectedCourses = Arrays.asList(javaCourse, javascriptCourse);
            when(courseRepository.findByCategoryContainingIgnoreCase(categoryFilter)).thenReturn(expectedCourses);
            
            CourseListResponseDTO result = listCoursesUseCase.execute(null, categoryFilter, 1, 10);
            
            assertNotNull(result, "Resultado não deve ser nulo");
            assertNotNull(result.getCourses(), "Lista de cursos não deve ser nula");
            assertEquals(2, result.getCourses().size(), "Deve retornar 2 cursos");
            assertEquals(2, result.getTotalItems(), "Total deve ser 2");
            assertEquals(1, result.getActive(), "Deve ter 1 curso ativo");
            assertEquals(1, result.getInactive(), "Deve ter 1 curso inativo");
            assertTrue(result.getCourses().stream().allMatch(course -> "Programação".equals(course.getCategory())), 
                "Todos os cursos devem ser da categoria Programação");
            verify(courseRepository).findByCategoryContainingIgnoreCase(categoryFilter);
            verify(courseRepository, never()).findAll();
        }
    }
    
    @Nested
    @DisplayName("Quando há filtro por nome e categoria")
    class WhenNameAndCategoryFilter {
        
        @Test
        @DisplayName("deve retornar cursos que atendam ambos os critérios")
        void shouldReturnCoursesByBothFilters() {
            String nameFilter = "Java";
            String categoryFilter = "Programação";
            List<CourseEntity> expectedCourses = Arrays.asList(javaCourse);
            when(courseRepository.findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(nameFilter, categoryFilter))
                .thenReturn(expectedCourses);
            
            CourseListResponseDTO result = listCoursesUseCase.execute(nameFilter, categoryFilter, 1, 10);
            
            assertNotNull(result, "Resultado não deve ser nulo");
            assertNotNull(result.getCourses(), "Lista de cursos não deve ser nula");
            assertEquals(1, result.getCourses().size(), "Deve retornar 1 curso");
            assertEquals(1, result.getTotalItems(), "Total deve ser 1");
            assertEquals(1, result.getActive(), "Deve ter 1 curso ativo");
            assertEquals(0, result.getInactive(), "Deve ter 0 cursos inativos");
            assertEquals("Java Básico", result.getCourses().get(0).getName(), "Deve retornar o curso Java Básico");
            assertEquals("Programação", result.getCourses().get(0).getCategory(), "Deve ser da categoria Programação");
            verify(courseRepository).findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(nameFilter, categoryFilter);
            verify(courseRepository, never()).findAll();
        }
    }
    
    @Nested
    @DisplayName("Quando filtros são vazios ou em branco")
    class WhenFiltersAreEmptyOrBlank {
        
        @Test
        @DisplayName("deve retornar todos os cursos quando nome é vazio")
        void shouldReturnAllCoursesWhenNameIsEmpty() {
            List<CourseEntity> expectedCourses = Arrays.asList(javaCourse, springCourse, javascriptCourse);
            when(courseRepository.findAll()).thenReturn(expectedCourses);
            
            CourseListResponseDTO result = listCoursesUseCase.execute("", null, 1, 10);
            
            assertNotNull(result, "Resultado não deve ser nulo");
            assertNotNull(result.getCourses(), "Lista de cursos não deve ser nula");
            assertEquals(3, result.getCourses().size(), "Deve retornar 3 cursos");
            assertEquals(3, result.getTotalItems(), "Total deve ser 3");
            assertEquals(2, result.getActive(), "Deve ter 2 cursos ativos");
            assertEquals(1, result.getInactive(), "Deve ter 1 curso inativo");
            verify(courseRepository).findAll();
        }
        
        @Test
        @DisplayName("deve retornar todos os cursos quando nome é apenas espaços")
        void shouldReturnAllCoursesWhenNameIsBlank() {
            List<CourseEntity> expectedCourses = Arrays.asList(javaCourse, springCourse, javascriptCourse);
            when(courseRepository.findAll()).thenReturn(expectedCourses);
                
            CourseListResponseDTO result = listCoursesUseCase.execute("   ", null, 1, 10);
            
            assertNotNull(result, "Resultado não deve ser nulo");
            assertNotNull(result.getCourses(), "Lista de cursos não deve ser nula");
            assertEquals(3, result.getCourses().size(), "Deve retornar 3 cursos");
            assertEquals(3, result.getTotalItems(), "Total deve ser 3");
            assertEquals(2, result.getActive(), "Deve ter 2 cursos ativos");
            assertEquals(1, result.getInactive(), "Deve ter 1 curso inativo");
            verify(courseRepository).findAll();
        }
    }
}
