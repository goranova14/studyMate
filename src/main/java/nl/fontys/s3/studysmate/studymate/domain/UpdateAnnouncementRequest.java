package nl.fontys.s3.studysmate.studymate.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAnnouncementRequest {
    private Long id;
    @NotBlank
    private String description;
    private String title;

}
