package nl.fontys.s3.studysmate.studymate.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Announcement {

    private Long id;
    private LocalDateTime submissionDate;

    private String description;
    private Long courseEntityId;

    private String title;
    private String userEntityEmail;
}
