package tn.esprit.SkiStationProject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.SkiStationProject.entities.Instructor;
import tn.esprit.SkiStationProject.repositories.InstructorRepository;
import tn.esprit.SkiStationProject.services.InstructorServicesImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
 class InstructorServiceTest {
    @Mock
    InstructorRepository instructorRepository;
    @InjectMocks
    InstructorServicesImpl instructorServices;
    @Test
     void retrieveAllPistesTest(){
        List<Instructor> pp = instructorRepository.findAll();
        List<Instructor> expectedP=instructorServices.retrieveAllInstructors();


        assertEquals(pp, expectedP);

    }

}
