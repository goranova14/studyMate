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
public class Grade {
    private Long id;
    private int gradeNum;
    private Long userPcn;
    private String assignmentUrl;

    private Long assignmentId;
    private LocalDateTime submissionDate;

}
