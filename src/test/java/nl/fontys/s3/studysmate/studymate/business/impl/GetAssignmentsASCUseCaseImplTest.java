package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.business.GetAssignmentsDeadlineASCUseCase;
import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.GetAllAssignmentsRequest;
import nl.fontys.s3.studysmate.studymate.domain.GetAllAssignmentsResponse;
import nl.fontys.s3.studysmate.studymate.persistence.AssignmentRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AssignmentEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAssignmentsASCUseCaseImplTest {

    @Mock
    private AssignmentRepository assignmentRepository;

    @InjectMocks
    private GetAssignmentsDeadlineASCUseCaseImpl getAssignmentsUseCase;

    @Test
    void getAssignments() {

        LocalDateTime deadline = LocalDateTime.of(2023, 6, 30, 23, 59);
        LocalDateTime deadline1 = LocalDateTime.of(2023, 6, 18, 23, 59);
        long courseId = 1L;
        AssignmentEntity assignmentEntity1 = AssignmentEntity.builder().deadline(deadline).build();
        AssignmentEntity assignmentEntity2 = AssignmentEntity.builder().deadline(deadline1).build();

        List<AssignmentEntity> assignmentEntities = new ArrayList<>();
        assignmentEntities.add(assignmentEntity1);
        assignmentEntities.add(assignmentEntity2);

        when(assignmentRepository.findAllByCourseEntity_IdDeadlineASC(courseId)).thenReturn(assignmentEntities);

        GetAllAssignmentsRequest request = GetAllAssignmentsRequest.builder().courseId(courseId).build();

        GetAllAssignmentsResponse response = getAssignmentsUseCase.getAssignments(request);

        assertNotNull(response);
        verify(assignmentRepository).findAllByCourseEntity_IdDeadlineASC(courseId);
    }

    @Test
    void getAssignmentsShouldThrowAnException() {
        when(assignmentRepository.findAllByCourseEntity_IdDeadlineASC(1L)).thenReturn(Collections.emptyList());
        GetAllAssignmentsRequest request = GetAllAssignmentsRequest.builder().courseId(1L).build();
        assertThrows(InvalidEntityException.class, ()
                -> getAssignmentsUseCase.getAssignments(request));
        verify(assignmentRepository).findAllByCourseEntity_IdDeadlineASC(1L);
    }
}