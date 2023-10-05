package nl.fontys.s3.studysmate.studymate.business;

import nl.fontys.s3.studysmate.studymate.domain.GetAllAssignmentsRequest;
import nl.fontys.s3.studysmate.studymate.domain.GetAllAssignmentsResponse;

public interface GetAssignmentsUseCase {
    GetAllAssignmentsResponse getAssignments(GetAllAssignmentsRequest request);
}
