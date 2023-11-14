package tn.esprit.SkiStationProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.SkiStationProject.entities.Course;
import tn.esprit.SkiStationProject.entities.Instructor;

import tn.esprit.SkiStationProject.repositories.CourseRepository;
import tn.esprit.SkiStationProject.repositories.InstructorRepository;
import tn.esprit.SkiStationProject.services.InstructorServicesImpl;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
 class InstructorServiceTest {
    @Mock
    InstructorRepository instructorRepository;
    @Mock
    CourseRepository courseRepository;
    InstructorServicesImpl instructorServices;

    @BeforeEach
    void setUp() {
        instructorServices = new InstructorServicesImpl(instructorRepository,courseRepository);
    }
    @Test
    void addInstructorTest() {

        // Créer un nouvel instructeur factice à ajouter
        Set<Course> courses = new HashSet<>();
        Instructor newInstructor = new Instructor("TESST", "Doe", LocalDate.now(), courses);

        // Utiliser le stubbing de Mockito
        when(instructorRepository.save(newInstructor)).thenReturn(newInstructor);

        // Appeler la méthode du service
        Instructor addedInstructor = instructorServices.addInstructor(newInstructor);

        // Vérifier que la méthode du repository a été appelée avec le nouvel instructeur
        verify(instructorRepository).save(newInstructor);

        // Vérifier que l'instructeur retourné par le service est le même que celui retourné par le repository
        assertEquals(newInstructor, addedInstructor);
    }

    @Test
    void retrieveAllInstructorsTest() {
        // Créer une liste factice d'instructeurs
        List<Instructor> fakeInstructors = Arrays.asList(
                new Instructor("John", "Doe", LocalDate.now(), new HashSet<>()),
                new Instructor("Jane", "Smith", LocalDate.now(), new HashSet<>())
        );

        // Utiliser le stubbing de Mockito pour simuler le comportement du repository
        when(instructorRepository.findAll()).thenReturn(fakeInstructors);

        // Appeler la méthode du service
        List<Instructor> retrievedInstructors = instructorServices.retrieveAllInstructors();

        // Vérifier que la méthode du repository a été appelée
        verify(instructorRepository).findAll();

        // Vérifier que la liste retournée par le service correspond à la liste factice
        assertEquals(fakeInstructors, retrievedInstructors);
    }

    @Test
    void testUpdateInstructor() {
        // Créer une liste factice d'instructeurs
        List<Instructor> fakeInstructors = Arrays.asList(
                new Instructor("John", "Doe", LocalDate.now(), new HashSet<>())
        );

        // Utiliser le stubbing de Mockito pour simuler le comportement du repository
        lenient().when(instructorRepository.findAll()).thenReturn(fakeInstructors);

        // Récupérer le premier instructeur de la liste factice
        Instructor firstInstructor = fakeInstructors.get(0);

        // Modifier quelques propriétés de l'instructeur
        String updatedFirstName = "UpdatedFirstName";
        String updatedLastName = "UpdatedLastName";
        LocalDate updatedDateOfHire = LocalDate.now().minusYears(1);

        // Définir les propriétés mises à jour
        firstInstructor.setFirstName(updatedFirstName);
        firstInstructor.setLastName(updatedLastName);
        firstInstructor.setDateOfHire(updatedDateOfHire);

        // Utiliser le stubbing de Mockito pour simuler le comportement du repository lors de la mise à jour
        lenient().when(instructorRepository.save(any(Instructor.class))).thenReturn(firstInstructor);

        // Appeler la méthode de mise à jour du service
        Instructor updatedInstructor = instructorServices.updateInstructor(firstInstructor);

        // Vérifier que la méthode du repository a été appelée avec le premier instructeur
        verify(instructorRepository).save(firstInstructor);

        // Vérifier que l'instructeur retourné par le service est le même que celui retourné par le repository
        assertEquals(updatedFirstName, updatedInstructor.getFirstName());
        assertEquals(updatedLastName, updatedInstructor.getLastName());
        assertEquals(updatedDateOfHire, updatedInstructor.getDateOfHire());
    }


    @Test
    void testDeleteInstructor() {
        // Créer une liste factice d'instructeurs
        List<Instructor> fakeInstructors = Arrays.asList(
                new Instructor("John", "Doe", LocalDate.now(), new HashSet<>())
        );

        // Utiliser le stubbing de Mockito pour simuler le comportement du repository
        lenient().when(instructorRepository.findAll()).thenReturn(fakeInstructors);

        // Récupérer l'ID du premier instructeur de la liste factice
        Long firstInstructorId = fakeInstructors.get(0).getId();

        // Utiliser le stubbing de Mockito pour simuler le comportement du repository lors de la suppression
        lenient().when(instructorRepository.existsById(firstInstructorId)).thenReturn(true);
        when(instructorRepository.findById(firstInstructorId)).thenReturn(Optional.of(fakeInstructors.get(0)));

        // Appeler la méthode de suppression du service
        boolean deleted = instructorServices.deleteInstructor(firstInstructorId);

        // Vérifier que la méthode du repository a été appelée avec l'instructeur à supprimer
        verify(instructorRepository).delete(fakeInstructors.get(0));

        // Vérifier que la méthode de suppression a renvoyé true
        assertTrue(deleted);
    }






}
