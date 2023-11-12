package tn.esprit.SkiStationProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.SkiStationProject.entities.Course;
import tn.esprit.SkiStationProject.entities.Instructor;
import tn.esprit.SkiStationProject.entities.Registration;
import tn.esprit.SkiStationProject.entities.Skier;
import tn.esprit.SkiStationProject.entities.enums.Support;
import tn.esprit.SkiStationProject.repositories.CourseRepository;
import tn.esprit.SkiStationProject.repositories.InstructorRepository;
import tn.esprit.SkiStationProject.repositories.RegistrationRepository;
import tn.esprit.SkiStationProject.repositories.SkierRepository;
import tn.esprit.SkiStationProject.services.RegistrationServicesImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class RegistrationServicesMockitoTest {

    @InjectMocks
    private RegistrationServicesImpl registrationServices;

    @Mock
    private SkierRepository skierRepository;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private InstructorRepository instructorRepository;
    @Mock
    private RegistrationRepository registrationRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addRegistrationAndAssignToSkier() {
        // Arrange
        Registration registration = new Registration();
        Skier skier = new Skier();
        when(skierRepository.findById(anyLong())).thenReturn(Optional.of(skier));
        when(registrationRepository.save(registration)).thenReturn(registration);

        // Act
        Registration result = registrationServices.addRegistrationAndAssignToSkier(registration, 1L);

        // Assert
        verify(skierRepository, times(1)).findById(1L);
        verify(registrationRepository, times(1)).save(registration);
        assertEquals(result, registration);
    }

    @Test
    void assignRegistrationToCourse() {
        // Arrange
        Registration registration = new Registration();
        when(registrationRepository.findById(anyLong())).thenReturn(Optional.of(registration));
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(new Course()));
        when(registrationRepository.save(registration)).thenReturn(registration);

        // Act
        Registration result = registrationServices.assignRegistrationToCourse(1L, 2L);

        // Assert
        verify(registrationRepository, times(1)).findById(1L);
        verify(courseRepository, times(1)).findById(2L);
        verify(registrationRepository, times(1)).save(registration);
        assertEquals(result, registration);
    }

    @Test
    void addRegistrationAndAssignToSkierAndCourse() {
        // Arrange
        Registration registration = new Registration();
        Skier skier = new Skier();
        Course course = new Course();

        when(skierRepository.findById(anyLong())).thenReturn(Optional.of(skier));
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
        when(registrationRepository.countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(anyInt(), anyLong(), anyLong())).thenReturn(0L);

        // Act
        Registration result = registrationServices.addRegistrationAndAssignToSkierAndCourse(registration, 1L, 2L);

        // Assert
        verify(skierRepository, times(1)).findById(1L);
        verify(courseRepository, times(1)).findById(2L);
        verify(registrationRepository, times(1)).countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(anyInt(), 1L, 2L);
        verify(registrationRepository, times(1)).save(registration);
        assertEquals(result, registration);
    }

    @Test
    void numWeeksCourseOfInstructorBySupport() {
        // Arrange
        Long instructorId = 1L;
        Support support = Support.SKI; // Assuming Support is an enum

        when(instructorRepository.findById(instructorId)).thenReturn(Optional.of(new Instructor())); // Assuming Instructor object should be returned
        when(registrationRepository.numWeeksCourseOfInstructorBySupport(instructorId, support)).thenReturn(Arrays.asList(1, 2, 3));

        // Act
        List<Integer> result = registrationServices.numWeeksCourseOfInstructorBySupport(instructorId, support);

        // Assert
        verify(instructorRepository, times(1)).findById(instructorId);
        verify(registrationRepository, times(1)).numWeeksCourseOfInstructorBySupport(instructorId, support);
        assertEquals(Arrays.asList(1, 2, 3), result);
    }

}
