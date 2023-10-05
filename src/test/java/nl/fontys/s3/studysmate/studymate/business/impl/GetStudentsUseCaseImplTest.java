package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.GetAllStudentsRequest;
import nl.fontys.s3.studysmate.studymate.domain.GetAllStudentsResponse;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetStudentsUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetStudentsUseCaseImpl getStudentsUseCase;

    @Test
    void getStudentsShouldReturnAllStudents() {
        UserEntity userEntity1 = UserEntity.builder()
                .firstName("Denitsa")
                .lastName("Goranova")
                .address("Street1")
                .password("Bulgaria")
                .userRoles(Set.of(
                        UserRoleEntity.builder()
                                .role(Roles.STUDENT)
                                .build()
                ))
                .pcn(1L).build();
        UserEntity userEntity2 = UserEntity.builder()
                .firstName("Mirela")
                .lastName("Goranova")
                .address("Street1")
                .userRoles(Set.of(
                        UserRoleEntity.builder()
                                .role(Roles.STUDENT)
                                .build()
                ))
                .password("Bulgaria")
                .pcn(2L).build();


        List<UserEntity> studentEntities = new ArrayList<UserEntity>();
        studentEntities.add(userEntity2);
        studentEntities.add(userEntity1);

        when(userRepository.findAll()).thenReturn(studentEntities);

        GetAllStudentsRequest request = GetAllStudentsRequest.builder().build();
        GetAllStudentsResponse actualResult = getStudentsUseCase.getStudents(request);
        User user1 = User.builder()
                .firstName("Denitsa")
                .lastName("Goranova")
                .address("Street1")
                .password("Bulgaria")
                .role(Roles.STUDENT)
                .pcn(1L).build();
        User user2 = User.builder()
                .firstName("Mirela")
                .lastName("Goranova")
                .address("Street1")
                .role(Roles.STUDENT)

                .password("Bulgaria")
                .pcn(2L).build();
        GetAllStudentsResponse expectedResult = GetAllStudentsResponse.builder()
                .users(List.of(user2, user1)).build();
        assertEquals(expectedResult, actualResult);
        verify(userRepository).findAll();
    }

    @Test
    void getStudentsShouldThrowAnException(){
        List<UserEntity> studentEntities= Collections.EMPTY_LIST;
        when(userRepository.findAll()).thenReturn(studentEntities);
        GetAllStudentsRequest request = GetAllStudentsRequest.builder().build();
        assertThrows(InvalidEntityException.class, ()
                -> getStudentsUseCase.getStudents(request));
        verify(userRepository).findAll();
    }
}