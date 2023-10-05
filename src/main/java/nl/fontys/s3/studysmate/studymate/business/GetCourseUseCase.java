package nl.fontys.s3.studysmate.studymate.business;

import nl.fontys.s3.studysmate.studymate.domain.Course;

import java.util.Optional;

public interface GetCourseUseCase {
    Optional<Course> getCourse(long id);
}
