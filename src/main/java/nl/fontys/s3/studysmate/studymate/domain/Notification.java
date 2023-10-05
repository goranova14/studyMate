package nl.fontys.s3.studysmate.studymate.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    private Long id;
    private Long userPcn;

    private Long assignmentId;
    private LocalDateTime submissionDate;

}
