package nl.fontys.s3.studysmate.studymate.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.business.CreateUserUseCase;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidPasswordException;
import nl.fontys.s3.studysmate.studymate.configuration.password.PasswordValidator;
import nl.fontys.s3.studysmate.studymate.domain.CreateStudentRequest;
import nl.fontys.s3.studysmate.studymate.domain.CreateStudentResponse;
import nl.fontys.s3.studysmate.studymate.domain.Roles;
import nl.fontys.s3.studysmate.studymate.persistence.UserRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserRoleEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public CreateStudentResponse createStudent(CreateStudentRequest request) {
        try {
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new InvalidEntityException("Email already exists");
            }
            if (!PasswordValidator.isValid(request.getPassword())) {
                throw new InvalidPasswordException("Invalid password, must contain 8 character,one upper case, one lower, one special symbol(@#$%^&+=) and one number");
            }
            UserEntity newStudent = createNewStudent(request);

            return CreateStudentResponse.builder()
                    .pcn(newStudent.getPcn())
                    .build();
        } catch (InvalidEntityException invalidEntityException) {
            throw new InvalidEntityException("Email already exists");
        }catch (InvalidPasswordException invalidEntityException) {
            throw new InvalidPasswordException("Invalid password, must contain 8 character,one upper case, one lower, one special symbol(@#$%^&+=), one digit ");
        }
    }

    private UserEntity createNewStudent(CreateStudentRequest request) {
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
                        .role(Roles.STUDENT)
                        .build()
        ));
        return userRepository.save(newStudent);
    }
}
