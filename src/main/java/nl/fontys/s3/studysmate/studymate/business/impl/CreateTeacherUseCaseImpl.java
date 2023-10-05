package nl.fontys.s3.studysmate.studymate.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.CreateTeacherUseCase;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidPasswordException;
import nl.fontys.s3.studysmate.studymate.configuration.password.PasswordValidator;
import nl.fontys.s3.studysmate.studymate.domain.*;
import nl.fontys.s3.studysmate.studymate.persistence.UserRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserRoleEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Set;

@Service
@AllArgsConstructor
public class CreateTeacherUseCaseImpl implements CreateTeacherUseCase {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public CreateTeacherResponse createTeacher(CreateTeacherRequest request) {
        try {
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new InvalidEntityException("Email already exists");
            }
            if (!PasswordValidator.isValid(request.getPassword())) {
                throw new InvalidPasswordException("Invalid password, must contain 8 character,one upper case, one lower, one special symbol(@#$%^&+=) ");
            }
            UserEntity newStudent = createNewStudent(request);

            return CreateTeacherResponse.builder()
                    .pcn(newStudent.getPcn())
                    .build();
        } catch (InvalidEntityException invalidEntityException) {
            throw new InvalidEntityException("Email already exists");
        }
        catch (InvalidPasswordException invalidEntityException) {
            throw new InvalidPasswordException("Invalid password, must contain 8 character,one upper case, one lower, one special symbol(@#$%^&+=), one digit ");
        }
    }

    private UserEntity createNewStudent(CreateTeacherRequest request) {
    String encodedPassword=passwordEncoder.encode(request.getPassword());
        UserEntity newStudent = UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .address(request.getAddress())
                .pcn(request.getPcn())
                .password(encodedPassword)
                .email(request.getEmail())
                .build();
        newStudent.setUserRoles(Set.of(
                UserRoleEntity.builder()
                        .user(newStudent)
                        .role(Roles.TEACHER)
                        .build()
        ));
        return userRepository.save(newStudent);
    }
}
