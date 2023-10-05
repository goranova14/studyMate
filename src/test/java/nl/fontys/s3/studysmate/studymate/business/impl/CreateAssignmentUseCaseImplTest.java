package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.domain.*;
import nl.fontys.s3.studysmate.studymate.persistence.AssignmentRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateAssignmentUseCaseImplTest {
    @Mock
    private AssignmentRepository assignmentRepository;
    @InjectMocks
    private CreateAssignmentUseCaseImpl createAssignmentUseCase;

    @Test
    void createAssignment() {

        CreateAssignmentRequest request = new CreateAssignmentRequest();
        request.setName("Title");
         request.setDescription("Description");
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setId(1L);

        request.setCourseEntity(courseEntity);

        AssignmentEntity assignmentEntity = AssignmentEntity.builder().id(1L)
                .name(request.getName())
                .description(request.getDescription())
                .courseEntity(request.getCourseEntity())
                .build();


        when(assignmentRepository.save(any(AssignmentEntity.class))).thenReturn(assignmentEntity);

        CreateAssignmentResponse response = createAssignmentUseCase.createAssignment(request);
        verify(assignmentRepository).save(any(AssignmentEntity.class));
        assertEquals(response.getTitle(), assignmentEntity.getName());
    }


}