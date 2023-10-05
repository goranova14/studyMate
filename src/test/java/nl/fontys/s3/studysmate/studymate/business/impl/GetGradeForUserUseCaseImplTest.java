package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.GetAllGradesForUserResponse;
import nl.fontys.s3.studysmate.studymate.persistence.GradeRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AssignmentEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.GradeEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class GetGradeForUserUseCaseImplTest {

    @Mock
    private GradeRepository gradeRepository;

    @InjectMocks
    private GetGradesForUserUseCaseImpl getGradesUseCase;
    @Test
    void getGradesForUser() {
        AssignmentEntity assignmentEntity= AssignmentEntity.builder().id(1l).build();
        UserEntity user=UserEntity.builder().pcn(2l).build();
        GradeEntity grade1=GradeEntity.builder().id(1l).userEntity(user).assignmentEntity(assignmentEntity).build();
        GradeEntity grade2=GradeEntity.builder().id(2l).userEntity(user).assignmentEntity(assignmentEntity).build();
        List<GradeEntity> gradeEntityList=new ArrayList<>();
        gradeEntityList.add(grade1);
        gradeEntityList.add(grade2);
        when(gradeRepository.findAllByUserEntityPcn(1l)).thenReturn(gradeEntityList);
        GetAllGradesForUserResponse response=getGradesUseCase.getGrades(1l);
        assertNotNull(response);
        verify(gradeRepository).findAllByUserEntityPcn(1l);
    }
    @Test
    void getGradesForUserNegative(){

        when(gradeRepository.findAllByUserEntityPcn(1l)).thenReturn(Collections.emptyList());
        assertThrows(InvalidEntityException.class,()->getGradesUseCase.getGrades(1l));
        verify(gradeRepository).findAllByUserEntityPcn(1l);


    }
}