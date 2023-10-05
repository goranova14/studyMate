package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.CreateGradeRequest;
import nl.fontys.s3.studysmate.studymate.domain.CreateGradeResponse;
import nl.fontys.s3.studysmate.studymate.domain.Roles;
import nl.fontys.s3.studysmate.studymate.persistence.AssignmentRepository;
import nl.fontys.s3.studysmate.studymate.persistence.GradeRepository;
import nl.fontys.s3.studysmate.studymate.persistence.UserRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AssignmentEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.GradeEntity;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateGradeUseCaseImplTest {
    @InjectMocks
    private CreateGradeUseCaseImpl createGradeUseCase;
    @Mock
    private GradeRepository gradeRepository;
    @Mock
    private AssignmentRepository assignmentRepository;
    @Mock
    private UserRepository userRepository;

    @Test
    void createGradePositive() {
        UserEntity userEntity = UserEntity.builder()
                .firstName("Denitsa")
                .lastName("Gotanova")
                .address("Niuewe")
                .pcn(1l)
                .email("denica@abv.bg")
                .userRoles(Set.of(
                        UserRoleEntity.builder()
                                .role(Roles.STUDENT)
                                .build()
                ))
                .password("encoded123")
                .build();
        AssignmentEntity assignmentEntity = AssignmentEntity.builder().id(1l).build();
        GradeEntity gradeEntity = GradeEntity.builder().userEntity(userEntity).assignmentEntity(assignmentEntity).grade(0).build();
        CreateGradeResponse response = CreateGradeResponse.builder().grade(1).build();
        CreateGradeRequest request = CreateGradeRequest.builder().gradeNum(1).user(userEntity).assignment(assignmentEntity).build();
        when(assignmentRepository.findById(request.getAssignment().getId())).thenReturn(Optional.of(assignmentEntity));
        when(userRepository.findByPcn(request.getUser().getPcn())).thenReturn(Optional.of(userEntity));
        when(gradeRepository.findByAssignmentEntityIdAndUserEntityPcn(assignmentEntity.getId(), userEntity.getPcn())).thenReturn(Optional.of(gradeEntity));
        when(gradeRepository.save(gradeEntity)).thenReturn(gradeEntity);
        assertEquals(response, createGradeUseCase.createGrade(request));
        verify(assignmentRepository).findById(request.getAssignment().getId());
        verify(userRepository).findByPcn(request.getUser().getPcn());
        verify(gradeRepository).findByAssignmentEntityIdAndUserEntityPcn(assignmentEntity.getId(), userEntity.getPcn());

        verify(gradeRepository).save(gradeEntity);

    }

    @Test
    void createNegativeGradeNotFound() {
        AssignmentEntity assignmentEntity = AssignmentEntity.builder().id(1l).build();
        UserEntity userEntity = UserEntity.builder()
                .firstName("Denitsa")
                .lastName("Gotanova")
                .address("Niuewe")
                .pcn(1l)
                .email("denica@abv.bg")
                .userRoles(Set.of(
                        UserRoleEntity.builder()
                                .role(Roles.STUDENT)
                                .build()
                ))
                .password("encoded123")
                .build();
        GradeEntity grade = GradeEntity.builder().grade(1).id(1l).userEntity(userEntity).assignmentEntity(assignmentEntity).build();
        CreateGradeRequest request = CreateGradeRequest.builder().gradeNum(1).user(userEntity).assignment(assignmentEntity).build();
        when(assignmentRepository.findById(request.getAssignment().getId())).thenReturn(Optional.of(assignmentEntity));
        when(userRepository.findByPcn(request.getUser().getPcn())).thenReturn(Optional.of(userEntity));
        when(gradeRepository.findByAssignmentEntityIdAndUserEntityPcn(request.getAssignment().getId(), request.getUser().getPcn())).thenReturn(Optional.empty());
        assertThrows(InvalidEntityException.class, () -> createGradeUseCase.createGrade(request));
        verify(assignmentRepository).findById(request.getAssignment().getId());
        verify(userRepository).findByPcn(request.getUser().getPcn());
        verify(gradeRepository).findByAssignmentEntityIdAndUserEntityPcn(1L, 1L);

    }

    @Test
    void createNegative() {
        AssignmentEntity assignmentEntity = AssignmentEntity.builder().id(1l).build();
        UserEntity userEntity = UserEntity.builder()
                .firstName("Denitsa")
                .lastName("Gotanova")
                .address("Niuewe")
                .pcn(1l)
                .email("denica@abv.bg")
                .userRoles(Set.of(
                        UserRoleEntity.builder()
                                .role(Roles.STUDENT)
                                .build()
                ))
                .password("encoded123")
                .build();
        GradeEntity grade = GradeEntity.builder().grade(1).id(1l).userEntity(userEntity).assignmentEntity(assignmentEntity).build();
        CreateGradeRequest request = CreateGradeRequest.builder().gradeNum(1).user(userEntity).assignment(assignmentEntity).build();
        when(assignmentRepository.findById(request.getAssignment().getId())).thenReturn(Optional.of(assignmentEntity));
        when(userRepository.findByPcn(request.getUser().getPcn())).thenReturn(Optional.of(userEntity));
        when(gradeRepository.findByAssignmentEntityIdAndUserEntityPcn(request.getAssignment().getId(), request.getUser().getPcn())).thenReturn(Optional.of(grade));
        assertThrows(InvalidEntityException.class, () -> createGradeUseCase.createGrade(request));
        verify(gradeRepository).findByAssignmentEntityIdAndUserEntityPcn(1L, 1L);
        verify(assignmentRepository).findById(request.getAssignment().getId());
        verify(userRepository).findByPcn(request.getUser().getPcn());

    }


}