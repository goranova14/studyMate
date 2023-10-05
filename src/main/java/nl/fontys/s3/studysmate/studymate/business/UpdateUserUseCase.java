package nl.fontys.s3.studysmate.studymate.business;

import nl.fontys.s3.studysmate.studymate.domain.UpdateStudentRequest;

public interface UpdateUserUseCase {
    void updateStudent(UpdateStudentRequest request);
}
