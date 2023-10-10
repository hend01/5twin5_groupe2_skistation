package tn.esprit.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.services.CourseServicesImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CourseServiceTest {

    @Mock
    private ICourseRepository courseRepository;

    @InjectMocks
    private CourseServicesImpl courseServices;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRetrieveAllCourses() {
        // Créez une liste fictive de cours pour simuler la réponse de la base de données
        List<Course> mockCourses = new ArrayList<>();
        mockCourses.add(new Course(1L, "Math", "John Doe"));
        mockCourses.add(new Course(2L, "Science", "Jane Smith"));

        // Configurez le comportement du mock du repository pour renvoyer la liste fictive de cours
        when(courseRepository.findAll()).thenReturn(mockCourses);

        // Appelez la méthode du service à tester
        List<Course> courses = courseServices.retrieveAllCourses();

        // Vérifiez que la liste retournée par le service correspond à la liste fictive
        assertEquals(mockCourses, courses);
    }

    // Ajoutez d'autres tests pour les autres méthodes du service CourseServices
}
