package nl.fontys.s3.studysmate.studymate.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.GetUserEmailUseCase;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.User;
import nl.fontys.s3.studysmate.studymate.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@AllArgsConstructor
public class GetUserEMailUseCaseImpl implements GetUserEmailUseCase {
    private UserRepository userRepository;

    @Override
    public Optional<User> getStudent(String email) {
        Optional<User> foundStudent = userRepository.findByEmail(email).map(UserConverter::convert);

        if (foundStudent.isEmpty()) {
            throw new InvalidEntityException("Invalid student");

        } else {
            return foundStudent;
        }
    }
}
