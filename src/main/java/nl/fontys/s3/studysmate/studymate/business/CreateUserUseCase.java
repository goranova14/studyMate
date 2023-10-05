package nl.fontys.s3.studysmate.studymate.business;


import nl.fontys.s3.studysmate.studymate.domain.CreateStudentRequest;
import nl.fontys.s3.studysmate.studymate.domain.CreateStudentResponse;

public interface CreateUserUseCase {
    CreateStudentResponse createStudent(CreateStudentRequest request);

}
