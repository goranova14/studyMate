package nl.fontys.s3.studysmate.studymate.business;

import nl.fontys.s3.studysmate.studymate.domain.GetAllStudentsRequest;
import nl.fontys.s3.studysmate.studymate.domain.GetAllStudentsResponse;

public interface GetStudentsUseCase {
    GetAllStudentsResponse getStudents(GetAllStudentsRequest request);
}
