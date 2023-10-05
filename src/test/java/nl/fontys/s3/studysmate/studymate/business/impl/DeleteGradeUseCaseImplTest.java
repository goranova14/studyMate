package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.Roles;
import nl.fontys.s3.studysmate.studymate.persistence.AssignmentRepository;
import nl.fontys.s3.studysmate.studymate.persistence.GradeRepository;
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteGradeUseCaseImplTest {

    @Mock
    private GradeRepository gradeRepository;

    @InjectMocks
    private DeleteGradeUseCaseImpl deleteGradeUseCase;

    @Test
    void deleteAssignmentShouldReturnVoid() {
        UserEntity userEntity = UserEntity.builder()
                .firstName("Denitsa")
                .lastName("Gotanova")
                .address("Niuewe")
                .pcn(2l)
                .email("denica@abv.bg")
                .userRoles(Set.of(
                        UserRoleEntity.builder()
                                .role(Roles.STUDENT)
                                .build()
                ))
                .password("encoded123")
                .build();
        AssignmentEntity assignmentEntity = AssignmentEntity.builder().id(1l).build();
        GradeEntity gradeEntity = GradeEntity.builder().userEntity(userEntity).assignmentEntity(assignmentEntity).grade(1).build();

        when(gradeRepository.findByAssignmentEntityIdAndUserEntityPcn(assignmentEntity.getId(),userEntity.getPcn())).thenReturn(Optional.of(gradeEntity));
        deleteGradeUseCase.deleteGrade(assignmentEntity.getId(),userEntity.getPcn());
        verify(gradeRepository).findByAssignmentEntityIdAndUserEntityPcn(assignmentEntity.getId(),userEntity.getPcn());
//        when(gradeRepository.findByAssignmentEntityIdAndUserEntityPcn(assignmentEntity.getId(),userEntity.getPcn())).thenReturn(Optional.empty());
//        verify(gradeRepository).findByAssignmentEntityIdAndUserEntityPcn(assignmentEntity.getId(),userEntity.getPcn());

        assertFalse(gradeRepository.existsById(1l));

    }

    @Test
    void deleteGradeShouldThrowAnInvalidStudentException() {
        when(gradeRepository.findByAssignmentEntityIdAndUserEntityPcn(1l,2l)).thenReturn(Optional.empty());
        assertThrows(InvalidEntityException.class, () -> deleteGradeUseCase.deleteGrade(1l,2l));
        verify(gradeRepository).findByAssignmentEntityIdAndUserEntityPcn(1l,2l);
    }
}