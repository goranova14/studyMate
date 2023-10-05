package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidPasswordException;
import nl.fontys.s3.studysmate.studymate.configuration.password.PasswordValidator;
import nl.fontys.s3.studysmate.studymate.domain.*;
import nl.fontys.s3.studysmate.studymate.persistence.UserRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserRoleEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)//allows to include the Mockito framework
class CreateTeacherUseCaseImplTest {
    @Mock//Mockito-basically create a mock object of the class
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks//injects the mock object into the class
    private CreateTeacherUseCaseImpl createTeacherUseCase;

    @Test
    void createUserPositive() {
        CreateTeacherRequest request = CreateTeacherRequest.builder()
                .firstName("Deni")
                .lastName("Goranova")
                .email("d.goranova@abv.bg")
                .address("Niuewe")
                .pcn(1L)
                .password("encoded123wW#")
                .build();

        UserEntity userEntity = UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .address(request.getAddress())
                .pcn(request.getPcn())
                .email(request.getEmail())
                .userRoles(Set.of(
                        UserRoleEntity.builder()
                                .role(Roles.TEACHER)
                                .build()
                ))
                .password("encoded123")
                .build();

        when(passwordEncoder.encode(request.getPassword())).thenReturn("encoded123");
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        CreateTeacherResponse response = createTeacherUseCase.createTeacher(request);
        verify(passwordEncoder).encode(request.getPassword());
        verify(userRepository).findByEmail(request.getEmail());
        verify(userRepository).save(userEntity);
        assertEquals(userEntity.getPcn(), response.getPcn());
    }

    @Test
    void createUserNegativeShouldThrowException() {
        CreateTeacherRequest request = CreateTeacherRequest.builder()
                .firstName("Deni")
                .lastName("Goranova")
                .email("d.goranova@abv.bg")
                .address("Niuewe")
                .pcn(1L)
                .password("encoded123")
//                .role(Roles.STUDENT)
                .build();

        UserEntity userEntity = UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .address(request.getAddress())
                .pcn(request.getPcn())
                .email(request.getEmail())
                .userRoles(Set.of(
                        UserRoleEntity.builder()
                                .role(Roles.TEACHER)
                                .build()
                ))
                .password("encoded123")
                .build();

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(userEntity));
        assertThrows(InvalidEntityException.class, () -> createTeacherUseCase.createTeacher(request));

        verify(userRepository).findByEmail(request.getEmail());


    }@Test
    void createUseThrowsExceptionPassword() {
        CreateTeacherRequest request = CreateTeacherRequest.builder()
                .firstName("Deni")
                .lastName("Goranova")
                .email("d.goranova@abv.bg")
                .address("Niuewe")
                .pcn(1L)
                .password("d")
                .build();

        assertThrows(InvalidPasswordException.class, () -> createTeacherUseCase.createTeacher(request));

        verify(userRepository).findByEmail(request.getEmail());


    }
}
