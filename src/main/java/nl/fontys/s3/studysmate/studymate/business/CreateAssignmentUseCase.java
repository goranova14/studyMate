package nl.fontys.s3.studysmate.studymate.business;

import nl.fontys.s3.studysmate.studymate.domain.CreateAssignmentRequest;
import nl.fontys.s3.studysmate.studymate.domain.CreateAssignmentResponse;

public interface CreateAssignmentUseCase {
    CreateAssignmentResponse createAssignment(CreateAssignmentRequest request);

}

