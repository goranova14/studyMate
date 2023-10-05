package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.Roles;
import nl.fontys.s3.studysmate.studymate.domain.User;
import nl.fontys.s3.studysmate.studymate.persistence.UserRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserRoleEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)//allows to include the Mockito framework
class GetUserEMailUseCaseImplTest {
    @Mock//Mockito-basically create a mock object of the class
    private UserRepository userRepository;
    @InjectMocks//injects the mock object into the class
    private GetUserEMailUseCaseImpl getUserEMailUseCase;

    @Test
    void getStudent() {
        UserEntity user = UserEntity.builder()
                .firstName("Deni")
                .lastName("Goranova")
                .address("M")
                .userRoles(Set.of(
                        UserRoleEntity.builder()
                                .role(Roles.STUDENT)
                                .build()
                ))
                .email("ab@abv.bg")

                .build();

        when(userRepository.findByEmail("ab@abv.bg")).thenReturn(Optional.of(user));
        Optional<User> foundUser=getUserEMailUseCase.getStudent("ab@abv.bg");
        verify(userRepository).findByEmail("ab@abv.bg");

        assertTrue(foundUser.isPresent());


    }

    @Test
    void getUseNegativeExceptionThrow() {
        when(userRepository.findByEmail("ab@abv.bg")).thenReturn(Optional.empty());
        assertThrows(InvalidEntityException.class,() -> getUserEMailUseCase.getStudent("ab@abv.bg"));

        verify(userRepository).findByEmail("ab@abv.bg");


    }
}