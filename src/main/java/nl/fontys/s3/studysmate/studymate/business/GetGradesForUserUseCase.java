package nl.fontys.s3.studysmate.studymate.business;

import nl.fontys.s3.studysmate.studymate.domain.GetAllGradesForUserResponse;

public interface GetGradesForUserUseCase {
    GetAllGradesForUserResponse getGrades(Long studentPcn);
}
