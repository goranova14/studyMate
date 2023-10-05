package nl.fontys.s3.studysmate.studymate.business;

import org.springframework.web.multipart.MultipartFile;


public interface UploadAssignmentUseCase {
    void uploadAssignment(MultipartFile assignment,Long studentPcn,Long assignmentId);

}

