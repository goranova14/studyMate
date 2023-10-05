package nl.fontys.s3.studysmate.studymate.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.GetCourseUseCase;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.Course;
import nl.fontys.s3.studysmate.studymate.persistence.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetCourseIdUseCaseImpl implements GetCourseUseCase {
    private CourseRepository courseRepository;


    @Override
    public Optional<Course> getCourse(long id) {
        Optional<Course> optionalCourse = courseRepository.findById(id).map(CourseConverter::convert);

        if (optionalCourse.isEmpty()) {
            throw new InvalidEntityException("Invalid course");

        } else {
            return optionalCourse;
        }
    }
}
