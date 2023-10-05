package nl.fontys.s3.studysmate.studymate.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.s3.studysmate.studymate.persistence.entity.CourseEntity;
import nl.fontys.s3.studysmate.studymate.persistence.entity.UserEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAnnouncementRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private CourseEntity course;
    @NotBlank
    private UserEntity userEntity;



}
