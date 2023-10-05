package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.AccessToken;
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

@ExtendWith(MockitoExtension.class)
class GetUserPCNUseCaseImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private AccessToken accessToken;

    @InjectMocks
    private GetUserPCNUseCaseImpl getStudentUseCase;

    @Test
    void testShouldReturnAStudentEntity() {
        Optional<UserEntity> studentEntity = Optional.of(UserEntity.builder()
                .pcn(1L)
                .lastName("Goranova")
                .firstName("Denitsa")
                .userRoles(Set.of(
                        UserRoleEntity.builder()
                                .role(Roles.STUDENT)
                                .build()
                ))
                .address("Niuewe")
                .password("Netherlands")
                .build());
        when(accessToken.getUserId()).thenReturn(1l);

        when(userRepository.findByPcn(1)).thenReturn(studentEntity);
        Optional<User> foundStudent = getStudentUseCase.getStudent(1);
        assertTrue(foundStudent.isPresent());
        assertEquals( 1,foundStudent.get().getPcn());
        assertEquals( "Netherlands",foundStudent.get().getPassword());
        assertEquals( "Niuewe",foundStudent.get().getAddress());
        assertEquals("Goranova",foundStudent.get().getLastName());
        assertEquals( "Denitsa",foundStudent.get().getFirstName());
        verify(accessToken).getUserId();

        verify(userRepository).findByPcn(1);
    }


    @Test
    void shouldThrowAnExceptionInvalidStudent() {
        when(accessToken.getUserId()).thenReturn(1l);

        assertThrows(InvalidEntityException.class, ()
                -> getStudentUseCase.getStudent(1));
        verify(accessToken).getUserId();

    }   @Test
    void shouldThrowAnExceptionInvalidStudentWhenAccessTokenNotValid() {
        when(accessToken.getUserId()).thenReturn(2l);

        assertThrows(InvalidEntityException.class, ()
                -> getStudentUseCase.getStudent(1));
        verify(accessToken).getUserId();

    }

}
