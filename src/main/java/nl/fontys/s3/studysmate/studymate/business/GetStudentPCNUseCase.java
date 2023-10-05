package nl.fontys.s3.studysmate.studymate.business;

import nl.fontys.s3.studysmate.studymate.domain.User;

import java.util.Optional;

public interface GetStudentPCNUseCase {
    Optional<User> getStudent(long studentPcn);
}
