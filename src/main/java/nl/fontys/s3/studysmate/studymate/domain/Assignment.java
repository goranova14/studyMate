package nl.fontys.s3.studysmate.studymate.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.s3.studysmate.studymate.persistence.entity.CourseEntity;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Assignment {
    private Long id;

    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private LocalDateTime deadline;
    @NotBlank
    private LocalDateTime submission;

    private CourseEntity courseEntity;


}
