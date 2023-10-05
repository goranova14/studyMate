package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.business.AccessTokenEncoder;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.*;
import nl.fontys.s3.studysmate.studymate.persistence.UserRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserRoleEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private AccessTokenEncoder accessTokenEncoder;
    @InjectMocks
    private LoginUseCaseImpl loginUseCase;


    @Test
    void loginNegativeThrowExceptionInvalidUser() {
        String encodedPassword = new BCryptPasswordEncoder().encode("wrongPassword");

        UserEntity userEntity = UserEntity.builder()
                .email("ab@abv.bg")
                .password("123")
                .build();

        LoginRequest loginRequest = LoginRequest.builder()
                .email("ab@abv.bg")
                .password(encodedPassword)
                .build();

        when(userRepository.findByEmail("ab@abv.bg")).thenReturn(Optional.of(userEntity));
        assertThrows(InvalidEntityException.class, () -> loginUseCase.login(loginRequest));
        verify(userRepository).findByEmail("ab@abv.bg");
    }
    @Test
    void findByEmailNegative(){
        String encodedPassword = new BCryptPasswordEncoder().encode("wrongPassword");

        LoginRequest loginRequest = LoginRequest.builder()
                .email("ab@abv.bg")
                .password(encodedPassword)
                .build();

        when(userRepository.findByEmail("ab@abv.bg")).thenReturn(Optional.empty());
        assertThrows(InvalidEntityException.class, () -> loginUseCase.login(loginRequest));
        verify(userRepository).findByEmail("ab@abv.bg");


    }

    @Test
    void LoginPositive() {
        String encodedPassword = new BCryptPasswordEncoder().encode("123");
        LoginRequest loginRequest = LoginRequest.builder()
                .email("ab@abv.bg")
                .password("123")
                .build();
        UserEntity userEntity = UserEntity.builder()
                .pcn(1l)

                .email("ab@abv.bg")
                .password(encodedPassword)
                .build();
        userEntity.setUserRoles(Set.of(
                UserRoleEntity.builder()
                        .user(userEntity)
                        .role(Roles.STUDENT)
                        .build()
        ));
        when(userRepository.findByEmail("ab@abv.bg")).thenReturn(Optional.of(userEntity));
//        when(passwordEncoder.matches(encodedPassword, userEntity.getPassword())).thenReturn(true);
        List<String> roles = userEntity.getUserRoles().stream()
                .map(userRole -> userRole.getRole().toString())
                .toList();
        when(accessTokenEncoder.encode(AccessToken.builder().roles(roles).subject(userEntity.getEmail()).userId(userEntity.getPcn()).build())).thenReturn("access");
        LoginResponse response = loginUseCase.login(loginRequest);

        assertEquals("access", response.getAccessToken());
        verify(userRepository).findByEmail("ab@abv.bg");
//        verify(passwordEncoder).matches(encodedPassword,userEntity.getPassword());
        verify(accessTokenEncoder).encode(AccessToken.builder().roles(roles).subject(userEntity.getEmail()).userId(userEntity.getPcn()).build());
    }
}