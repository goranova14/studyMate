package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.AccessToken;
import nl.fontys.s3.studysmate.studymate.domain.Roles;
import nl.fontys.s3.studysmate.studymate.persistence.UserRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteUserUseCaseImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private AccessToken accessToken;

    @InjectMocks
    private DeleteUserUseCaseImpl deleteStudentUseCase;
    @InjectMocks//injects the mock object into the class
    private CreateUserUseCaseImpl createStudentUseCase;


    @Test
    void deleteStudent() {
        UserEntity student = UserEntity.builder().firstName("Denitsa").lastName("Goranova").pcn(1L).password("123").address("Street 1").build();

        when(accessToken.hasRole(Roles.TEACHER.name())).thenReturn(true);
        when(userRepository.existsByPcn(student.getPcn())).thenReturn(true);

        deleteStudentUseCase.deleteStudent(student.getPcn());
        verify(userRepository).existsByPcn(student.getPcn());
        verify(accessToken).hasRole(Roles.TEACHER.name());
    }

    @Test
    void deleteStudentShouldReturnVoid() {


        UserEntity student = UserEntity.builder().firstName("Denitsa").lastName("Goranova").pcn(1L).password("123").address("Street 1").build();
        when(userRepository.existsByPcn(student.getPcn())).thenReturn(true);
        when(accessToken.getUserId()).thenReturn(1l);
        deleteStudentUseCase.deleteStudent(student.getPcn());
        verify(userRepository).existsByPcn(student.getPcn());
        when(userRepository.existsByPcn(student.getPcn())).thenReturn(false);
        verify(userRepository).existsByPcn(student.getPcn());
        verify(accessToken).getUserId();
        assertFalse(userRepository.existsByPcn(student.getPcn()));

    }

    @Test
    void deleteStudentShouldThrowAnInvalidStudentException() {


        UserEntity student = UserEntity.builder().firstName("Denitsa").lastName("Goranova").pcn(1L).password("Bulgaria").address("Street 1").build();
        when(accessToken.getUserId()).thenReturn(1l);

        when(userRepository.existsByPcn(student.getPcn())).thenReturn(false);
        assertThrows(InvalidEntityException.class, () -> deleteStudentUseCase.deleteStudent(student.getPcn()));//,"400 BAD_REQUEST No student found with pcn: "+student.getPcn());
        verify(userRepository).existsByPcn(student.getPcn());
        verify(accessToken).getUserId();

    }

    @Test
    void deleteStudentShouldThrowAnInvalidStudentExceptionWhenAccessTokenNotValid() {
        UserEntity student = UserEntity.builder().firstName("Denitsa").lastName("Goranova").pcn(1L).password("Bulgaria").address("Street 1").build();
        when(accessToken.getUserId()).thenReturn(2l);

        assertThrows(InvalidEntityException.class, () -> deleteStudentUseCase.deleteStudent(student.getPcn()));//,"400 BAD_REQUEST No student found with pcn: "+student.getPcn());
        verify(accessToken).getUserId();

    }
}