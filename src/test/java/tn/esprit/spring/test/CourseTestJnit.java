package tn.esprit.spring.test;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.services.ICourseServices;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CourseTestJnit {


    @Autowired
    private  ICourseServices courseServices;


    @Test
    public void testAddCourse(){
        Course course = new Course().builder()
                .level(12)
                .build();
        log.info("Adding new activity");
        Course savedCourse = courseServices.addCourse(course);
        log.info("asserting saved activity id is not null");
        Assertions.assertNotNull(savedCourse.getNumCourse());

    }

    @Test
    public void testRetrieveAllCourses(){
        Course course = new Course().builder()
                .level(12)
                .build();
        Course cours2 = new Course().builder()
                .level(13)
                .build();
        log.info("Adding new activity");
        Course course1 = courseServices.addCourse(course);
        Course course2 = courseServices.addCourse(cours2);
        log.info("retrieve all activity sectors ..");
        List<Course> courseLis = courseServices.retrieveAllCourses();
        log.info("Assert that the activity list is not empty");
        Assertions.assertNotEquals(courseLis.size(), 0);
        log.info("delete the tested activities");

    }

    @Test
    public void testRetrieveAllCourseById(){
        Course course = new Course().builder()
                .numCourse(1L)
                .level(12)
                .build();

        log.info("Adding new activity");
        Course course1 = courseServices.addCourse(course);

        log.info("retrieve all activity sectors ..");
        Course courseDetail = courseServices.retrieveCourse(course.getNumCourse());
        log.info("Assert that the activity list is not empty");
        Assertions.assertNotNull(courseDetail.getNumCourse());

    }


}
