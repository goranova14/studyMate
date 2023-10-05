package nl.fontys.s3.studysmate.studymate.domain;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.s3.studysmate.studymate.persistence.entity.GradeEntity;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAssignmentRequest {
    @NotNull
    private Long id;
    private String name;
    private String description;
    private LocalDateTime deadline;
    private String feedback;
    private GradeEntity grade;
}
