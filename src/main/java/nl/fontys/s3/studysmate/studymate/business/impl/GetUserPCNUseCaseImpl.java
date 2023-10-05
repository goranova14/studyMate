package nl.fontys.s3.studysmate.studymate.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.GetStudentPCNUseCase;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.AccessToken;
import nl.fontys.s3.studysmate.studymate.domain.User;
import nl.fontys.s3.studysmate.studymate.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetUserPCNUseCaseImpl implements GetStudentPCNUseCase {

    private UserRepository userRepository;
    private AccessToken requestAccessToken;

    @Override
    public Optional<User> getStudent(long studentPcn) {
        if (requestAccessToken.getUserId() != studentPcn) {
            throw new InvalidEntityException("STUDENT_ID_NOT_FROM_LOGGED_IN_USER");
        }
        Optional<User> foundStudent = userRepository.findByPcn(studentPcn).map(UserConverter::convert);
        if (foundStudent.isEmpty()) {
            throw new InvalidEntityException("Invalid student");

        } else {
            return foundStudent;
        }
    }
}
