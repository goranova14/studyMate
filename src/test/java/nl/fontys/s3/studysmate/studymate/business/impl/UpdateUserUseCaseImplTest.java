package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.configuration.password.PasswordValidator;
import nl.fontys.s3.studysmate.studymate.domain.AccessToken;
import nl.fontys.s3.studysmate.studymate.domain.UpdateStudentRequest;
import nl.fontys.s3.studysmate.studymate.persistence.UserRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateUserUseCaseImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AccessToken accessToken;
    @InjectMocks
    private UpdateUserUseCaseImpl updateUserUseCase;

    @Test
    void updateStudentPositive() {
        UpdateStudentRequest request = UpdateStudentRequest.builder()
                .address("Address").pcn(1L).firstName("S").lastName("S")            .password("encoded123wW#")
                .email("a@abv.g").build();

        UserEntity userEntity = UserEntity.builder().address("Address").pcn(1L).firstName("S").lastName("S").password("123").email("a@abv.g").build();
        when(accessToken.getUserId()).thenReturn(1l);

        when(passwordEncoder.encode(request.getPassword())).thenReturn("123");

        when(userRepository.findByPcn(request.getPcn())).thenReturn(Optional.of(userEntity));

        when(userRepository.findByEmail(userEntity.getEmail())).thenReturn(Optional.empty());

        when(userRepository.save(userEntity)).thenReturn(userEntity);

        updateUserUseCase.updateStudent(request);
        verify(accessToken).getUserId();

        verify(userRepository).findByEmail(userEntity.getEmail());

        verify(userRepository).findByPcn(userEntity.getPcn());
        verify(userRepository).save(userEntity);

    }

    @Test
    void UpdateStudentNegativeAuthentication() {
        UpdateStudentRequest request = UpdateStudentRequest.builder().address("Address").pcn(1L).firstName("S").lastName("S").password("123").email("a@abv.g").build();
        when(accessToken.getUserId()).thenReturn(12l);
        assertThrows(InvalidEntityException.class, () -> updateUserUseCase.updateStudent(request));
        verify(accessToken).getUserId();
    }

    @Test
    void UpdateStudentThrowsExceptionWhenFindByEmailUserExists() {
        UpdateStudentRequest request = UpdateStudentRequest.builder().address("Address").pcn(1L).firstName("S").lastName("S").password("123").email("a@abv.g").build();
        UserEntity foundStudent = UserEntity.builder()
                .address("Address").pcn(2L).firstName("S").lastName("S").password("123").email("a@abv.g").build();
        when(accessToken.getUserId()).thenReturn(1l);
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(foundStudent));
        assertThrows(InvalidEntityException.class, () -> updateUserUseCase.updateStudent(request));
        verify(accessToken).getUserId();
        verify(userRepository).findByEmail(request.getEmail());
    } @Test
    void UpdateStudentThrowsExceptionWhenFindByPcnUserIsNotPresent() {
        UpdateStudentRequest request = UpdateStudentRequest.builder().address("Address").pcn(1L).firstName("S").lastName("S")                .password("encoded123wW#")
                .email("a@abv.g").build();

        UserEntity userEntity = UserEntity.builder()
                .address("Address").pcn(1L).firstName("S").lastName("S").password("123").email("a@abv.g").build();
       when(accessToken.getUserId()).thenReturn(1l);

       when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(userEntity));
        when(userRepository.findByPcn(request.getPcn())).thenReturn(Optional.empty());
        assertThrows(InvalidEntityException.class, () -> updateUserUseCase.updateStudent(request));
        verify(accessToken).getUserId();
        verify(userRepository).findByEmail(request.getEmail());
        verify(userRepository).findByPcn(request.getPcn());
    }
}