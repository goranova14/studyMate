package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.business.GetGradeUseCase;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.Course;
import nl.fontys.s3.studysmate.studymate.domain.Grade;
import nl.fontys.s3.studysmate.studymate.persistence.GradeRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AssignmentEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.CourseEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.GradeEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetGradeUseCaseImplTest {
    @Mock
    private GradeRepository gradeRepository;

    @InjectMocks
    private GetGradeUseCaseImpl getGradeUseCase;

    @Test
    void getGrade() {
        UserEntity user= UserEntity.builder().pcn(2l).build();
        AssignmentEntity assignment= AssignmentEntity.builder().id(1l).build();
        GradeEntity gradeEntity = GradeEntity.builder()
                .id(1L)
                .userEntity(user)
                .assignmentEntity(assignment)
                .grade(2)
                .build();
        when(gradeRepository.findByAssignmentEntityIdAndUserEntityPcn(1L,2l)).thenReturn(Optional.of(gradeEntity));
        Grade result = getGradeUseCase.getGrade(1l, 2l);
        assertEquals(2,result.getGradeNum());

        verify(gradeRepository).findByAssignmentEntityIdAndUserEntityPcn(1L,2l);
    }   @Test
    void getGradeNegative() {

        when(gradeRepository.findByAssignmentEntityIdAndUserEntityPcn(1L,2l)).thenReturn(Optional.empty());
        assertThrows(InvalidEntityException.class,()->getGradeUseCase.getGrade(1l,2l));
        verify(gradeRepository).findByAssignmentEntityIdAndUserEntityPcn(1L,2l);
    }




}