package nl.fontys.s3.studysmate.studymate.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.UpdateUserUseCase;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidPasswordException;
import nl.fontys.s3.studysmate.studymate.configuration.password.PasswordValidator;
import nl.fontys.s3.studysmate.studymate.domain.AccessToken;
import nl.fontys.s3.studysmate.studymate.domain.UpdateStudentRequest;
import nl.fontys.s3.studysmate.studymate.persistence.UserRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private AccessToken requestAccessToken;


    @Override
    public void updateStudent(UpdateStudentRequest request) {

        if (requestAccessToken.getUserId() != request.getPcn()) {
            throw new InvalidEntityException("USER_ID_NOT_FROM_LOGGED_IN_USER");
        }
        Optional<UserEntity> foundStudent=userRepository.findByEmail(request.getEmail());
        if (foundStudent.isPresent()&&foundStudent.get().getPcn()!= request.getPcn()) {
            throw new InvalidEntityException("Email already exists");
        }
        if (!PasswordValidator.isValid(request.getPassword())) {
            throw new InvalidPasswordException("Invalid password, must contain 8 character,one upper case, one lower, one special symbol(@#$%^&+=) ");
        }
        Optional<UserEntity> student = userRepository.findByPcn(request.getPcn());

        if (student.isPresent()) {
            String encodedPassword=passwordEncoder.encode(request.getPassword());

            UserEntity newStudent = student.get();
            newStudent.setFirstName(request.getFirstName());
            newStudent.setLastName(request.getLastName());
            newStudent.setAddress(request.getAddress());
            newStudent.setPassword(encodedPassword);
            newStudent.setEmail(request.getEmail());
            newStudent.setUserRoles(student.get().getUserRoles());
            userRepository.save(newStudent);
        } else {
            throw new InvalidEntityException("INVALID_STUDENT_PCN");

        }

    }
}
