package nl.fontys.s3.studysmate.studymate.business;

import nl.fontys.s3.studysmate.studymate.domain.UpdateGradeRequest;

public interface UpdateGradeUseCase {
    void updateGrade(UpdateGradeRequest request);
}
