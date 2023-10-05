package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.Roles;
import nl.fontys.s3.studysmate.studymate.domain.UpdateCourseRequest;
import nl.fontys.s3.studysmate.studymate.domain.UpdateGradeRequest;
import nl.fontys.s3.studysmate.studymate.persistence.GradeRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateGradeUseCaseImplTest {
    @Mock
    private GradeRepository gradeRepository;

    @InjectMocks
    private UpdateGradeUseCaseImpl updateGradeUseCase;

    @Test
    void updateGrade() {
        AssignmentEntity assignment = AssignmentEntity.builder().id(1l).build();
        UserEntity user = UserEntity.builder().pcn(2l).build();

        GradeEntity gradeEntity = GradeEntity.builder()
                .id(1L)
                .grade(1).userEntity(user)
                .assignmentEntity(assignment)
                .build();

        UpdateGradeRequest request = UpdateGradeRequest.builder()
                .gradeNum(gradeEntity.getGrade())
                .assignment(assignment)
                .user(user)
                .build();
        when(gradeRepository.findByAssignmentEntityIdAndUserEntityPcn(1L,2l)).thenReturn(Optional.of(gradeEntity));
        when(gradeRepository.save(gradeEntity)).thenReturn(gradeEntity);

        updateGradeUseCase.updateGrade(request);

        verify(gradeRepository).findByAssignmentEntityIdAndUserEntityPcn(1l,2l);
        verify(gradeRepository).save(gradeEntity);
        assertEquals(request.getGradeNum(), gradeEntity.getGrade());
    }



    @Test
    void updateCourseNegative() {
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
        GradeEntity grade= GradeEntity.builder().grade(1).id(1l).userEntity(userEntity).assignmentEntity(assignmentEntity).build();

        UpdateGradeRequest request = UpdateGradeRequest.builder()
                .gradeNum(grade.getGrade())
                .user(userEntity)
                .assignment(assignmentEntity)

                .build();
       when(gradeRepository.findByAssignmentEntityIdAndUserEntityPcn(request.getAssignment().getId(), request.getUser().getPcn())).thenReturn(Optional.empty());
        assertThrows(InvalidEntityException.class, () -> updateGradeUseCase.updateGrade(request));
        verify(gradeRepository).findByAssignmentEntityIdAndUserEntityPcn(1L, 1L);
    }
}