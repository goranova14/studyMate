package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.business.exception.InvalidEntityException;
import nl.fontys.s3.studysmate.studymate.persistence.AssignmentRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AnnouncementEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AssignmentEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteAssignmentUseCaseImplTest {

    @Mock
    private AssignmentRepository assignmentRepository;

    @InjectMocks
    private DeleteAssignmentCaseImpl deleteAssignmentCase;

    @Test
    void deleteAssignmentShouldReturnVoid() {

        AssignmentEntity assignmentEntity = AssignmentEntity.builder().id(1L).name("assignment").build();
        when(assignmentRepository.existsById(assignmentEntity.getId())).thenReturn(true);
        deleteAssignmentCase.deleteAssignment(assignmentEntity.getId());
        verify(assignmentRepository).existsById(assignmentEntity.getId());

        when(assignmentRepository.existsById(assignmentEntity.getId())).thenReturn(false);
        verify(assignmentRepository).existsById(assignmentEntity.getId());
        assertFalse(assignmentRepository.existsById(assignmentEntity.getId()));

    }

    @Test
    void deleteAssignmentShouldThrowAnInvalidStudentException() {
        AssignmentEntity assignmentEntity = AssignmentEntity.builder().id(1L).name("title").build();
        when(assignmentRepository.existsById(assignmentEntity.getId())).thenReturn(false);
        assertThrows(InvalidEntityException.class, () -> deleteAssignmentCase.deleteAssignment(assignmentEntity.getId()));
        verify(assignmentRepository).existsById(assignmentEntity.getId());
    }
}