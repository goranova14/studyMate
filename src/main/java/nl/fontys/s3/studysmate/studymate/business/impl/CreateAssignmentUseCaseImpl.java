package nl.fontys.s3.studysmate.studymate.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.CreateAssignmentUseCase;
import nl.fontys.s3.studysmate.studymate.domain.CreateAssignmentRequest;
import nl.fontys.s3.studysmate.studymate.domain.CreateAssignmentResponse;
import nl.fontys.s3.studysmate.studymate.persistence.AssignmentRepository;
import nl.fontys.s3.studysmate.studymate.persistence.entity.AssignmentEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateAssignmentUseCaseImpl implements CreateAssignmentUseCase {
    private final AssignmentRepository assignmentRepository;


    @Override
    public CreateAssignmentResponse createAssignment(CreateAssignmentRequest request) {

        createNewAssignment(request);
        return CreateAssignmentResponse.builder()
                .title(request.getName())
                .build();
    }


    private AssignmentEntity createNewAssignment(CreateAssignmentRequest request) {
        AssignmentEntity newAssignment = AssignmentEntity.builder()
                .name(request.getName())
                .deadline(request.getDeadline())
                .courseEntity(request.getCourseEntity())
                .description(request.getDescription())
                .build();

        return assignmentRepository.save(newAssignment);

    }
}

