package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.domain.UpdateAssignmentRequest;
import nl.fontys.s3.studysmate.studymate.domain.UpdateCourseRequest;
import nl.fontys.s3.studysmate.studymate.persistence.AssignmentRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AssignmentEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.CourseEntity;
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
class UpdateAssignmentUseCaseImplTest {
        @Mock
        private AssignmentRepository assignmentRepository;

        @InjectMocks
        private UpdateAssignmentUseCaseImpl updateAssignmentUseCase;
    @Test
    void updateAssignment() {
        AssignmentEntity assignment = AssignmentEntity.builder()
                .id(1L)
                .build();
        UpdateAssignmentRequest request = UpdateAssignmentRequest.builder()
                .id(1L)
                .build();
        when(assignmentRepository.findById(request.getId())).thenReturn(Optional.of(assignment));
        when(assignmentRepository.save(assignment)).thenReturn(assignment);

        updateAssignmentUseCase.updateAssignment(request);
        verify(assignmentRepository).findById(request.getId());
        verify(assignmentRepository).save(assignment);
        assertEquals(request.getId(), assignment.getId());
    }

    @Test
    void updateAssignmentNegative() {
        UpdateAssignmentRequest request = UpdateAssignmentRequest.builder()
                .id(1L)
                .build();
        when(assignmentRepository.findById(request.getId())).thenReturn(Optional.empty());
        assertThrows(InvalidEntityException.class, () -> updateAssignmentUseCase.updateAssignment(request));
        verify(assignmentRepository).findById(request.getId());
    }

}