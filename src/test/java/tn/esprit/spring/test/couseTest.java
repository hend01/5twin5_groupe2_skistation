package tn.esprit.spring.test;


import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.entities.TypeCourse;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.services.CourseServicesImpl;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Test;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class couseTest {
    @InjectMocks
    private CourseServicesImpl courceService;
    @Mock
    private ICourseRepository courseRepository;

    Course c = new Course(1L,1,TypeCourse.INDIVIDUAL,Support.SKI,5.75f,0);
    Course c1 = new Course(2L,0,TypeCourse.INDIVIDUAL,Support.SKI,5.75f,10);

    @BeforeEach
    public void setUp() {
        courseRepository.deleteAll();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Order(1)
    public void testAddCourse() throws Exception  {
        when(courseRepository.save(any(Course.class))).thenReturn(c1);
        Course cource =courceService.addCourse(c1);
        log.info(cource.toString());
        verify(courseRepository, times(1)).save(eq(c1));
        // Verify
        assertEquals(2L, cource.getNumCourse().longValue());
        assertEquals(0, cource.getLevel());

    }
    @Test
    @Order(2)
    public void testAllCourses(){
        when(courseRepository.findAll()).thenReturn(Stream
               .of(c,c1).collect(Collectors.toList()));
        assertEquals(2, courceService.retrieveAllCourses().size());
        log.info(""+courceService.retrieveAllCourses().size());
    }
    @Test
    @Order(3)
    public void testUpdateCourses(){
        Course b=new Course();
        b.setNumCourse(3L);
        b.setTypeCourse(TypeCourse.INDIVIDUAL);
        doReturn(b).when(courseRepository).save(b);
        Course response = courceService.addCourse(b);
        Course updated=new Course();
        updated.setNumCourse(3L);
        updated.setTypeCourse(TypeCourse.COLLECTIVE_ADULT);
        doReturn(updated).when(courseRepository).save(updated);
        Course updatedd= courceService.updateCourse(updated);
        log.info(""+response);
        log.info(""+updated);
        log.info(""+updatedd);
        assertNotNull(b);
        assertNotNull(updated);
        assertEquals(TypeCourse.COLLECTIVE_ADULT, updatedd.getTypeCourse());
    }
    @Test
    @Order(4)
    public void getCourse() {
        doReturn(c).when(courseRepository).save(c);
        Course response = courceService.addCourse(c);
        log.info(""+response);
        when(courseRepository.findById(response.getNumCourse().longValue())).thenReturn(Optional.of(c));
        Course cource =courceService.retrieveCourse(response.getNumCourse().longValue());
        log.info(""+cource);
        assertEquals(1, cource.getLevel());
    }
}
