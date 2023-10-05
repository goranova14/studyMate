package nl.fontys.s3.studysmate.studymate.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@Builder
public class UploadAssignmentRequest {
    private MultipartFile assignment;
    private Long studentPcn;
    private Long assignmentId;
    @NotBlank
    private LocalDateTime deadline;

}
