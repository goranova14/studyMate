package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.*;
import nl.fontys.s3.studysmate.studymate.persistence.AssignmentRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AssignmentEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAssignmentsUseCaseImplTest {

    @Mock
    private AssignmentRepository assignmentRepository;

    @InjectMocks
    private GetAssignmentsUseCaseImpl getAssignmentsUseCase;

    @Test
    void getAssignments() {


        long courseId = 1L;
        AssignmentEntity assignmentEntity1 = AssignmentEntity.builder().build();
        AssignmentEntity assignmentEntity2 = AssignmentEntity.builder().build();

        List<AssignmentEntity> assignmentEntities = new ArrayList<>();
        assignmentEntities.add(assignmentEntity1);
        assignmentEntities.add(assignmentEntity2);

        when(assignmentRepository.findAllByCourseEntity_Id(courseId)).thenReturn(assignmentEntities);

        GetAllAssignmentsRequest request = GetAllAssignmentsRequest.builder().courseId(courseId).build();

        GetAllAssignmentsResponse response = getAssignmentsUseCase.getAssignments(request);

        assertNotNull(response);
        verify(assignmentRepository).findAllByCourseEntity_Id(courseId);
    }

    @Test
    void getAssignmentsShouldThrowAnException() {
        when(assignmentRepository.findAllByCourseEntity_Id(1L)).thenReturn(Collections.emptyList());
        GetAllAssignmentsRequest request = GetAllAssignmentsRequest.builder().courseId(1L).build();
        assertThrows(InvalidEntityException.class, ()
                -> getAssignmentsUseCase.getAssignments(request));
        verify(assignmentRepository).findAllByCourseEntity_Id(1L);
    }
}