package tn.esprit.spring.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.TypeCourse;
import tn.esprit.spring.repositories.ICourseRepository;

import java.util.List;
@AllArgsConstructor
@Service

public class CourseServicesImpl implements ICourseServices {
    private static final Logger logger = LogManager.getLogger(CourseServicesImpl.class);

    private static final String TYPE_LABEL = ", Type: ";

    private ICourseRepository courseRepository;

    @Override
    public List<Course> retrieveAllCourses() {
        logger.info("Appel de la méthode retrieveAllCourses()");
        List<Course> courses = courseRepository.findAll();
        for (Course course : courses) {
            logger.debug("Course ID: " + course.getNumCourse() + ", Level: " + course.getLevel() + TYPE_LABEL + course.getTypeCourse());
        }
        return courses;
    }

    @Override
    public Course addCourse(Course course) {
        logger.info("Ajout d'un nouveau cours");
        logger.debug("Course ID: " + course.getNumCourse() + ", Level: " + course.getLevel() + TYPE_LABEL + course.getTypeCourse());
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Course course) {
        logger.info("Mise à jour du cours avec l'ID : " + course.getNumCourse());
        logger.debug("Course ID: " + course.getNumCourse() + ", Level: " + course.getLevel() + TYPE_LABEL + course.getTypeCourse());
        return courseRepository.save(course);
    }

    @Override
    public Course retrieveCourse(Long numCourse) {
        logger.info("Recherche du cours avec l'ID : " + numCourse);
        Course course = courseRepository.findById(numCourse).orElse(null);
        if (course != null) {
            logger.debug("Course ID: " + course.getNumCourse() + ", Level: " + course.getLevel() + TYPE_LABEL + course.getTypeCourse());
        } else {
            logger.warn("Aucun cours trouvé avec l'ID : " + numCourse);
        }
        return course;
    }
}
