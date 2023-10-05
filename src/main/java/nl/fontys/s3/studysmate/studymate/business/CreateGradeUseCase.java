package nl.fontys.s3.studysmate.studymate.business;

import nl.fontys.s3.studysmate.studymate.domain.CreateGradeRequest;
import nl.fontys.s3.studysmate.studymate.domain.CreateGradeResponse;

public interface CreateGradeUseCase {
    CreateGradeResponse createGrade(CreateGradeRequest request);
}
