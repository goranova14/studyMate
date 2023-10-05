package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.GetUploadedAssignmentResponse;
import nl.fontys.s3.studysmate.studymate.persistence.GradeRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AssignmentEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.GradeEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUploadedAssignmentUseCaseImplTest {
    @Mock
    private GradeRepository gradeRepository;

    @InjectMocks
    private GetUploadedAssignmentUseCaseImpl getUploadedAssignmentUseCase;

    @Test
    void getUrlPositive() {
        AssignmentEntity assignmentEntity = AssignmentEntity.builder().id(2l).build();
        UserEntity user = UserEntity.builder().pcn(1l).build();
        GradeEntity grade = GradeEntity.builder().assignmentUrl("test").build();
        when(gradeRepository.findByAssignmentEntityIdAndUserEntityPcn(assignmentEntity.getId(), user.getPcn())).thenReturn(Optional.of(grade));
        GetUploadedAssignmentResponse response = GetUploadedAssignmentResponse.builder().url("test").build();
        GetUploadedAssignmentResponse actualResponse = getUploadedAssignmentUseCase.getUrl(assignmentEntity.getId(), user.getPcn());
        assertEquals(response.getUrl(), actualResponse.getUrl());

        verify(gradeRepository).findByAssignmentEntityIdAndUserEntityPcn(assignmentEntity.getId(),user.getPcn());
    }
    @Test
    void getUrlNegate() {
        AssignmentEntity assignmentEntity = AssignmentEntity.builder().id(2l).build();
        UserEntity user = UserEntity.builder().pcn(1l).build();
        GradeEntity grade = GradeEntity.builder().assignmentUrl("re").build();
        when(gradeRepository.findByAssignmentEntityIdAndUserEntityPcn(assignmentEntity.getId(), user.getPcn())).thenReturn(Optional.empty());
        assertThrows(InvalidEntityException.class, () -> getUploadedAssignmentUseCase.getUrl(assignmentEntity.getId(),user.getPcn()));
        verify(gradeRepository).findByAssignmentEntityIdAndUserEntityPcn(assignmentEntity.getId(),user.getPcn());
    }
}