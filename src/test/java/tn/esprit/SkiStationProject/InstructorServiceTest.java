package tn.esprit.SkiStationProject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.SkiStationProject.entities.Course;
import tn.esprit.SkiStationProject.entities.Instructor;

import tn.esprit.SkiStationProject.repositories.InstructorRepository;
import tn.esprit.SkiStationProject.services.InstructorServicesImpl;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import java.util.List;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
 class InstructorServiceTest {
    @Mock
    InstructorRepository instructorRepository;
    @Autowired
    InstructorServicesImpl instructorServices;

    @Test
    void addInstructorTest() {
        // Create a new instructor factice to add
        Set<Course> courses = new HashSet<>();
        Instructor newInstructor = new Instructor("TESST", "Doe", LocalDate.now(), courses);

        // Use lenient stubbing
        lenient().when(instructorRepository.save(newInstructor)).thenReturn(newInstructor);
        assertEquals(newInstructor, instructorServices.addInstructor(newInstructor));
    }
    @Test
    void retrieveAllInstructorsTest() {
        List<Instructor> retrievedInstructors = instructorServices.retrieveAllInstructors();


        // Assert that the retrieved list is not empty and contains the expected number of instructors
        assertNotNull(retrievedInstructors);
        assertTrue(retrievedInstructors.size() > 0);

    }


    @Test
    void testDeleteFirstInstructor() {
        List<Instructor> instructors = instructorServices.retrieveAllInstructors();

        if (instructors.isEmpty()) {
            // Handle the case where no instructors are available for deletion
            fail("No instructors found for deletion");
        } else {
            Long firstInstructorId = instructors.get(0).getId();
            boolean deleted = instructorServices.deleteInstructor(firstInstructorId);

            assertTrue(deleted);
        }
    }

    @Test
    void testUpdateInstructor() {
        List<Instructor> instructors = instructorServices.retrieveAllInstructors();

        if (instructors.isEmpty()) {
            // Handle the case where no instructors are available for updating
            fail("No instructors found for updating");
        } else {
            // Get the first instructor
            Instructor firstInstructor = instructors.get(0);

            // Modify some properties of the instructor
            String updatedFirstName = "UpdatedFirstName";
            String updatedLastName = "UpdatedLastName";
            LocalDate updatedDateOfHire = LocalDate.now().minusYears(1);

            // Set the updated properties
            firstInstructor.setFirstName(updatedFirstName);
            firstInstructor.setLastName(updatedLastName);
            firstInstructor.setDateOfHire(updatedDateOfHire);

            // Perform the update operation
            Instructor updatedInstructor = instructorServices.updateInstructor(firstInstructor);

            // Check if the instructor was updated
            assertNotNull(updatedInstructor);
            assertEquals(updatedFirstName, updatedInstructor.getFirstName());
            assertEquals(updatedLastName, updatedInstructor.getLastName());
            assertEquals(updatedDateOfHire, updatedInstructor.getDateOfHire());
        }
    }



}
