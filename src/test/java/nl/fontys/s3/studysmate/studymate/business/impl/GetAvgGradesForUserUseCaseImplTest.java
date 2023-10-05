package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.business.GetGradesForUserUseCase;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.GetAllGradesForUserResponse;
import nl.fontys.s3.studysmate.studymate.domain.GetAvgGradesResponse;
import nl.fontys.s3.studysmate.studymate.domain.Grade;
import nl.fontys.s3.studysmate.studymate.persistence.AssignmentRepository;
import nl.fontys.s3.studysmate.studymate.persistence.GradeRepository;
import nl.fontys.s3.studysmate.studymate.persistence.UserRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.GradeEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAvgGradesForUserUseCaseImplTest {
    @Mock
    private GradeRepository gradeRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetAvgGradesForUserUseCaseImpl getAvgGradesForUserUseCase;

    @Test
    void getAvgPositive(){
        UserEntity user= UserEntity.builder().pcn(1l).build();
        when(userRepository.findByPcn(user.getPcn())).thenReturn(Optional.of(user));
        double avgGrade=2.5;
        when(gradeRepository.getAverageGradeForUser(user.getPcn())).thenReturn(2.5);
        GetAvgGradesResponse response=getAvgGradesForUserUseCase.getAvgGrade(user.getPcn());
        assertEquals(avgGrade,response.getGrade());
        verify(userRepository).findByPcn(user.getPcn());
        verify(gradeRepository).getAverageGradeForUser(user.getPcn());

    }
    @Test
    void getAvgGradeNegative(){
        UserEntity user= UserEntity.builder().pcn(1l).build();
        when(userRepository.findByPcn(user.getPcn())).thenReturn(Optional.empty());
        assertThrows(InvalidEntityException.class, ()
                -> getAvgGradesForUserUseCase.getAvgGrade(user.getPcn()));
        verify(userRepository).findByPcn(user.getPcn());

    }
}