package nl.fontys.s3.studysmate.studymate.business;

import nl.fontys.s3.studysmate.studymate.domain.UpdateAssignmentRequest;

public interface UpdateAssignmentUseCase {
    void updateAssignment(UpdateAssignmentRequest request);
}
