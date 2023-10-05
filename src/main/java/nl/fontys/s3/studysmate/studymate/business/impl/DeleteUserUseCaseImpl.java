package nl.fontys.s3.studysmate.studymate.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.DeleteUserUseCase;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.AccessToken;
import nl.fontys.s3.studysmate.studymate.domain.Roles;
import nl.fontys.s3.studysmate.studymate.persistence.UserRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {

    private final UserRepository userRepository;
    private final AccessToken requestAccessToken;

    @Override
    public void deleteStudent(long studentPcn) {
        if (!requestAccessToken.hasRole(Roles.TEACHER.name()) && (requestAccessToken.getUserId() != studentPcn)) {
            throw new InvalidEntityException("STUDENT_ID_NOT_FROM_LOGGED_IN_USER");
        }
        boolean exists = userRepository.existsByPcn(studentPcn);
        if (!exists) {
            throw new InvalidEntityException("No student found with pcn:" + studentPcn);

        } else {
            this.userRepository.deleteByPcn(studentPcn);

        }


    }
}
