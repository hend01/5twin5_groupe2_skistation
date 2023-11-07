package tn.esprit.SkiStationProject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.SkiStationProject.entities.Course;
import tn.esprit.SkiStationProject.entities.Instructor;
import tn.esprit.SkiStationProject.entities.enums.Support;
import tn.esprit.SkiStationProject.entities.enums.TypeCourse;
import tn.esprit.SkiStationProject.repositories.InstructorRepository;
import tn.esprit.SkiStationProject.services.InstructorServicesImpl;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
 class InstructorServiceTest {
    @Mock
    InstructorRepository instructorRepository;
    @InjectMocks
    InstructorServicesImpl instructorServices;
    @Test
     void retrieveAllPistesTest(){
        List<Instructor> instructors = instructorRepository.findAll();
        List<Instructor> expectedInstructors=instructorServices.retrieveAllInstructors();

        assertEquals(instructors, expectedInstructors);

    }
   @Test
   void addInstructorTest() {
      // Créez un nouvel instructeur factice à ajouter
      Instructor newInstructor = new Instructor();
      newInstructor.setFirstName("John");
      newInstructor.setLastName("Doe");
      newInstructor.setDateOfHire(LocalDate.now()); // Date d'embauche actuelle
      Set<Course> courses = new HashSet<>();

      Course course1 = new Course();
      course1.setLevel(2);
      course1.setTypeCourse(TypeCourse.COLLECTIVE_ADULT);

      Course course2 = new Course();
      course2.setLevel(3);
      course2.setTypeCourse(TypeCourse.INDIVIDUAL);

      courses.add(course1);
      courses.add(course2);

      newInstructor.setCourses(courses);


      // Configurez le comportement simulé de repository.save() pour renvoyer le nouvel instructeur
      when(instructorRepository.save(newInstructor)).thenReturn(newInstructor);

      // Appelez la méthode d'ajout de service
      Instructor addedInstructor = instructorServices.addInstructor(newInstructor);

      // Vérifiez si l'instructeur renvoyé par le service correspond à celui que vous avez ajouté
      assertNotNull(addedInstructor);
      assertEquals(newInstructor.getFirstName(), addedInstructor.getFirstName());
      assertEquals(newInstructor.getLastName(), addedInstructor.getLastName());
      assertEquals(newInstructor.getDateOfHire(), addedInstructor.getDateOfHire());
      assertEquals(newInstructor.getCourses(), addedInstructor.getCourses());
   }

}
