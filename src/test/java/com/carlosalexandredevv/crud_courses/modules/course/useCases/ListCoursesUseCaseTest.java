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
            
            List<CourseEntity> result = listCoursesUseCase.execute(null, null);
            
            assertNotNull(result, "Resultado não deve ser nulo");
            assertEquals(3, result.size(), "Deve retornar 3 cursos");
            assertTrue(result.containsAll(expectedCourses), "Deve conter todos os cursos esperados");
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
            
            List<CourseEntity> result = listCoursesUseCase.execute(nameFilter, null);
            
            assertNotNull(result, "Resultado não deve ser nulo");
            assertEquals(1, result.size(), "Deve retornar 1 curso");
            assertEquals("Java Básico", result.get(0).getName(), "Deve retornar o curso Java Básico");
            verify(courseRepository).findByNameContainingIgnoreCase(nameFilter);
            verify(courseRepository, never()).findAll();
        }
        
        @Test
        @DisplayName("deve retornar lista vazia quando não encontrar cursos")
        void shouldReturnEmptyListWhenNoCoursesFound() {
            String nameFilter = "Python";
            when(courseRepository.findByNameContainingIgnoreCase(nameFilter)).thenReturn(Collections.emptyList());
            
            List<CourseEntity> result = listCoursesUseCase.execute(nameFilter, null);
            
            assertNotNull(result, "Resultado não deve ser nulo");
            assertTrue(result.isEmpty(), "Deve retornar lista vazia");
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
            
            List<CourseEntity> result = listCoursesUseCase.execute(null, categoryFilter);
            
            assertNotNull(result, "Resultado não deve ser nulo");
            assertEquals(2, result.size(), "Deve retornar 2 cursos");
            assertTrue(result.stream().allMatch(course -> "Programação".equals(course.getCategory())), 
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
            
            List<CourseEntity> result = listCoursesUseCase.execute(nameFilter, categoryFilter);
            
            assertNotNull(result, "Resultado não deve ser nulo");
            assertEquals(1, result.size(), "Deve retornar 1 curso");
            assertEquals("Java Básico", result.get(0).getName(), "Deve retornar o curso Java Básico");
            assertEquals("Programação", result.get(0).getCategory(), "Deve ser da categoria Programação");
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
            
            List<CourseEntity> result = listCoursesUseCase.execute("", null);
            
            assertNotNull(result, "Resultado não deve ser nulo");
            assertEquals(3, result.size(), "Deve retornar 3 cursos");
            verify(courseRepository).findAll();
        }
        
        @Test
        @DisplayName("deve retornar todos os cursos quando nome é apenas espaços")
        void shouldReturnAllCoursesWhenNameIsBlank() {
            List<CourseEntity> expectedCourses = Arrays.asList(javaCourse, springCourse, javascriptCourse);
            when(courseRepository.findAll()).thenReturn(expectedCourses);
                
            List<CourseEntity> result = listCoursesUseCase.execute("   ", null);
            
            assertNotNull(result, "Resultado não deve ser nulo");
            assertEquals(3, result.size(), "Deve retornar 3 cursos");
            verify(courseRepository).findAll();
        }
    }
}
