package nl.fontys.s3.studysmate.studymate.business;

import nl.fontys.s3.studysmate.studymate.domain.GetAllCoursesRequest;
import nl.fontys.s3.studysmate.studymate.domain.GetAllCoursesResponse;

public interface GetCoursesUseCase {
    GetAllCoursesResponse getCourses(GetAllCoursesRequest request);
}
