package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.GetAllGradesForUserResponse;
import nl.fontys.s3.studysmate.studymate.persistence.GradeRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AssignmentEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.GradeEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetStudentsGradesUseCaseImplTest {
    @Mock
    private GradeRepository gradeRepository;
    @InjectMocks
    private GetStudentsGradesUseCaseImpl getStudentsGradesUseCase;

    @Test
    public void testGetStudentsPositive() {
        int requestNum = 123;
        List<GradeEntity> gradeEntities = new ArrayList<>();
        AssignmentEntity assignmentEntity = AssignmentEntity.builder().id(123l).build();
        UserEntity user = UserEntity.builder().pcn(1l).build();
        GradeEntity gradeEntity = GradeEntity.builder().userEntity(user).assignmentEntity(assignmentEntity).build();
        gradeEntities.add(gradeEntity);
        when(gradeRepository.getGradeByUserEntityPcn(requestNum)).thenReturn(gradeEntities);

        GetAllGradesForUserResponse response = getStudentsGradesUseCase.getStudents(requestNum);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.getGrades().size());
    }

    @Test
    public void testGetStudentsNegative() {
        int requestNum = 123;
        List<GradeEntity> gradeEntities = new ArrayList<>();
        when(gradeRepository.getGradeByUserEntityPcn(requestNum)).thenReturn(gradeEntities);

        Assertions.assertThrows(InvalidEntityException.class, () -> {
            getStudentsGradesUseCase.getStudents(requestNum);
        });
    }
}