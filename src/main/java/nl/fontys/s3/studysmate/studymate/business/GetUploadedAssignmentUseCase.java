package nl.fontys.s3.studysmate.studymate.business;

import nl.fontys.s3.studysmate.studymate.domain.GetUploadedAssignmentResponse;


public interface GetUploadedAssignmentUseCase {
    GetUploadedAssignmentResponse getUrl(Long assignmentId,Long studentPcn);
}
