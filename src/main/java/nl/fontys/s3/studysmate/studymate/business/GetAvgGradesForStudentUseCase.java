package nl.fontys.s3.studysmate.studymate.business;

import nl.fontys.s3.studysmate.studymate.domain.GetAvgGradesResponse;

public interface GetAvgGradesForStudentUseCase {
    GetAvgGradesResponse getAvgGrade(Long id);
}
