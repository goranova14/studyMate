package nl.fontys.s3.studysmate.studymate.business;


import nl.fontys.s3.studysmate.studymate.domain.CreateTeacherRequest;
import nl.fontys.s3.studysmate.studymate.domain.CreateTeacherResponse;

public interface CreateTeacherUseCase {
    CreateTeacherResponse createTeacher(CreateTeacherRequest request);

}
