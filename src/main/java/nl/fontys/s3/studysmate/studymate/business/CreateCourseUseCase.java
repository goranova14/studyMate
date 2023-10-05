package nl.fontys.s3.studysmate.studymate.business;

import nl.fontys.s3.studysmate.studymate.domain.CreateCourseRequest;
import nl.fontys.s3.studysmate.studymate.domain.CreateCourseResponse;

public interface CreateCourseUseCase {
    CreateCourseResponse createCourse(CreateCourseRequest request);

}

