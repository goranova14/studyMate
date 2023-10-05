package nl.fontys.s3.studysmate.studymate.business;

import nl.fontys.s3.studysmate.studymate.domain.GetAllGradesForUserResponse;

public interface GetStudentsGradesUseCase {
    GetAllGradesForUserResponse getStudents(int requestGrade);

}
